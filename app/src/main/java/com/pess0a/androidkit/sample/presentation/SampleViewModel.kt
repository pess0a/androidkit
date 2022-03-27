package com.pess0a.androidkit.sample.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pess0a.androidkit.core.SingleLiveEvent
import com.pess0a.androidkit.core.UIState
import com.pess0a.androidkit.sample.domain.SampleBusiness
import com.pess0a.androidkit.sample.domain.model.CountriesModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SampleViewModel(
    private val coroutineContext: CoroutineContext,
    private val sampleBusiness: SampleBusiness
) : ViewModel() {

    init {
        getCountries()
    }

    private val _uiState = SingleLiveEvent<UIState<Throwable, List<CountriesModel>>>()
    val uiState: LiveData<UIState<Throwable, List<CountriesModel>>> = _uiState

    private fun getCountries() = viewModelScope.launch(coroutineContext) {
        _uiState.value = UIState.Loading
        sampleBusiness.getCountries()
            .catch {
                _uiState.value = UIState.Error(it)
            }
            .collect {
                _uiState.value = UIState.Success(it)
            }
    }
}
