package xyz.rfsfernandes.tmdbdemo.data.mappers

import xyz.rfsfernandes.tmdbdemo.data.local.model.GenreEntity
import xyz.rfsfernandes.tmdbdemo.data.remote.model.genre.Genre

fun Genre.toEntity(language: String): GenreEntity {
    return GenreEntity(
        id = null,
        genreId = id,
        name = name,
        language = language
    )
}
