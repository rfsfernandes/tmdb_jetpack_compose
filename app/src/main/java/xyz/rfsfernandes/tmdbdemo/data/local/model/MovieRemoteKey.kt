package xyz.rfsfernandes.tmdbdemo.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import xyz.rfsfernandes.tmdbdemo.domain.MovieHomeType

@Entity
data class MovieRemoteKey(
    @PrimaryKey val movieId: Int,
    val homeType: MovieHomeType,
    val language: String,
    val prevKey: Int?,
    val nextKey: Int?
)
