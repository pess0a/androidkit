package com.pess0a.androidkit.sample.data

import com.pess0a.androidkit.sample.data.dto.CountriesDto
import retrofit2.Response
import retrofit2.http.GET

interface SampleApi {

    @GET("acountries/countries")
    suspend fun getCountries(): Response<List<CountriesDto>>
}