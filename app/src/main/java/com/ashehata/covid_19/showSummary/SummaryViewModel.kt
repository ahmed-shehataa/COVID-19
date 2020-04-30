package com.ashehata.covid_19.showSummary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import java.lang.Exception

class SummaryViewModel(private val useCase: SummaryUseCase) : ViewModel() {

    private lateinit var mJob: Job
    private val _viewState = MutableLiveData<SummaryViewState>()

    val viewState: LiveData<SummaryViewState>
        get() = _viewState

    private fun getCurrentViewState() = _viewState.value

    init {
        _viewState.value = SummaryViewState(
            null,
            null,
            null,
            true,
            false,
            true,
            ""
        )
        loadSummary()
    }

    private fun loadSummary() {
        // Update view state
        //_viewState.value = useCase.getSummary()

        /*
        https://medium.com/@cesarmcferreira/how-to-use-the-new-android-viewmodelscope-in-clean-architecture-2a33aac959ee
         */

        mJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = useCase.getSummary()

                withContext(Dispatchers.Main) {
                    if (result.countries.isNotEmpty()) {
                        _viewState.value = getCurrentViewState()?.copy(
                            countries = result.countries,
                            global = result.global,
                            error = null,
                            loading = false,
                            empty = false
                        )
                    } else {
                        _viewState.value = getCurrentViewState()?.copy(
                            error = null,
                            loading = false,
                            empty = true
                        )
                    }
                }
            } catch (e: Exception) {
               withContext(Dispatchers.Main) {
                   delay(800)
                   _viewState.value = getCurrentViewState()?.copy(
                       error = e,
                       loading = false,
                       empty = true
                   )
               }
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        if (!mJob.isCancelled) {
            mJob.cancel()
        }
    }
}