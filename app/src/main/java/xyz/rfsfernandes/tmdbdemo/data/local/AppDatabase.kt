package xyz.rfsfernandes.tmdbdemo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import xyz.rfsfernandes.tmdbdemo.data.local.model.CacheTimeEntity
import xyz.rfsfernandes.tmdbdemo.data.local.model.GenreEntity
import xyz.rfsfernandes.tmdbdemo.data.local.model.MovieEntity
import xyz.rfsfernandes.tmdbdemo.data.local.model.MovieRemoteKey
import xyz.rfsfernandes.tmdbdemo.data.local.model.moviedetails.MovieDetailsEntity

@Database(
    entities = [MovieEntity::class, GenreEntity::class, MovieRemoteKey::class, CacheTimeEntity::class, MovieDetailsEntity::class],
    version = 1
)
@TypeConverters(MoviesTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tmdbDAO(): TmdbDAO
}
