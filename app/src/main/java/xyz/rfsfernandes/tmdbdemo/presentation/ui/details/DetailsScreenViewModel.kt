package xyz.rfsfernandes.tmdbdemo.presentation.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.rfsfernandes.tmdbdemo.data.util.Resource
import xyz.rfsfernandes.tmdbdemo.domain.usecases.GetMovieDetailsUseCase
import xyz.rfsfernandes.tmdbdemo.presentation.ui.details.viewstate.MovieDetailsViewState

class DetailsScreenViewModel(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
) : ViewModel() {
    private val _viewState = MutableStateFlow(MovieDetailsViewState())
    val viewState = _viewState

    fun getMovieDetails(movieId: Int, language: String) {
        _viewState.value = _viewState.value.copy(isLoading = true)
        viewModelScope.launch {
            getMovieDetailsUseCase(movieId, language).collect { result ->
                _viewState.update {
                    when (result) {
                        is Resource.Default<*> -> viewState.value.copy()
                        is Resource.Error<*> -> viewState.value.copy(
                            error = result.message,
                            isLoading = false
                        )

                        is Resource.NetworkNotAvailable<*> -> viewState.value.copy(
                            error = "Network not available",
                            isLoading = false
                        )

                        is Resource.Success<*> -> viewState.value.copy(
                            movieDetails = result.data,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
}
