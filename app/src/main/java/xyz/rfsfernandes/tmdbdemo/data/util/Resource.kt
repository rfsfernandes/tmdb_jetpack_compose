package xyz.rfsfernandes.tmdbdemo.data.util

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String? = null, val errorCode: Int? = null) :
        Resource<T>(message = message)

    class NetworkNotAvailable<T> : Resource<T>()
    class Default<T> : Resource<T>()
}
