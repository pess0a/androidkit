package com.pess0a.androidkit.sample.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.pess0a.androidkit.core.UIState
import com.pess0a.androidkit.databinding.ActivitySampleBinding
import com.pess0a.androidkit.sample.domain.model.CountriesModel
import org.koin.android.ext.android.inject

class SampleActivity : AppCompatActivity() {

    lateinit var binding : ActivitySampleBinding
    private val viewModel: SampleViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.uiState.observe(this, ::handleState)
    }

    private fun handleState(state: UIState<Throwable, List<CountriesModel>>) {
        binding.loadingView.isVisible = state is UIState.Loading
        binding.textViewResult.isVisible = state is UIState.Success
        binding.errorView.isVisible = state is UIState.Error

        state.apply {
            onSuccess { binding.textViewResult.text = it.toString() }
            onError { binding.textViewResult }
        }
    }
}
