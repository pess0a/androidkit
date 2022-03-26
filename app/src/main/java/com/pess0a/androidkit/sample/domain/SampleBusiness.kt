package com.pess0a.androidkit.sample.domain

import com.pess0a.androidkit.sample.data.SampleRepository
import com.pess0a.androidkit.sample.data.dto.CountriesDto
import com.pess0a.androidkit.sample.domain.model.CountriesModel
import kotlinx.coroutines.flow.flow

class SampleBusiness(private val sampleRepository: SampleRepository) {

    fun getCountries() = flow {
        val apiResult = sampleRepository.getCountries()
        val data = apiResult.getBodyOrThrow()
        val countriesList = data.map { it.toCountriesModel() }
        emit(countriesList)
    }

    private fun CountriesDto.toCountriesModel(): CountriesModel {
        return CountriesModel(
            name = this.name
        )
    }
}