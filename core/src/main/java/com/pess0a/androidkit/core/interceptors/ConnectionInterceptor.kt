package com.pess0a.androidkit.core.interceptors

import com.pess0a.androidkit.core.utils.ConnectionUtils
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.lang.Exception

internal class ConnectionInterceptor (private val connectionUtils: ConnectionUtils): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        runBlocking {
            val hasConnection = connectionUtils.isConnectionOn()
            if(!hasConnection) {
                throw Exception("No Connection")
            }
        }
        return chain.proceed(chain.request())
    }

}