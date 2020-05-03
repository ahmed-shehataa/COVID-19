package com.ashehata.covid_19.showSummary

import android.util.Log
import kotlinx.coroutines.*
import java.lang.Exception


class SummaryUseCase(private val repositoryImpl: SummaryRepositoryImpl) {

    fun getSummary(viewModelScope: CoroutineScope, viewState: SummaryViewState?, updateViewState: (SummaryViewState?) -> Unit): Job{

         return viewModelScope.launch {
            try {
                // Get data
                val result = withContext(Dispatchers.IO) {
                    repositoryImpl.getSummary()
                }

                if (result.countries.isNotEmpty()) {
                    val sortedCountries = result.countries.sortedByDescending {
                    it.totalConfirmed
                }
                updateViewState(viewState?.copy(
                    countries = sortedCountries,
                    global = result.global,
                    lastUpdate = sortedCountries.get(0).date,
                    errorMessage = null,
                    loading = false,
                    empty = false,
                    refresh = false
                ))
                } else {
                updateViewState(viewState?.copy(
                    errorMessage = null,
                    loading = false,
                    empty = true,
                    lastUpdate = "Try again",
                    refresh = false
                ))
                }

            } catch (e: Exception) {
                delay(800)
                Log.v("mError", e.message!!)
                 updateViewState(viewState?.copy(
                     errorMessage = e.localizedMessage,
                     loading = false,
                     lastUpdate = "Try again",
                     empty = true,
                     refresh = false
                ))
            }
        }
    }
}