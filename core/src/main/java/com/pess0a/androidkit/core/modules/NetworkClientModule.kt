package com.pess0a.androidkit.core.modules

import com.pess0a.androidkit.core.network.NetworkClient
import com.pess0a.androidkit.core.di.KoinConfig
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal class NetworkClientModule(private val koinConfig: KoinConfig) {

    fun provide() = listOf(networkModule)

    private val networkModule = module {
        single(named("networkClient")) { buildNetworkClient() }
    }

    private fun buildNetworkClient(): NetworkClient {
        if(koinConfig.isDebuggable) {

        }
        return NetworkClient.Builder(koinConfig.application)
            .baseUrl(koinConfig.baseUrl)
            .addLoggerInterceptor(koinConfig.isDebuggable)
            .setTimeout(30)
            .build()
    }
}