package xyz.rfsfernandes.tmdbdemo.data.util

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    data class Success<T>(val newData: T? = null) : Resource<T>(newData)
    class Error<T>(message: String? = null, val errorCode: Int? = null) :
        Resource<T>(message = message)

    class NetworkNotAvailable<T>() : Resource<T>()
    class Default<T> : Resource<T>()
}
