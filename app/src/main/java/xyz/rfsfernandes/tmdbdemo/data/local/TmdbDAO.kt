package xyz.rfsfernandes.tmdbdemo.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import xyz.rfsfernandes.tmdbdemo.data.local.model.CacheTimeEntity
import xyz.rfsfernandes.tmdbdemo.data.local.model.GenreEntity
import xyz.rfsfernandes.tmdbdemo.data.local.model.MovieEntity
import xyz.rfsfernandes.tmdbdemo.data.local.model.MovieRemoteKey

@Dao
interface TmdbDAO {

    // Genres
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieGenres(genres: List<GenreEntity>)

    @Query("SELECT * FROM GenreEntity WHERE language = :language")
    suspend fun getMovieGenres(language: String): List<GenreEntity>

    @Query("DELETE FROM GenreEntity WHERE language = :language")
    suspend fun deleteMovieGenres(language: String)

    @Query("DELETE FROM GenreEntity")
    suspend fun deleteAllMovieGenres()

    // Movies
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movie: List<MovieEntity>)

    @Query("SELECT * FROM MovieEntity WHERE language = :language AND homeType LIKE '%' || :homeType || '%'")
    fun getMoviesPaged(language: String, homeType: String): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM MovieEntity WHERE movieId = :movieId")
    suspend fun getMovie(movieId: Int): List<MovieEntity>

    @Query("SELECT * FROM MovieEntity WHERE language = :language AND homeType = :homeType")
    suspend fun getAllMovies(language: String, homeType: String): List<MovieEntity>

    @Query("DELETE FROM MovieEntity WHERE language = :language AND homeType LIKE '%' || :homeType || '%'")
    suspend fun deleteMovies(language: String, homeType: String)

    // Movies remote keys
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllKeys(remoteKeys: List<MovieRemoteKey>)

    @Query("SELECT * FROM movieremotekey WHERE movieId = :id AND language = :language AND homeType LIKE '%' || :homeType || '%'")
    suspend fun getRemoteKeyByMovieId(id: Int, language: String, homeType: String): MovieRemoteKey?

    @Query("DELETE FROM movieremotekey WHERE language = :language AND homeType LIKE '%' || :homeType || '%'")
    suspend fun clearRemoteKeysFromType(language: String, homeType: String)

    // Cache
    @Query("SELECT * FROM CacheTimeEntity WHERE language = :language")
    suspend fun getCacheTime(language: String): CacheTimeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCacheTime(cacheTime: CacheTimeEntity)

    @Query("DELETE FROM CacheTimeEntity WHERE language = :language")
    suspend fun deleteCacheTime(language: String)

}
