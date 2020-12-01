package com.example.leboncoinexamen.framework.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.leboncoinexamen.R

class MainActivity : AppCompatActivity() {
    private val KEY_CURRENT_POSITION = "com.google.samples.gridtopager.key.currentPosition"

    companion object {
        public var currentPosition: Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(KEY_CURRENT_POSITION, 0)
            // Return here to prevent adding additional GridFragments when changing orientation.
            return
        }
        (application as MyApplication).mApplicationComponent?.inject(this)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_CURRENT_POSITION, currentPosition)
    }
}