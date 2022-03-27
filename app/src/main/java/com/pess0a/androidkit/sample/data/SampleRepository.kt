package com.pess0a.androidkit.sample.data

import com.pess0a.androidkit.core.api.ApiExecutor

class SampleRepository(private val apiExecutor: ApiExecutor, private val sampleApi : SampleApi) {

    suspend fun getCountries() = apiExecutor.executeRequest { sampleApi.getCountries() }

}
