package xyz.rfsfernandes.tmdbdemo.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["movieId","language"])
data class MovieEntity(
    val movieId: Int,
    val title: String,
    val originalTitle: String,
    val releaseDate: String,
    val overview: String,
    val backdropPath: String?,
    val posterPath: String?,
    val originalLanguage: String,
    val voteAverage: Double,
    val voteCount: Int,
    val popularity: Double,
    val adult: Boolean,
    val video: Boolean,
    val genreIds: List<Int>,
    val language: String,
    val homeType: MovieHomeType,
)
