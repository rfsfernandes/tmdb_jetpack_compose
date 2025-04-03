package xyz.rfsfernandes.tmdbdemo.domain.mediator

import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import xyz.rfsfernandes.tmdbdemo.data.repository.Repository
import xyz.rfsfernandes.tmdbdemo.data.util.Resource
import xyz.rfsfernandes.tmdbdemo.domain.MovieHomeType
import xyz.rfsfernandes.tmdbdemo.domain.mappers.toDomain
import xyz.rfsfernandes.tmdbdemo.domain.model.GenreDataModel
import xyz.rfsfernandes.tmdbdemo.domain.model.MovieDataModel
import xyz.rfsfernandes.tmdbdemo.domain.model.MovieDetailsDataModel

class TmdbMediator(
    private val repository: Repository,
) {

    fun movieGenres(language: String?): Flow<Resource<List<GenreDataModel>>> =
        repository.getMovieGenres(language).map {
            when (it) {
                is Resource.Error -> Resource.Error<List<GenreDataModel>>(it.message, it.errorCode)
                is Resource.NetworkNotAvailable -> Resource.NetworkNotAvailable<List<GenreDataModel>>()
                is Resource.Success -> Resource.Success<List<GenreDataModel>>(it.data?.map { content -> content.toDomain() })
                is Resource.Default -> Resource.Default()
            }
        }

    fun movies(homeType: MovieHomeType, language: String): Flow<PagingData<MovieDataModel>> =
        repository.getMovies(homeType, language)
            .map { pagingData ->
                pagingData.map { it.toDomain() }
            }

    fun movieDetails(movieId: Int, language: String): Flow<Resource<MovieDetailsDataModel>> =
        repository.getMovieDetails(movieId, language).map {
            when (it) {
                is Resource.Default -> Resource.Default()
                is Resource.Error -> Resource.Error<MovieDetailsDataModel>(it.message, it.errorCode)
                is Resource.NetworkNotAvailable -> Resource.NetworkNotAvailable<MovieDetailsDataModel>()
                is Resource.Success -> Resource.Success<MovieDetailsDataModel>(it.data?.toDomain())
            }
        }
}
