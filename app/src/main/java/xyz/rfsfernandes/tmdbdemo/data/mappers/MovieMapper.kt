package xyz.rfsfernandes.tmdbdemo.data.mappers

import xyz.rfsfernandes.tmdbdemo.data.local.model.MovieEntity
import xyz.rfsfernandes.tmdbdemo.data.local.model.MovieHomeType
import xyz.rfsfernandes.tmdbdemo.data.remote.model.movie.Movie


fun Movie.toMovieEntity(language: String, homeType: MovieHomeType): MovieEntity {
    return MovieEntity(
        movieId = id,
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
