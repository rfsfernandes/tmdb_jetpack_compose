package xyz.rfsfernandes.tmdbdemo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import xyz.rfsfernandes.tmdbdemo.data.local.model.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun tmdbDAO(): TmdbDAO
}
