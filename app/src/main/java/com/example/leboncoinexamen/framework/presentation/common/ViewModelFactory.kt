package com.example.leboncoinexamen.framework.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.leboncoinexamen.domain.repositories.AlbumRepository
import com.example.leboncoinexamen.framework.presentation.album.AlbumViewModel
import com.example.leboncoinexamen.framework.presentation.photo.PhotoViewModel
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ViewModelFactory
@Inject
constructor(
    private val albumRepository: AlbumRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {

            AlbumViewModel::class.java -> {
                AlbumViewModel(
                    albumRepository = albumRepository
                ) as T
            }

            PhotoViewModel::class.java -> {
                PhotoViewModel(
                    albumRepository = albumRepository
                ) as T
            }

            else -> {
                throw IllegalArgumentException("unknown model class $modelClass")
            }
        }
    }
}


