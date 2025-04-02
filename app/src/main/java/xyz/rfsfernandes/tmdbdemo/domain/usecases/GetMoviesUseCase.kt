package xyz.rfsfernandes.tmdbdemo.domain.usecases

import android.util.Log
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import xyz.rfsfernandes.tmdbdemo.data.local.model.MovieHomeType
import xyz.rfsfernandes.tmdbdemo.data.util.Resource
import xyz.rfsfernandes.tmdbdemo.domain.mediator.TmdbMediator
import xyz.rfsfernandes.tmdbdemo.domain.model.MovieDataModel

class GetMoviesUseCase(private val mediator: TmdbMediator) {

    companion object GMG {
        private const val TAG = "GetMoviesUseCase"
    }

    operator fun invoke(movieHomeType: MovieHomeType, language: String): Flow<PagingData<MovieDataModel>> = mediator.movies(movieHomeType, language).onEach {
        Log.i(TAG, "invoke: $it")
    }
}
