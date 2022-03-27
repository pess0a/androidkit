package com.pess0a.androidkit.sample.di

import android.app.Application
import com.pess0a.androidkit.BuildConfig
import com.pess0a.androidkit.core.api.ApiExecutor
import com.pess0a.androidkit.core.network.NetworkSecureClient
import com.pess0a.androidkit.sample.data.SampleApi
import com.pess0a.androidkit.sample.data.SampleRepository
import com.pess0a.androidkit.sample.domain.SampleBusiness
import com.pess0a.androidkit.sample.presentation.SampleViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var SampleModule = module {
    viewModel { SampleViewModel(Dispatchers.Main, get()) }
    single { SampleBusiness(get()) }
    single { SampleRepository(get(), get()) }
    single { provideApiService(get()) }



    single { provideNetworkClient(androidApplication()) }
    single { ApiExecutor() }

}

private fun provideNetworkClient(application: Application): NetworkSecureClient {
    val isDebuggable = BuildConfig.DEBUG
    return NetworkSecureClient.Builder(application, isDebuggable)
        .setBaseUrl("https://api.sampleapis.com/")
        .build()
}

private fun provideApiService(networkSecureClient: NetworkSecureClient): SampleApi {
    return networkSecureClient.provideSecureApi(SampleApi::class.java)
}
