package com.example.pgyebu

import android.app.Application
import android.content.Context

class MyApplication : Application(){

    init {
        instance = this
    }

    override fun onCreate() {
        prefs = MySharedPreferences(this)
        super.onCreate()
    }

    companion object {
        private var instance: MyApplication? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
        lateinit var prefs: MySharedPreferences
    }

}