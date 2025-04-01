package xyz.rfsfernandes.tmdbdemo.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @PrimaryKey val id: Int
)
