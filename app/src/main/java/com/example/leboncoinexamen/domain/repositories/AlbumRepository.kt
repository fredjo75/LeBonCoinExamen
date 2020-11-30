package com.example.leboncoinexamen.domain.repositories

import androidx.lifecycle.LiveData
import com.example.leboncoinexamen.domain.model.Album

interface AlbumRepository {
    val allAlbums: LiveData<List<Album>>
    suspend fun fetchAlbums()
}