package xyz.rfsfernandes.tmdbdemo.data.local.model

import androidx.room.Entity
import xyz.rfsfernandes.tmdbdemo.domain.MovieHomeType

@Entity(primaryKeys = ["movieId", "language"])
data class MovieEntity(
    val movieId: Int,
    val title: String,
    val backdropPath: String?,
    val posterPath: String?,
    val language: String,
    val homeType: MovieHomeType,
)
