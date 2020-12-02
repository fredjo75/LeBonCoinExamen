package com.example.leboncoinexamen.framework.presentation.album

import androidx.arch.core.util.Function
import androidx.lifecycle.*
import com.example.leboncoinexamen.domain.model.Album
import com.example.leboncoinexamen.domain.repositories.AlbumRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumViewModel
@Inject
constructor(
    albumRepository: AlbumRepository
) : ViewModel() {

    private val _albums: LiveData<List<Album>> = albumRepository.allAlbums
    val albums: LiveData<List<Album>>
        get() = _albums

    private val _adapterAlbums: LiveData<List<DataItem>> = Transformations.map(albums, Function {
        return@Function addHeaderAndSubmitList(it)
    })
    val adapterAlbums: LiveData<List<DataItem>>
        get() = _adapterAlbums

    private fun addHeaderAndSubmitList(list: List<Album>?): List<DataItem> {
        return when (list) {
            null -> listOf()
            else -> addheaders(list.map { DataItem.AlbumItem(it) })
        }
    }

    private fun addheaders(list: List<DataItem>): List<DataItem> {
        val mutList = list.toMutableList()
        val iterator = mutList.listIterator()
        var albumId: Int = Int.MIN_VALUE
        for (item in iterator) {
            item as DataItem.AlbumItem
            if (item.album.albumId != null && albumId != item.album.albumId) {
                albumId = item.album.albumId
                iterator.previous()
                iterator.add(DataItem.HeaderItem(item.album.albumId))
                iterator.next()
            }
        }

        return mutList.toList()
    }
}