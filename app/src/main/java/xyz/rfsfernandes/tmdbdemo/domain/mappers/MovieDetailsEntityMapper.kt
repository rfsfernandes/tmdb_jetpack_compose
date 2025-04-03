package xyz.rfsfernandes.tmdbdemo.domain.mappers

import xyz.rfsfernandes.tmdbdemo.data.local.model.moviedetails.MovieDetailsEntity
import xyz.rfsfernandes.tmdbdemo.data.remote.model.genre.Genre
import xyz.rfsfernandes.tmdbdemo.data.remote.model.movie.details.MovieCollection
import xyz.rfsfernandes.tmdbdemo.data.remote.model.movie.details.ProductionCompanies
import xyz.rfsfernandes.tmdbdemo.data.remote.model.movie.details.ProductionCountries
import xyz.rfsfernandes.tmdbdemo.data.remote.model.movie.details.SpokenLanguages
import xyz.rfsfernandes.tmdbdemo.domain.model.GenreDataModel
import xyz.rfsfernandes.tmdbdemo.domain.model.MovieCollectionDataModel
import xyz.rfsfernandes.tmdbdemo.domain.model.MovieDetailsDataModel
import xyz.rfsfernandes.tmdbdemo.domain.model.ProductionCompaniesDataModel
import xyz.rfsfernandes.tmdbdemo.domain.model.ProductionCountriesDataModel
import xyz.rfsfernandes.tmdbdemo.domain.model.SpokenLanguagesDataModel

fun MovieDetailsEntity.toDomain(): MovieDetailsDataModel {
    return MovieDetailsDataModel(
        id = id,
        adult = adult,
        backdropPath = backdropPath,
        belongsToCollection = belongsToCollection?.toDomain(),
        budget = budget,
        genres = genres.map { it.toDomain() },
        homepage = homepage,
        imdbId = imdbId,
        originCountry = originCountry,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        productionCompanies = productionCompanies.map { it.toDomain() },
        productionCountries = productionCountries.map { it.toDomain() },
        releaseDate = releaseDate,
        revenue = revenue,
        runtime = runtime,
        spokenLanguages = spokenLanguages.map { it.toDomain() },
        status = status,
        tagline = tagline,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount,
        language = language,
    )
}

fun Genre.toDomain(): GenreDataModel {
    return GenreDataModel(
        id = id,
        name = name,
    )
}

fun ProductionCompanies.toDomain(): ProductionCompaniesDataModel {
    return ProductionCompaniesDataModel(
        logoPath = logoPath,
        name = name,
    )
}

fun ProductionCountries.toDomain(): ProductionCountriesDataModel {
    return ProductionCountriesDataModel(
        iso31661 = iso31661,
        name = name,
    )
}

fun SpokenLanguages.toDomain(): SpokenLanguagesDataModel {
    return SpokenLanguagesDataModel(
        englishName = englishName,
        iso6391 = iso6391,
        name = name,
    )
}

fun MovieCollection.toDomain(): MovieCollectionDataModel {
    return MovieCollectionDataModel(
        id = id,
        name = name,
        posterPath = posterPath,
        backdropPath = backdropPath,
    )
}