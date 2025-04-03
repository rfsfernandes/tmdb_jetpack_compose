package xyz.rfsfernandes.tmdbdemo.data.local.model

import androidx.annotation.StringRes
import xyz.rfsfernandes.tmdbdemo.R

enum class MovieHomeType(@StringRes val title: Int) {
    FEATURED(R.string.featured),
    NOW_PLAYING(R.string.now_playing),
    POPULAR(R.string.popular),
    TOP_RATED(R.string.top_rated),
    UPCOMING(R.string.upcoming),
}
