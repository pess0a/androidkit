package com.pess0a.androidkit.core.providers

import java.util.concurrent.TimeUnit
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient

internal class HttpClientProvider() {

    private var timeout = DEFAULT_TIMEOUT
    private val interceptors: MutableList<Interceptor> = mutableListOf()
    private var authenticator: Authenticator? = null


    fun setTimeout(timeout: Long): HttpClientProvider {
        this.timeout = timeout
        return this
    }

    fun addInterceptor(interceptor: Interceptor): HttpClientProvider {
        interceptors.add(interceptor)
        return this
    }

    fun addAuthenticator(authenticator: Authenticator): HttpClientProvider {
        this.authenticator = authenticator
        return this
    }

    fun build(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .addInterceptors(interceptors)
            .addAuthenticatorIfNeeded(authenticator)

        return builder.build()
    }

    private fun OkHttpClient.Builder.addInterceptors(
        interceptor: List<Interceptor>
    ): OkHttpClient.Builder {
        interceptor.iterator().forEach { this.addInterceptor(it) }
        return this
    }

    private fun OkHttpClient.Builder.addAuthenticatorIfNeeded(
        authenticator: Authenticator?
    ): OkHttpClient.Builder {
        if (authenticator != null) this.authenticator(authenticator)
        return this
    }

    companion object {
        private const val DEFAULT_TIMEOUT = 30L
    }
}