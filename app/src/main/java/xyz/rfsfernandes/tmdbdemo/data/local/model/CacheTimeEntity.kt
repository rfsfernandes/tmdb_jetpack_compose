package xyz.rfsfernandes.tmdbdemo.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CacheTimeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val cacheAtDate: Long,
    val cacheDuration: Long,
    val language: String,
)
