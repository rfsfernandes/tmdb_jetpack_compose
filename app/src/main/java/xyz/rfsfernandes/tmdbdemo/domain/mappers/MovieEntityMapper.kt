package xyz.rfsfernandes.tmdbdemo.domain.mappers

import xyz.rfsfernandes.tmdbdemo.data.local.model.MovieEntity
import xyz.rfsfernandes.tmdbdemo.domain.model.MovieDataModel

fun MovieEntity.toDomain(): MovieDataModel {

    return MovieDataModel(
        movieId = movieId,
        title = title,
        backdropPath = backdropPath,
        posterPath = posterPath,
        language = language,
        homeType = homeType,
    )
}
