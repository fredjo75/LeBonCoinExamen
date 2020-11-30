package com.example.leboncoinexamen.framework.presentation.photo

import androidx.lifecycle.ViewModel
import com.example.leboncoinexamen.domain.repositories.AlbumRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoViewModel
@Inject
constructor(
    private val albumRepository: AlbumRepository
) : ViewModel() {
}