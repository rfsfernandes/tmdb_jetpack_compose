package xyz.rfsfernandes.tmdbdemo.presentation.ui.details.viewstate

import xyz.rfsfernandes.tmdbdemo.domain.model.MovieDetailsDataModel

data class MovieDetailsViewState(
    val movieDetails: MovieDetailsDataModel? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
