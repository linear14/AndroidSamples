package com.dongldh.carrotimagepickerexample.util

import android.app.Application
import android.content.Context

class App: Application() {

    companion object {
        private var instance: App? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }


}