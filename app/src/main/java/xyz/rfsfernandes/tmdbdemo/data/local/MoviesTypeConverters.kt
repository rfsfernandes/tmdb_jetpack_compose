package xyz.rfsfernandes.tmdbdemo.data.local

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import xyz.rfsfernandes.tmdbdemo.data.remote.model.genre.Genre
import xyz.rfsfernandes.tmdbdemo.data.remote.model.movie.details.MovieCollection
import xyz.rfsfernandes.tmdbdemo.data.remote.model.movie.details.ProductionCompanies
import xyz.rfsfernandes.tmdbdemo.data.remote.model.movie.details.ProductionCountries
import xyz.rfsfernandes.tmdbdemo.data.remote.model.movie.details.SpokenLanguages

class MoviesTypeConverters {

    private val moshi: Moshi = Moshi.Builder().build()
    private val genreAdapter = moshi.adapter<List<String>>(List::class.java)

    // String
    @TypeConverter
    fun fromStringList(valueStringList: List<String>?): String? {
        return if (valueStringList == null) {
            null
        } else {
            val listType = Types.newParameterizedType(List::class.java, String::class.java)
            val adapter: JsonAdapter<List<String>> = moshi.adapter(listType)
            adapter.toJson(valueStringList)
        }
    }

    @TypeConverter
    fun toStringList(valueString: String?): List<String>? {
        return if (valueString == null) {
            null
        } else {
            val listType =
                Types.newParameterizedType(List::class.java, String::class.java)
            val adapter: JsonAdapter<List<String>> = moshi.adapter(listType)
            adapter.fromJson(valueString)
        }
    }

    // Genres
    @TypeConverter
    fun fromGenreList(genreList: List<Genre>?): String? {
        return if (genreList == null) {
            null
        } else {
            val listType = Types.newParameterizedType(List::class.java, Genre::class.java)
            val adapter: JsonAdapter<List<Genre>> = moshi.adapter(listType)
            adapter.toJson(genreList)
        }
    }

    @TypeConverter
    fun toGenreList(genreString: String?): List<Genre>? {
        return if (genreString == null) {
            null
        } else {
            val listType =
                Types.newParameterizedType(List::class.java, Genre::class.java)
            val adapter: JsonAdapter<List<Genre>> = moshi.adapter(listType)
            adapter.fromJson(genreString)
        }
    }

    // ProductionCompanies
    @TypeConverter
    fun fromProductionCompaniesList(productionCompaniesList: List<ProductionCompanies>?): String? {
        return if (productionCompaniesList == null) {
            null
        } else {
            val listType =
                Types.newParameterizedType(List::class.java, ProductionCompanies::class.java)
            val adapter: JsonAdapter<List<ProductionCompanies>> = moshi.adapter(listType)
            adapter.toJson(productionCompaniesList)
        }
    }

    @TypeConverter
    fun toProductionCompaniesList(productionCompaniesListString: String?): List<ProductionCompanies>? {
        return if (productionCompaniesListString == null) {
            null
        } else {
            val listType =
                Types.newParameterizedType(List::class.java, ProductionCompanies::class.java)
            val adapter: JsonAdapter<List<ProductionCompanies>> = moshi.adapter(listType)
            adapter.fromJson(productionCompaniesListString)
        }
    }

    // SpokenLanguages
    @TypeConverter
    fun fromSpokenLanguagesList(spokenLanguagesList: List<SpokenLanguages>?): String? {
        return if (spokenLanguagesList == null) {
            null
        } else {
            val listType =
                Types.newParameterizedType(List::class.java, SpokenLanguages::class.java)
            val adapter: JsonAdapter<List<SpokenLanguages>> = moshi.adapter(listType)
            adapter.toJson(spokenLanguagesList)
        }
    }

    @TypeConverter
    fun toSpokenLanguagesList(spokenLanguagesListString: String?): List<SpokenLanguages>? {
        return if (spokenLanguagesListString == null) {
            null
        } else {
            val listType =
                Types.newParameterizedType(List::class.java, SpokenLanguages::class.java)
            val adapter: JsonAdapter<List<SpokenLanguages>> = moshi.adapter(listType)
            adapter.fromJson(spokenLanguagesListString)
        }
    }

    // ProductionCountries
    @TypeConverter
    fun fromProductionCountriesList(productionCountriesList: List<ProductionCountries>?): String? {
        return if (productionCountriesList == null) {
            null
        } else {
            val listType =
                Types.newParameterizedType(List::class.java, ProductionCountries::class.java)
            val adapter: JsonAdapter<List<ProductionCountries>> = moshi.adapter(listType)
            adapter.toJson(productionCountriesList)
        }
    }

    @TypeConverter
    fun toProductionCountriesList(productionCountriesListString: String?): List<ProductionCountries>? {
        return if (productionCountriesListString == null) {
            null
        } else {
            val listType =
                Types.newParameterizedType(List::class.java, ProductionCountries::class.java)
            val adapter: JsonAdapter<List<ProductionCountries>> = moshi.adapter(listType)
            adapter.fromJson(productionCountriesListString)
        }
    }

    // MovieCollection
    @TypeConverter
    fun fromMovieCollection(movieCollection: MovieCollection?): String? {
        return movieCollection?.let {
            val adapter: JsonAdapter<MovieCollection> = moshi.adapter(MovieCollection::class.java)
            adapter.toJson(it)
        }
    }

    @TypeConverter
    fun toMovieCollection(movieCollectionString: String?): MovieCollection? {
        return movieCollectionString?.let {
            val adapter: JsonAdapter<MovieCollection> = moshi.adapter(MovieCollection::class.java)
            adapter.fromJson(it)
        }
    }
}


