package com.pess0a.androidkit.core.network

import android.app.Application
import com.pess0a.androidkit.core.di.KoinConfig
import com.pess0a.androidkit.core.di.KoinInit
import org.koin.core.qualifier.named

class NetworkSecureClient {

    fun <T> provideSecureApi(apiInterface: Class<T>): T {
        val networkClient = KoinInit.getKoinApp().koin.get<NetworkClient>(named("networkClient"))
        return networkClient.provideApi(apiInterface)
    }

    class Builder(private val application: Application, private val isDebuggable: Boolean) {
        private var baseUrl: String = ""

        fun setBaseUrl(baseUrl: String): Builder {
            this.baseUrl = baseUrl
            return this
        }

        fun build(): NetworkSecureClient {
            val config = KoinConfig(application, baseUrl, isDebuggable)
            KoinInit.init(config)
            return NetworkSecureClient()
        }
    }
}