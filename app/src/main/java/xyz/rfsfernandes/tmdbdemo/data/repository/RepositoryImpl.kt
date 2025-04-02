package xyz.rfsfernandes.tmdbdemo.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import xyz.rfsfernandes.tmdbdemo.data.local.AppDatabase
import xyz.rfsfernandes.tmdbdemo.data.local.TmdbDAO
import xyz.rfsfernandes.tmdbdemo.data.local.model.GenreEntity
import xyz.rfsfernandes.tmdbdemo.data.local.model.MovieEntity
import xyz.rfsfernandes.tmdbdemo.data.local.model.MovieHomeType
import xyz.rfsfernandes.tmdbdemo.data.mappers.toEntity
import xyz.rfsfernandes.tmdbdemo.data.network.NetworkManager
import xyz.rfsfernandes.tmdbdemo.data.remote.TmdbService
import xyz.rfsfernandes.tmdbdemo.data.remote.model.error.ErrorResponse
import xyz.rfsfernandes.tmdbdemo.data.remotemediator.MovieRemoteMediator
import xyz.rfsfernandes.tmdbdemo.data.util.Resource

class RepositoryImpl(
    private val appDatabase: AppDatabase,
    private val tmdbService: TmdbService,
    networkManager: NetworkManager,
): Repository {

    private val tmdbDAO: TmdbDAO = appDatabase.tmdbDAO()

    private val moshi = Moshi.Builder().build()
    private val errorAdapter = moshi.adapter(ErrorResponse::class.java)
    private val hasNetwork = networkManager.value

    private val cacheDuration = 3 * 24 * 60 * 60 * 1000L // 1 day

    companion object RI {
        private const val TAG = "RepositoryImpl"
    }

    override fun getMovieGenres(language: String?): Flow<Resource<List<GenreEntity>>> = flow {
        val dbResult = tmdbDAO.getMovieGenres(language ?: "en")
        if (dbResult.isNotEmpty()) {
            emit(Resource.Success(dbResult))
            Log.i(TAG, "getMovieGenres: loaded from db -> $dbResult")
        }

        val cacheTime = tmdbDAO.getCacheTime(language ?: "en")
        val currentTimeMillis = System.currentTimeMillis()
        if (cacheTime != null && currentTimeMillis - cacheTime.cacheAtDate < cacheDuration) {
            Log.i(TAG, "getMovieGenres: loaded from cache")
            return@flow
        }

        if(hasNetwork == true) {
            try {
                val response = tmdbService.getMovieGenres(language)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        val map = body.genres.map { it.toEntity(language!!) }
                        tmdbDAO.insertMovieGenres(map)
                        emit(Resource.Success(map))
                    } else {
                        emit(Resource.Error<List<GenreEntity>>(message = response.message()))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorResponse = errorBody?.let { errorAdapter.fromJson(it) }
                    val errorMessage = errorResponse?.statusMessage ?: "Unknown error"
                    emit(Resource.Error<List<GenreEntity>>(errorCode = response.code(), message = errorMessage))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception while fetching getMovieGenres: ${e.message}")
                emit(Resource.Error<List<GenreEntity>>(e.message))
            }
        } else {
            emit(Resource.NetworkNotAvailable())
        }
    }.flowOn(Dispatchers.IO)

    @OptIn(ExperimentalPagingApi::class)
    override fun getMovies(
        homeType: MovieHomeType,
        language: String,
    ): Flow<PagingData<MovieEntity>>  {

        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 5,
                enablePlaceholders = false,
                maxSize = 100
            ),
            remoteMediator = MovieRemoteMediator(
                tmdbService = tmdbService,
                appDatabase = appDatabase,
                type = homeType,
                language = language
            ),
        ) {
            if (homeType == MovieHomeType.FEATURED) {
                tmdbDAO.getFeaturedMovies(MovieHomeType.FEATURED.name)
            } else {
                tmdbDAO.getMoviesPaged(language, homeType.name)
            }
        }.flow
    }

}
