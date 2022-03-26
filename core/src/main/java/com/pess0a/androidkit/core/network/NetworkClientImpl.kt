package com.pess0a.androidkit.core.network

import retrofit2.Retrofit

internal class NetworkClientImpl(private val retrofit: Retrofit) : NetworkClient {

    override fun <T> provideApi(apiInterface: Class<T>): T {
        return retrofit.create(apiInterface)
    }


}