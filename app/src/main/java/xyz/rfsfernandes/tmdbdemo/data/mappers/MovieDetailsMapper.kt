package xyz.rfsfernandes.tmdbdemo.data.mappers

import xyz.rfsfernandes.tmdbdemo.data.local.model.moviedetails.MovieDetailsEntity
import xyz.rfsfernandes.tmdbdemo.data.remote.model.movie.details.MovieDetails

fun MovieDetails.toEntity(language: String): MovieDetailsEntity {
    return MovieDetailsEntity(
        id = id,
        adult = adult,
        backdropPath = backdropPath,
        belongsToCollection = belongsToCollection,
        budget = budget,
        genres = genres,
        homepage = homepage,
        imdbId = imdbId,
        originCountry = originCountry,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        productionCompanies = productionCompanies,
        productionCountries = productionCountries,
        releaseDate = releaseDate,
        revenue = revenue,
        runtime = runtime,
        spokenLanguages = spokenLanguages,
        status = status,
        tagline = tagline,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount,
        language = language
    )
}
