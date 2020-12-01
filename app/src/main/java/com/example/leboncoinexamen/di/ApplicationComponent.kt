package com.example.leboncoinexamen.di

import com.example.leboncoinexamen.framework.presentation.MainActivity
import com.example.leboncoinexamen.framework.presentation.MyApplication
import com.example.leboncoinexamen.framework.presentation.album.AlbumFragment
import com.example.leboncoinexamen.framework.presentation.photo.PhotoPagerFragment
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@FlowPreview
@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    @Component.Factory
    interface Factory{

        fun create(@BindsInstance app: MyApplication): ApplicationComponent
    }

    fun inject(mainActivity: MainActivity)

    fun inject(albumFragment: AlbumFragment)

    fun inject(albumPagerFragment: PhotoPagerFragment)
}