package xyz.rfsfernandes.tmdbdemo.presentation.ui.home.viewstate

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import xyz.rfsfernandes.tmdbdemo.data.local.model.MovieEntity
import xyz.rfsfernandes.tmdbdemo.domain.model.GenreDataModel
import xyz.rfsfernandes.tmdbdemo.domain.model.MovieDataModel

data class HomeViewState(
    val isLoading: Boolean = false,
    val popularMovies: Flow<PagingData<MovieDataModel>>? = emptyFlow(),
    val nowPlayingMovies: Flow<PagingData<MovieDataModel>>? = emptyFlow(),
    val topRatedMovies: Flow<PagingData<MovieDataModel>>? = emptyFlow(),
    val upcomingMovies: Flow<PagingData<MovieDataModel>>? = emptyFlow(),
    val movieGenres: List<GenreDataModel>? = null,
    val error: String? = null,
)
