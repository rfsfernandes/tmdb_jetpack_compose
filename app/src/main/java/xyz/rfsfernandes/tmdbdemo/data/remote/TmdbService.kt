package xyz.rfsfernandes.tmdbdemo.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import xyz.rfsfernandes.tmdbdemo.data.remote.model.genre.GenreResponse
import xyz.rfsfernandes.tmdbdemo.data.remote.model.movie.MovieResponse

interface TmdbService {

    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query("language") language: String?,
//        @Header("Authorization") header: String = "Bearer ${BuildConfig.API_KEY}"
    ): Response<GenreResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String?,
        @Query("page") page: Int?,
    ): Response<MovieResponse>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String?,
        @Query("page") page: Int?,
    ): Response<MovieResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language: String?,
        @Query("page") page: Int?,
    ): Response<MovieResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language") language: String?,
        @Query("page") page: Int?,
    ): Response<MovieResponse>

    @GET("discover/movie?include_adult=true&include_video=false&language=en-US&page=1&sort_by=vote_count.desc")
    suspend fun getFeaturedMovies(
        @Query("language") language: String?,
    ): Response<MovieResponse>
}
