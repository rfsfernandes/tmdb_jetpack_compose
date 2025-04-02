package xyz.rfsfernandes.tmdbdemo.data.remote.model.error

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    val success: Boolean,
    @Json(name = "status_code") val statusCode: Int,
    @Json(name = "status_message") val statusMessage: String
)
