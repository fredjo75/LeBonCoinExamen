package com.example.leboncoinexamen.di

import com.example.leboncoinexamen.framework.presentation.MainActivity
import com.example.leboncoinexamen.framework.presentation.album.AlbumFragment
import com.example.leboncoinexamen.framework.presentation.photo.PhotoFragment
import dagger.Component

@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(albumFragment: AlbumFragment)

    fun inject(albumFragment: PhotoFragment)
}