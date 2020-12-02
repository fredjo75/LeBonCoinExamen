package com.example.leboncoinexamen.framework.presentation

import androidx.multidex.MultiDexApplication
import com.example.leboncoinexamen.di.ApplicationComponent
import com.example.leboncoinexamen.di.DaggerApplicationComponent


open class MyApplication : MultiDexApplication() {
    var mApplicationComponent: ApplicationComponent? = null

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    open fun initAppComponent() {
        mApplicationComponent = DaggerApplicationComponent
            .factory()
            .create(this)
    }

}