package com.pess0a.androidkit.core.network

import android.app.Application
import android.content.Context
import androidx.annotation.VisibleForTesting
import com.pess0a.androidkit.core.interceptors.ConnectionInterceptor
import com.pess0a.androidkit.core.providers.HttpClientProvider
import com.pess0a.androidkit.core.utils.ConnectionUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


interface NetworkClient {

    fun <T> provideApi(apiInterface: Class<T>): T

    class Builder(private val context: Context) {
        private var baseUrl = ""

        @VisibleForTesting
        internal var httpClientProvider = HttpClientProvider()

        @VisibleForTesting
        internal var connectionUtils = ConnectionUtils(context)

        private var gsonConverter = GsonConverterFactory.create()

        fun baseUrl(baseUrl: String): Builder {
            this.baseUrl = baseUrl
            return this
        }

        fun setTimeout(timeout: Long): Builder {
            httpClientProvider.setTimeout(timeout)
            return this
        }

        fun addInterceptor(interceptor: Interceptor): Builder {
            httpClientProvider.addInterceptor(interceptor)
            return this
        }

        fun build(): NetworkClient {
            if (context !is Application)
                throw IllegalArgumentException("Use application context instead to avoid memory leak!")
            if (baseUrl.isEmpty())
                throw IllegalArgumentException("Base URL required.")
            addConnectionInterceptor()
            val retrofit = buildRetrofit(baseUrl, httpClientProvider.build(), gsonConverter)
            return NetworkClientImpl(retrofit.build())
        }

        fun addLoggerInterceptor(isDebuggable : Boolean): Builder {
            if(isDebuggable) {
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                addInterceptor(logging)
            }
            return this
        }

        private fun addConnectionInterceptor() {
            val interceptor = ConnectionInterceptor(connectionUtils)

            addInterceptor(interceptor)
        }

        private fun buildRetrofit(baseUrl: String, httpClient : OkHttpClient, converter: GsonConverterFactory ): Retrofit.Builder {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converter)
                .client(httpClient)
        }
    }
}