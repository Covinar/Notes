package com.example.notes.app

import android.app.Application
import com.example.notes.di.SingletonComponent

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        SingletonComponent.init(this)
    }

}