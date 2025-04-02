package xyz.rfsfernandes.tmdbdemo.data.local

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import xyz.rfsfernandes.tmdbdemo.data.local.model.MovieHomeType

class MoviesTypeConverters {

    private val moshi: Moshi = Moshi.Builder().build()
    private val genreAdapter = moshi.adapter<List<Int>>(List::class.java)

    @TypeConverter
    fun fromGenreIdsList(genreIds: List<Int>): String {
        return genreAdapter.toJson(genreIds)
    }

    @TypeConverter
    fun toGenreIdsList(genreIdsJson: String): List<Int> {
        return genreAdapter.fromJson(genreIdsJson) ?: emptyList()
    }
}


