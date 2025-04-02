package xyz.rfsfernandes.tmdbdemo.data.remote.model.movie

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieResponse(
    val dates: Dates? = null,
    val page: Int,
    val results: List<Movie>,
    @Json(name = "total_pages") val totalPages: Int? = null,
    @Json(name = "total_results") val totalResults: Int? = null,
)
