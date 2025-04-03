package xyz.rfsfernandes.tmdbdemo.data.remote.model.movie.details

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SpokenLanguages(
    @Json(name = "english_name") val englishName: String? = null,
    @Json(name = "iso_639_1") val iso6391: String? = null,
    @Json(name = "name") val name: String? = null
)
