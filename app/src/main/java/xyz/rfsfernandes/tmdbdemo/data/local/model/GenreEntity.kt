package xyz.rfsfernandes.tmdbdemo.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GenreEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val genreId: Int,
    val name: String,
    val language: String,
)
