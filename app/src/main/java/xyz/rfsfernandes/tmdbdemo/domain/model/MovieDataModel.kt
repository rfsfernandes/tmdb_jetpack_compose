package xyz.rfsfernandes.tmdbdemo.domain.model

import xyz.rfsfernandes.tmdbdemo.domain.MovieHomeType

data class MovieDataModel(
    val movieId: Int,
    val title: String,
    val backdropPath: String?,
    val posterPath: String?,
    val language: String,
    val homeType: MovieHomeType,
)
