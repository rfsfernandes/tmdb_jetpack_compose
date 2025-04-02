package xyz.rfsfernandes.tmdbdemo.data.remote.model.movie

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie(
    val id: Int,
    val title: String,
    @Json(name = "original_title") val originalTitle: String,
    @Json(name = "release_date") val releaseDate: String,
    val overview: String,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "original_language") val originalLanguage: String,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "vote_count") val voteCount: Int,
    val popularity: Double,
    val adult: Boolean,
    val video: Boolean,
    @Json(name = "genre_ids") val genreIds: List<Int>
)
