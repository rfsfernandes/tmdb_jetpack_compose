package xyz.rfsfernandes.tmdbdemo.data.remote

import android.content.Context
import com.squareup.moshi.Moshi
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import xyz.rfsfernandes.tmdbdemo.data.network.NetworkManager
import java.io.File

class RetrofitBuilder(
    private val context: Context,
    baseUrl: String,
    moshi: Moshi,
    private val networkManager: NetworkManager) {

    private fun provideCache(): Cache {
        val cacheSize = 10 * 1024 * 1024L // 10 MB
        val cacheDir = File(context.cacheDir, "http_cache")
        return Cache(cacheDir, cacheSize)
    }

    private fun provideClient(): OkHttpClient {
        val cache = provideCache()

        // Interceptor to force cache when offline
        val offlineInterceptor = Interceptor { chain ->
            var request = chain.request()
            if (networkManager.value == false) {
                request = request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=604800") // 7 days
                    .build()
            }
            chain.proceed(request)
        }

        // Interceptor to cache responses
        val onlineInterceptor = Interceptor { chain ->
            val response = chain.proceed(chain.request())
            val maxAge = 60 // read from cache for 1 minute
            response.newBuilder()
                .header("Cache-Control", "public, max-age=$maxAge")
                .build()
        }

        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(offlineInterceptor)
            .addInterceptor(TokenInterceptor())
            .addNetworkInterceptor(onlineInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    private var instance: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(provideClient())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val tmdbService: TmdbService = instance.create(TmdbService::class.java)
}
