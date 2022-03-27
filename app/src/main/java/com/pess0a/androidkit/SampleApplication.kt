package com.pess0a.androidkit

import android.app.Application
import com.pess0a.androidkit.sample.di.SampleModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SampleApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SampleApplication)
            modules(SampleModule)
        }
    }
}