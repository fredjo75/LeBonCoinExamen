package com.example.leboncoinexamen.data.oop.mapper

import com.example.leboncoinexamen.data.datasource.db.model.DbAlbum
import com.example.leboncoinexamen.data.datasource.network.model.NetworkAlbum

class NetworkDataMapper : Mapper<NetworkAlbum, DbAlbum> {
    override fun map(input: NetworkAlbum): DbAlbum {
        return DbAlbum(
            input.albumId ?: 0,
            input.id ?: 0,
            input.title.orEmpty(),
            input.url.orEmpty(),
            input.thumbnailUrl.orEmpty(),
        )
    }
}
