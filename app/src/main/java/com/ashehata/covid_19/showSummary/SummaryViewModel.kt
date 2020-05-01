package com.ashehata.covid_19.showSummary

import android.util.Log
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
        /*
        https://medium.com/@cesarmcferreira/how-to-use-the-new-android-viewmodelscope-in-clean-architecture-2a33aac959ee
         */
        getSummary()
    }
    fun setRefreshing() {
        if (getCurrentViewState()!!.loading || !getCurrentViewState()!!.empty ) {
            _viewState.value = getCurrentViewState()?.copy(empty = false, refresh = false)
            return
        }

        _viewState.value = getCurrentViewState()?.copy(empty = false, refresh = true, error = null)
        getSummary()
    }

    private fun getSummary() {
        mJob = useCase.getSummary(viewModelScope, getCurrentViewState()){
            _viewState.value = it
        }

    }

    override fun onCleared() {
        super.onCleared()
        Log.v("viewModelDestroyed", "true")
        if (!mJob.isCancelled) {
            mJob.cancel()
        }
    }
}