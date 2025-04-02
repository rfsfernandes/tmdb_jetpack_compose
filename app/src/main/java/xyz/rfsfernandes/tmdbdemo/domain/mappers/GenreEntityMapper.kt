package xyz.rfsfernandes.tmdbdemo.domain.mappers

import xyz.rfsfernandes.tmdbdemo.data.local.model.GenreEntity
import xyz.rfsfernandes.tmdbdemo.domain.model.GenreDataModel

fun GenreEntity.toDomain(): GenreDataModel {
    return GenreDataModel(
        id = genreId,
        name = name,
    )
}
