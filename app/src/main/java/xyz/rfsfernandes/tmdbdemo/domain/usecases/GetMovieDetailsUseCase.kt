package xyz.rfsfernandes.tmdbdemo.domain.usecases

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import xyz.rfsfernandes.tmdbdemo.data.util.Resource
import xyz.rfsfernandes.tmdbdemo.domain.mediator.TmdbMediator
import xyz.rfsfernandes.tmdbdemo.domain.model.MovieDetailsDataModel

class GetMovieDetailsUseCase(private val mediator: TmdbMediator) {

    companion object GMDUC {
        private const val TAG = "GetMovieDetailsUseCase"
    }

    operator fun invoke(movieId: Int, language: String): Flow<Resource<MovieDetailsDataModel>> =
        mediator.movieDetails(movieId, language).onEach {
            Log.i(TAG, "invoke: $it")
        }
}
