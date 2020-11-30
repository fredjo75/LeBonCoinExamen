package com.example.leboncoinexamen.framework.presentation.album

import com.example.leboncoinexamen.domain.model.Album

sealed class DataItem {
    data class AlbumItem(val album: Album) : DataItem() {
        override val id = album.id
    }

    data class HeaderItem(val albumId: Int) : DataItem() {
        override val id = Int.MIN_VALUE
    }

    abstract val id: Int?
}