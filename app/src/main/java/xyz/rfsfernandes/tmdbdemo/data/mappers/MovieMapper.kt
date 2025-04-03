package xyz.rfsfernandes.tmdbdemo.data.mappers

import xyz.rfsfernandes.tmdbdemo.data.local.model.MovieEntity
import xyz.rfsfernandes.tmdbdemo.data.remote.model.movie.Movie
import xyz.rfsfernandes.tmdbdemo.domain.MovieHomeType


fun Movie.toMovieEntity(language: String, homeType: MovieHomeType): MovieEntity {
    return MovieEntity(
        movieId = id,
        title = title,
        backdropPath = backdropPath,
        posterPath = posterPath,
        language = language,
        homeType = homeType,
    )
}
