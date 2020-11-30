package com.example.leboncoinexamen.di

import androidx.lifecycle.ViewModelProvider
import com.example.leboncoinexamen.data.datasource.db.AlbumDatabase
import com.example.leboncoinexamen.data.datasource.db.AlbumDatabaseDOA
import com.example.leboncoinexamen.data.network.AlbumApi
import com.example.leboncoinexamen.data.network.AlbumApiService
import com.example.leboncoinexamen.data.oop.mapper.DBDataMapper
import com.example.leboncoinexamen.data.oop.mapper.NetworkDataMapper
import com.example.leboncoinexamen.data.reporitory.AlbumRepositoryImpl
import com.example.leboncoinexamen.domain.repositories.AlbumRepository
import com.example.leboncoinexamen.framework.presentation.MyApplication
import com.example.leboncoinexamen.framework.presentation.common.ViewModelFactory
import dagger.Module
import dagger.Provides


@Module
class ApplicationModule(private val myApplication: MyApplication) {

    @Provides
    fun provideAlbumDatabaseDOA(): AlbumDatabaseDOA {
        return AlbumDatabase.getDatabase(myApplication).albumDatabaseDOA
    }

    @Provides
    fun provideAlbumApiService(): AlbumApiService {
        return AlbumApi.retrofitService
    }

    @Provides
    fun provideAlbumRepository(
        albumDao: AlbumDatabaseDOA,
        albumApiService: AlbumApiService
    ): AlbumRepository {
        return AlbumRepositoryImpl(
            albumDao,
            albumApiService,
            DBDataMapper(),
            NetworkDataMapper()
        )
    }

    @Provides
    fun provideAlbumListViewModelFactory(
        albumRepository: AlbumRepository
    ): ViewModelProvider.Factory {
        return ViewModelFactory(
            albumRepository = albumRepository
        )
    }

}