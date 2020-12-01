package com.example.leboncoinexamen.framework.presentation

import androidx.multidex.MultiDexApplication
import com.example.leboncoinexamen.di.ApplicationComponent
import com.example.leboncoinexamen.di.ApplicationModule
import com.example.leboncoinexamen.di.DaggerApplicationComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
open class MyApplication : MultiDexApplication() {
    var mApplicationComponent: ApplicationComponent? = null

    override fun onCreate() {
        super.onCreate()
        //mApplicationComponent = getApplicationComponent()
        initAppComponent()

    }
    open fun initAppComponent(){
        mApplicationComponent = DaggerApplicationComponent
            .factory()
            .create(this)
    }
    /*fun getApplicationComponent(): ApplicationComponent? {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
        }
        return mApplicationComponent
    }*/
}