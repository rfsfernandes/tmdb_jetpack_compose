package xyz.rfsfernandes.tmdbdemo.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitBuilder(baseUrl: String) {

    private val client = OkHttpClient.Builder()
        .addInterceptor(TokenInterceptor())
        .build()

    private var instance: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val tmdbService: TmdbService = instance.create(TmdbService::class.java)
}
