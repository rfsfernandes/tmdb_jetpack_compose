package xyz.rfsfernandes.tmdbdemo.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import xyz.rfsfernandes.tmdbdemo.BuildConfig

class TokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // Get the token from your secure storage
        val token = BuildConfig.API_KEY

        // If the token exists, add it to the request header
        val newRequest = request.newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(newRequest)
    }

}
