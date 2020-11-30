package com.example.leboncoinexamen.framework.presentation.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leboncoinexamen.domain.model.Album
import com.example.leboncoinexamen.domain.repositories.AlbumRepository
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumViewModel
@Inject
constructor(
    private val albumRepository: AlbumRepository
) : ViewModel() {

    private val _status = MutableLiveData<AlbumApiStatus>()
    val status: LiveData<AlbumApiStatus>
        get() = _status

    private val _response: LiveData<List<Album>> = albumRepository.allAlbums
    val response: LiveData<List<Album>>
        get() = _response

    companion object {
        private const val TAG: String = "AlbumListViewModel"
    }

    init {
        viewModelScope.launch {
            albumRepository.fetchAlbums()
        }
    }

    enum class AlbumApiStatus { LOADING, ERROR, DONE }
}