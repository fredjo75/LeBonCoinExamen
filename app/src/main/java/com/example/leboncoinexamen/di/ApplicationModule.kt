package com.example.leboncoinexamen.di

import androidx.lifecycle.ViewModelProvider
import com.example.leboncoinexamen.data.datasource.db.AlbumDatabase
import com.example.leboncoinexamen.data.datasource.db.AlbumDatabaseDOA
import com.example.leboncoinexamen.data.datasource.network.AlbumApi
import com.example.leboncoinexamen.data.datasource.network.AlbumApiService
import com.example.leboncoinexamen.data.oop.mapper.DBDataMapper
import com.example.leboncoinexamen.data.oop.mapper.NetworkDataMapper
import com.example.leboncoinexamen.data.reporitory.AlbumRepositoryImpl
import com.example.leboncoinexamen.domain.repositories.AlbumRepository
import com.example.leboncoinexamen.framework.presentation.MyApplication
import com.example.leboncoinexamen.framework.presentation.common.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ApplicationModule {

    @Singleton
    @JvmStatic
    @Provides
    fun provideAlbumDatabaseDOA(myApplication: MyApplication): AlbumDatabaseDOA {
        return AlbumDatabase.getDatabase(myApplication).albumDatabaseDOA
    }

    @Singleton
    @JvmStatic
    @Provides
    fun provideAlbumApiService(): AlbumApiService {
        return AlbumApi.retrofitService
    }

    @Singleton
    @JvmStatic
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

    @Singleton
    @JvmStatic
    @Provides
    fun provideAlbumListViewModelFactory(
        albumRepository: AlbumRepository
    ): ViewModelProvider.Factory {
        return ViewModelFactory(
            albumRepository = albumRepository
        )
    }

}