package xyz.rfsfernandes.tmdbdemo.data.remote.model.movie.details

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductionCountries(
    @Json(name = "iso_3166_1") val iso31661: String? = null,
    @Json(name = "name") val name: String? = null
)
