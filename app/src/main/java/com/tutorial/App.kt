package com.tutorial

import android.app.Application
import com.tutorial.domain.di.koinModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoinModule()
    }

    private fun startKoinModule() {
        startKoin{
            modules(koinModule)
                .androidContext(this@App)
        }
    }
}