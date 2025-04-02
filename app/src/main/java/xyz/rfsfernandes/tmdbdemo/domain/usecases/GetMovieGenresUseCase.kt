package xyz.rfsfernandes.tmdbdemo.domain.usecases

import android.util.Log
import kotlinx.coroutines.flow.onEach
import xyz.rfsfernandes.tmdbdemo.domain.mediator.TmdbMediator

class GetMovieGenresUseCase(private val mediator: TmdbMediator) {

    companion object GMG {
        private const val TAG = "GetMovieGenres"
    }

    operator fun invoke(language: String?) = mediator.movieGenres(language).onEach {
        Log.i(TAG, "invoke: $it")
    }
}
