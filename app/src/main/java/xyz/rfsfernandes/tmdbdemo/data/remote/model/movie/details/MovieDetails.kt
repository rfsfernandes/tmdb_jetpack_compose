package xyz.rfsfernandes.tmdbdemo.data.remote.model.movie.details

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import xyz.rfsfernandes.tmdbdemo.data.remote.model.genre.Genre

@JsonClass(generateAdapter = true)
data class MovieDetails(
    @Json(name = "adult") val adult: Boolean? = null,
    @Json(name = "backdrop_path") val backdropPath: String? = null,
    @Json(name = "belongs_to_collection") val belongsToCollection: MovieCollection? = null,
    @Json(name = "budget") val budget: Int? = null,
    @Json(name = "genres") val genres: List<Genre> = listOf(),
    @Json(name = "homepage") val homepage: String? = null,
    @Json(name = "id") val id: Int,
    @Json(name = "imdb_id") val imdbId: String? = null,
    @Json(name = "origin_country") val originCountry: List<String> = listOf(),
    @Json(name = "original_language") val originalLanguage: String? = null,
    @Json(name = "original_title") val originalTitle: String? = null,
    @Json(name = "overview") val overview: String? = null,
    @Json(name = "popularity") val popularity: Double? = null,
    @Json(name = "poster_path") val posterPath: String? = null,
    @Json(name = "production_companies") val productionCompanies: List<ProductionCompanies> = listOf(),
    @Json(name = "production_countries") val productionCountries: List<ProductionCountries> = listOf(),
    @Json(name = "release_date") val releaseDate: String? = null,
    @Json(name = "revenue") val revenue: Int? = null,
    @Json(name = "runtime") val runtime: Int? = null,
    @Json(name = "spoken_languages") val spokenLanguages: List<SpokenLanguages> = listOf(),
    @Json(name = "status") val status: String? = null,
    @Json(name = "tagline") val tagline: String? = null,
    @Json(name = "title") val title: String? = null,
    @Json(name = "video") val video: Boolean? = null,
    @Json(name = "vote_average") val voteAverage: Double? = null,
    @Json(name = "vote_count") val voteCount: Int? = null
)
