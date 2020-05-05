package com.ashehata.covid_19.showSummary

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.ashehata.covid_19.externals.ErrorType
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
            ErrorType.NoError,
            true,
            false,
            true,
            "",
            showUpArrow = false
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

        if (getCurrentViewState()!!.loading ) {
            _viewState.value = getCurrentViewState()?.copy(empty = false,
                refresh = false,
                showUpArrow = false,
                errorType = ErrorType.NoError,
                searchCountryPosition = null)
            return
        }

        _viewState.value = getCurrentViewState()?.copy(empty = false,
            refresh = true,
            errorType = ErrorType.NoError,
            searchCountryPosition = null)
        getSummary()
    }

    fun setSearch(text: String?) {
        if (text.isNullOrEmpty()) {
            _viewState.value = getCurrentViewState()?.copy(searchCountryPosition = null, errorType = ErrorType.EmptyField)
            return
        }

        if (getCurrentViewState()?.countries.isNullOrEmpty()) {
            _viewState.value = getCurrentViewState()?.copy(searchCountryPosition = null, errorType = ErrorType.TryAgain)
            return
        }

        val searchedItem = getCurrentViewState()?.countries?.find {
            it.country.toLowerCase(Locale.ENGLISH).contains(text)

        }

        if (searchedItem != null) {
            val position = getCurrentViewState()?.copy()?.countries?.indexOf(searchedItem)
            _viewState.value = getCurrentViewState()?.copy(searchCountryPosition = position, errorType = ErrorType.NoError)

        } else {
            _viewState.value = getCurrentViewState()?.copy(searchCountryPosition = null, errorType = ErrorType.NoCountry)
        }

    }

    fun goUp(recyclerView: () -> Unit) {
        if (!getCurrentViewState()!!.empty) {
            recyclerView()
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