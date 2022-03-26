package com.pess0a.androidkit.core.di

import com.pess0a.androidkit.core.modules.NetworkClientModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.dsl.koinApplication
import java.lang.IllegalArgumentException

internal object KoinInit {

    private lateinit var koinApp: KoinApplication

    fun init(config: KoinConfig) {
        val networkModules = buildModules(config)
        koinApp = koinApplication {
            androidContext(config.application)
            modules(networkModules)
        }
    }

    fun getKoinApp(): KoinApplication {
        if (!KoinInit::koinApp.isInitialized) {
            throw IllegalArgumentException("KoinApp not init")
        }
        return koinApp
    }

    private fun buildModules(config: KoinConfig): List<Module> {
        return NetworkClientModule(config).provide()
    }

}