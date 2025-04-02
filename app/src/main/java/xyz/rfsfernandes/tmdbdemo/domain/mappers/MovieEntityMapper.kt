package xyz.rfsfernandes.tmdbdemo.domain.mappers

import xyz.rfsfernandes.tmdbdemo.data.local.model.MovieEntity
import xyz.rfsfernandes.tmdbdemo.domain.model.MovieDataModel

fun MovieEntity.toDomain(): MovieDataModel {

    return MovieDataModel(
        movieId = movieId,
        title = title,
        originalTitle = originalTitle,
        releaseDate = releaseDate,
        overview = overview,
        backdropPath = backdropPath,
        posterPath = posterPath,
        originalLanguage = originalLanguage,
        voteAverage = voteAverage,
        voteCount = voteCount,
        popularity = popularity,
        adult = adult,
        video = video,
        genreIds = genreIds,
        language = language,
        homeType = homeType,
    )
}
