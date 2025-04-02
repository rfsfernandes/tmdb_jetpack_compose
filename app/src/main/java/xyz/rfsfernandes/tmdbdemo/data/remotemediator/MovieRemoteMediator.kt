package xyz.rfsfernandes.tmdbdemo.data.remotemediator

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import retrofit2.HttpException
import xyz.rfsfernandes.tmdbdemo.data.local.AppDatabase
import xyz.rfsfernandes.tmdbdemo.data.local.TmdbDAO
import xyz.rfsfernandes.tmdbdemo.data.local.model.MovieEntity
import xyz.rfsfernandes.tmdbdemo.data.local.model.MovieHomeType
import xyz.rfsfernandes.tmdbdemo.data.local.model.MovieRemoteKey
import xyz.rfsfernandes.tmdbdemo.data.mappers.toMovieEntity
import xyz.rfsfernandes.tmdbdemo.data.remote.TmdbService

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val tmdbService: TmdbService,
    private val appDatabase: AppDatabase,
    private val type: MovieHomeType,
    private val language: String,
) : RemoteMediator<Int, MovieEntity>() {

    private val tmdbDAO: TmdbDAO = appDatabase.tmdbDAO()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                if (lastItem == null) {
                    // Se o PagingState está vazio, tenta buscar o último item do banco de dados
                    val moviesInDb = tmdbDAO.getAllMovies(language, type.name)
                    if (moviesInDb.isEmpty()) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    val lastMovie = moviesInDb.last()
                    val remoteKey = tmdbDAO.getRemoteKeyByMovieId(lastMovie.movieId, language, type.name)
                        ?: return MediatorResult.Error(
                            IllegalStateException("No remote key found for movie ${lastMovie.movieId}")
                        )
                    remoteKey.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                } else {
                    val remoteKey = tmdbDAO.getRemoteKeyByMovieId(lastItem.movieId, language, type.name)
                        ?: return MediatorResult.Error(
                            IllegalStateException("No remote key found for movie ${lastItem.movieId}")
                        )
                    remoteKey.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                }

            }
        }

        return try {
            val response = when (type) {
                MovieHomeType.NOW_PLAYING -> tmdbService.getNowPlayingMovies(language, page)
                MovieHomeType.POPULAR -> tmdbService.getPopularMovies(language, page)
                MovieHomeType.TOP_RATED -> tmdbService.getTopRatedMovies(language, page)
                MovieHomeType.UPCOMING -> tmdbService.getUpcomingMovies(language, page)
            }

            if (response.isSuccessful) {
                val movies = response.body()?.results ?: emptyList()
                val endOfPagination = movies.isEmpty() || response.body()?.totalPages == page || movies.size < state.config.pageSize
                appDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        tmdbDAO.deleteMovies(type.name, language)
                        tmdbDAO.clearRemoteKeysFromType(language, type.name)
                    }

                    val keys = movies.map { movie ->
                        MovieRemoteKey(
                            movieId = movie.id,
                            prevKey = if (page == 1) null else page - 1,
                            nextKey = if (endOfPagination) null else page + 1,
                            language = language,
                            homeType = type,
                        )
                    }
                    Log.d("MovieRemoteMediator", "Page: $page, NextKey: ${keys.firstOrNull()?.nextKey}")

                    val movieEntities = movies.map { it.toMovieEntity(language, type) }

                    tmdbDAO.insertAllKeys(keys)
                    tmdbDAO.insertMovies(movieEntities)
                    MediatorResult.Success(endOfPaginationReached = endOfPagination)
                }
            } else {
                MediatorResult.Error(HttpException(response))
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
