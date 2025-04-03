package xyz.rfsfernandes.tmdbdemo.data.local.model.moviedetails

import androidx.room.Entity
import xyz.rfsfernandes.tmdbdemo.data.remote.model.genre.Genre
import xyz.rfsfernandes.tmdbdemo.data.remote.model.movie.details.MovieCollection
import xyz.rfsfernandes.tmdbdemo.data.remote.model.movie.details.ProductionCompanies
import xyz.rfsfernandes.tmdbdemo.data.remote.model.movie.details.ProductionCountries
import xyz.rfsfernandes.tmdbdemo.data.remote.model.movie.details.SpokenLanguages

@Entity(primaryKeys = ["id", "language"])
data class MovieDetailsEntity(
    val id: Int,
    val adult: Boolean? = null,
    val backdropPath: String? = null,
    val belongsToCollection: MovieCollection? = null,
    val budget: Int? = null,
    val genres: List<Genre> = listOf(),
    val homepage: String? = null,
    val imdbId: String? = null,
    val originCountry: List<String> = listOf(),
    val originalLanguage: String? = null,
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val posterPath: String? = null,
    val productionCompanies: List<ProductionCompanies> = listOf(),
    val productionCountries: List<ProductionCountries> = listOf(),
    val releaseDate: String? = null,
    val revenue: Int? = null,
    val runtime: Int? = null,
    val spokenLanguages: List<SpokenLanguages> = listOf(),
    val status: String? = null,
    val tagline: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val voteAverage: Double? = null,
    val voteCount: Int? = null,
    val language: String
)
