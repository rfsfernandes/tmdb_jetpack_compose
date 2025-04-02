package xyz.rfsfernandes.tmdbdemo.domain.model

import xyz.rfsfernandes.tmdbdemo.data.local.model.MovieHomeType

data class MovieDataModel(
    val movieId: Int,
    val title: String,
    val originalTitle: String,
    val releaseDate: String,
    val overview: String,
    val backdropPath: String?,
    val posterPath: String?,
    val originalLanguage: String,
    val voteAverage: Double,
    val voteCount: Int,
    val popularity: Double,
    val adult: Boolean,
    val video: Boolean,
    val genreIds: List<Int>,
    val language: String,
    val homeType: MovieHomeType,
)
