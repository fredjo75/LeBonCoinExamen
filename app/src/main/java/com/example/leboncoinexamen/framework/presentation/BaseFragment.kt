package com.example.leboncoinexamen.framework.presentation

import androidx.fragment.app.Fragment
import com.example.leboncoinexamen.di.ApplicationComponent

open class BaseFragment : Fragment() {
    fun getAppComponent(): ApplicationComponent {
        return activity?.run {
            (application as MyApplication).getApplicationComponent()
        } ?: throw Exception("AppComponent is null.")
    }
}