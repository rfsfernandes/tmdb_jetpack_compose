package xyz.rfsfernandes.tmdbdemo.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import xyz.rfsfernandes.tmdbdemo.BuildConfig
import xyz.rfsfernandes.tmdbdemo.data.local.AppDatabase
import xyz.rfsfernandes.tmdbdemo.data.local.TmdbDAO
import xyz.rfsfernandes.tmdbdemo.data.remote.RetrofitBuilder
import xyz.rfsfernandes.tmdbdemo.data.remote.TmdbService
import xyz.rfsfernandes.tmdbdemo.data.repository.Repository
import xyz.rfsfernandes.tmdbdemo.data.repository.RepositoryImpl

object DI {

    private val appModule = module {
        single<Repository> { RepositoryImpl() }
    }

    private val dataModule = module {
        single { RetrofitBuilder(BuildConfig.TMDB_ENDPOINT).tmdbService }
        single { Room.databaseBuilder(androidContext(), AppDatabase::class.java, BuildConfig.DB_NAME).build() }
        single { get<AppDatabase>().tmdbDAO() }
    }


    val modules = listOf(appModule, dataModule)
}