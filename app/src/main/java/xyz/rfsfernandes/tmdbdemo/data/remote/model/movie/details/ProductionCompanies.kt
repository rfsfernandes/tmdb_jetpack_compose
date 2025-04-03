package xyz.rfsfernandes.tmdbdemo.data.remote.model.movie.details

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductionCompanies(
    @Json(name = "id") val id: Int? = null,
    @Json(name = "logo_path") val logoPath: String? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "origin_country") val originCountry: String? = null
)
