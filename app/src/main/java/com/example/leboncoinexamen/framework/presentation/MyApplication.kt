package com.example.leboncoinexamen.framework.presentation

import androidx.multidex.MultiDexApplication
import com.example.leboncoinexamen.di.ApplicationComponent
import com.example.leboncoinexamen.di.ApplicationModule
import com.example.leboncoinexamen.di.DaggerApplicationComponent


class MyApplication : MultiDexApplication() {
    private var mApplicationComponent: ApplicationComponent? = null

    override fun onCreate() {
        super.onCreate()
        mApplicationComponent = getApplicationComponent()
    }

    fun getApplicationComponent(): ApplicationComponent? {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
        }
        return mApplicationComponent
    }
}