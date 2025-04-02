package xyz.rfsfernandes.tmdbdemo.di

import androidx.room.Room
import com.squareup.moshi.Moshi
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import xyz.rfsfernandes.tmdbdemo.BuildConfig
import xyz.rfsfernandes.tmdbdemo.data.local.AppDatabase
import xyz.rfsfernandes.tmdbdemo.data.local.TmdbDAO
import xyz.rfsfernandes.tmdbdemo.data.network.NetworkManager
import xyz.rfsfernandes.tmdbdemo.data.remote.RetrofitBuilder
import xyz.rfsfernandes.tmdbdemo.data.remote.TmdbService
import xyz.rfsfernandes.tmdbdemo.data.repository.Repository
import xyz.rfsfernandes.tmdbdemo.data.repository.RepositoryImpl
import xyz.rfsfernandes.tmdbdemo.domain.mediator.TmdbMediator
import xyz.rfsfernandes.tmdbdemo.domain.usecases.GetMovieGenresUseCase
import xyz.rfsfernandes.tmdbdemo.domain.usecases.GetMoviesUseCase
import xyz.rfsfernandes.tmdbdemo.presentation.ui.home.HomeViewModel

object DI {

    private val networkModule = module {
        single { NetworkManager(androidContext()) }
    }

    private val dataModule = module {
        single<Moshi> { Moshi.Builder().build() }
        single<TmdbService> { RetrofitBuilder(androidContext(), BuildConfig.TMDB_ENDPOINT, get(), get()).tmdbService }
        single<AppDatabase> {
            Room
                .databaseBuilder(androidContext(), AppDatabase::class.java, BuildConfig.DB_NAME)
                .build()
        }
        single<TmdbDAO> { get<AppDatabase>().tmdbDAO() }
        single<Repository> { RepositoryImpl(get<AppDatabase>(), get<TmdbService>(), get())}
    }

    private val domainModule = module {
        single<TmdbMediator> { TmdbMediator(get<Repository>()) }
        single<GetMovieGenresUseCase> { GetMovieGenresUseCase(get<TmdbMediator>()) }
        single<GetMoviesUseCase> { GetMoviesUseCase(get<TmdbMediator>()) }
    }

    private val viewModelModule = module {
        viewModelOf(::HomeViewModel)
    }

    val modules = listOf(networkModule, dataModule, domainModule, viewModelModule)
}