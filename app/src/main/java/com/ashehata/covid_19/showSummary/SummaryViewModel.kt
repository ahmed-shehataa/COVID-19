package com.ashehata.covid_19.showSummary

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashehata.covid_19.models.Countries
import kotlinx.coroutines.*
import java.util.*

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
            _viewState.value = getCurrentViewState()?.copy(empty = false,
                refresh = false,
                errorMessage = null,
                searchCountryPosition = null)
            return
        }

        _viewState.value = getCurrentViewState()?.copy(empty = false,
            refresh = true,
            errorMessage = null,
            searchCountryPosition = null)
        getSummary()
    }

    fun setSearch(text: String?) {
        if (text.isNullOrEmpty()) {
            _viewState.value = getCurrentViewState()?.copy(searchCountryPosition = null, errorMessage = "Empty Field")
            return
        }

        if (getCurrentViewState()?.countries.isNullOrEmpty()) {
            _viewState.value = getCurrentViewState()?.copy(searchCountryPosition = null, errorMessage = "Try again")
            return
        }

        val searchedItem = getCurrentViewState()?.countries?.find {
            it.country.toLowerCase(Locale.ENGLISH).contains(text)

        }

        if (searchedItem != null) {
            val position = getCurrentViewState()?.copy()?.countries?.indexOf(searchedItem)
            _viewState.value = getCurrentViewState()?.copy(searchCountryPosition = position, errorMessage = null)

        } else {
            _viewState.value = getCurrentViewState()?.copy(searchCountryPosition = null, errorMessage = "Country not found")
        }

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