package xyz.rfsfernandes.tmdbdemo.presentation.ui.home

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import xyz.rfsfernandes.tmdbdemo.data.network.NetworkManager
import xyz.rfsfernandes.tmdbdemo.domain.MovieHomeType
import xyz.rfsfernandes.tmdbdemo.data.util.Resource
import xyz.rfsfernandes.tmdbdemo.domain.model.MovieDataModel
import xyz.rfsfernandes.tmdbdemo.domain.usecases.GetMoviesUseCase
import xyz.rfsfernandes.tmdbdemo.presentation.ui.home.viewstate.HomeViewState
import java.io.IOException

class HomeViewModel(
    private val getMoviesUseCase: GetMoviesUseCase,
) : ViewModel() {
    private val _viewState: MutableStateFlow<HomeViewState> = MutableStateFlow(HomeViewState())
    val viewState: StateFlow<HomeViewState> = _viewState

    var homeList: LazyListState by mutableStateOf(LazyListState())
    var categoriesListState: List<Pair<MovieHomeType, MutableState<LazyListState>>> =
        MovieHomeType.entries.filter { it != MovieHomeType.FEATURED }
            .map { Pair(it, mutableStateOf(LazyListState())) }

    fun getMovies(language: String) {
        viewModelScope.launch {
            _viewState.update { it.copy(isLoading = true) }

            val moviesList = listOf(
                MovieHomeType.NOW_PLAYING to getMoviesUseCase(
                    MovieHomeType.NOW_PLAYING,
                    language
                ).cachedIn(viewModelScope),
                MovieHomeType.POPULAR to getMoviesUseCase(MovieHomeType.POPULAR, language).cachedIn(
                    viewModelScope
                ),
                MovieHomeType.TOP_RATED to getMoviesUseCase(
                    MovieHomeType.TOP_RATED,
                    language
                ).cachedIn(viewModelScope),
                MovieHomeType.UPCOMING to getMoviesUseCase(
                    MovieHomeType.UPCOMING,
                    language
                ).cachedIn(viewModelScope),
                MovieHomeType.FEATURED to getMoviesUseCase(
                    MovieHomeType.FEATURED,
                    language
                ).cachedIn(viewModelScope),
            )

            moviesList.forEach { (homeViewType, moviesFlow) ->
                launch {
                    moviesFlow
                        .map { pagingData ->
                            Resource.Success(pagingData) as Resource<PagingData<MovieDataModel>>
                        }
                        .catch { e ->
                            emit(
                                when (e) {
                                    is IOException -> Resource.NetworkNotAvailable()
                                    is HttpException -> Resource.Error(
                                        message = e.localizedMessage ?: "HTTP Error",
                                        errorCode = e.code()
                                    )

                                    else -> Resource.Error(message = "Unexpected error occurred")
                                }
                            )
                        }
                        .collect { result ->
                            _viewState.update { currentState ->
                                when (result) {
                                    is Resource.Default -> currentState
                                    is Resource.Error -> currentState.copy(
                                        isLoading = false, error = result.message
                                    )

                                    is Resource.NetworkNotAvailable -> currentState.copy(
                                        isLoading = false, error = "Network not available"
                                    )

                                    is Resource.Success -> {
                                        when (homeViewType) {
                                            MovieHomeType.POPULAR -> currentState.copy(
                                                isLoading = false,
                                                error = null,
                                                popularMovies = moviesFlow
                                            )

                                            MovieHomeType.NOW_PLAYING -> currentState.copy(
                                                isLoading = false,
                                                error = null,
                                                nowPlayingMovies = moviesFlow
                                            )

                                            MovieHomeType.TOP_RATED -> currentState.copy(
                                                isLoading = false,
                                                error = null,
                                                topRatedMovies = moviesFlow
                                            )

                                            MovieHomeType.UPCOMING -> currentState.copy(
                                                isLoading = false,
                                                error = null,
                                                upcomingMovies = moviesFlow
                                            )

                                            MovieHomeType.FEATURED -> currentState.copy(
                                                isLoading = false,
                                                error = null,
                                                featuredMovies = moviesFlow
                                            )
                                        }
                                    }
                                }
                            }
                        }
                }
            }
        }
    }
}
