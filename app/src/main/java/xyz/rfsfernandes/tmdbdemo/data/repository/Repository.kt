package xyz.rfsfernandes.tmdbdemo.data.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import xyz.rfsfernandes.tmdbdemo.data.local.model.GenreEntity
import xyz.rfsfernandes.tmdbdemo.data.local.model.MovieEntity
import xyz.rfsfernandes.tmdbdemo.data.local.model.moviedetails.MovieDetailsEntity
import xyz.rfsfernandes.tmdbdemo.data.util.Resource
import xyz.rfsfernandes.tmdbdemo.domain.MovieHomeType

interface Repository {

    fun getMovieGenres(language: String?): Flow<Resource<List<GenreEntity>>>

    fun getMovies(
        homeType: MovieHomeType,
        language: String,
    ): Flow<PagingData<MovieEntity>>

    fun getMovieDetails(
        movieId: Int,
        language: String,
    ): Flow<Resource<MovieDetailsEntity>>
}
