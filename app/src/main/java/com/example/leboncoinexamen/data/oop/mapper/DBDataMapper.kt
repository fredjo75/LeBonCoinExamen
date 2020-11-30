package com.example.leboncoinexamen.data.oop.mapper

import com.example.leboncoinexamen.data.datasource.db.model.DbAlbum
import com.example.leboncoinexamen.domain.model.Album


class DBDataMapper : Mapper<DbAlbum, Album> {
    override fun map(input: DbAlbum): Album {
        return Album(
            input.albumId ?: 0,
            input.id ?: 0,
            input.title.orEmpty(),
            input.url.orEmpty(),
            input.thumbnailUrl.orEmpty(),
        )
    }
}