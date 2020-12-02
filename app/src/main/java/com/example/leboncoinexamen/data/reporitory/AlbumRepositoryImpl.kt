package com.example.leboncoinexamen.data.reporitory

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.leboncoinexamen.data.datasource.db.AlbumDatabaseDOA
import com.example.leboncoinexamen.data.datasource.db.model.DbAlbum
import com.example.leboncoinexamen.data.datasource.network.AlbumApiService
import com.example.leboncoinexamen.data.datasource.network.model.NetworkAlbum
import com.example.leboncoinexamen.data.oop.mapper.Mapper
import com.example.leboncoinexamen.domain.model.Album
import com.example.leboncoinexamen.domain.repositories.AlbumRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumRepositoryImpl @Inject constructor(
    private var albumDao: AlbumDatabaseDOA,
    private var albumApiService: AlbumApiService,
    private var dbMapper: Mapper<DbAlbum, Album>,
    private var networkMapper: Mapper<NetworkAlbum, DbAlbum>,
) : AlbumRepository {

    companion object {
        private const val TAG: String = "AlbumRepositoryImpl"
    }

    init {
        GlobalScope.launch { fetchAlbums() }
    }

    override val allAlbums: LiveData<List<Album>> =
        Transformations.map(albumDao.getAll()) { list -> list.map { dbMapper.map(it) } }

    @WorkerThread
    private fun refreshAlbums(albums: List<NetworkAlbum>) {
        albumDao.insertAll(albums.map { networkMapper.map(it) })
    }

    @WorkerThread
    override suspend fun fetchAlbums() {
        try {
            albumApiService.getAlbums().enqueue(object : Callback<List<NetworkAlbum>> {
                override fun onResponse(
                    call: Call<List<NetworkAlbum>>,
                    response: retrofit2.Response<List<NetworkAlbum>>
                ) {
                    Log.d(TAG, "Reached this place")
                    if (response.isSuccessful) {
                        GlobalScope.launch { refreshAlbums(response.body()!!) }
                    }
                    Log.d(TAG, "Response returned by website is : " + response.code())
                }

                override fun onFailure(call: Call<List<NetworkAlbum>>, t: Throwable) {
                    Log.e(TAG, "onFailure", t)
                }
            })
        } catch (e: Exception) {
            Log.e(TAG, "fetchAlbums", e)
        }
    }
}