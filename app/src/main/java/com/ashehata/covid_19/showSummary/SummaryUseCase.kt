package com.ashehata.covid_19.showSummary

import android.util.Log
import com.ashehata.covid_19.externals.ErrorType
import kotlinx.coroutines.*
import java.lang.Exception


class SummaryUseCase(private val repository: SummaryRepository) {

    fun getSummary(viewModelScope: CoroutineScope, viewState: SummaryViewState?, updateViewState: (SummaryViewState?) -> Unit): Job{

        return viewModelScope.launch(Dispatchers.Main) {
             Log.v("parentJob", isActive.toString())
             // Get data
             val result=
                 try {
                     Log.v("childJob", isActive.toString())
                     repository.getSummary()?.await()
                 } catch (e: Exception) {
                     Log.v("mError", e.message!!)
                     null
                 }

             if (result != null) {
                 if (result.countries.isNotEmpty()) {
                     val sortedCountries = result.countries.sortedByDescending {
                         it.totalConfirmed
                     }
                     updateViewState(
                         viewState?.copy(
                             countries = sortedCountries,
                             global = result.global,
                             lastUpdate = sortedCountries.get(0).date,
                             errorType = ErrorType.NoError,
                             loading = false,
                             empty = false,
                             refresh = false,
                             showUpArrow = true
                         )
                     )
                 } else {
                     updateViewState(
                         viewState?.copy(
                             errorType = ErrorType.NoError,
                             loading = false,
                             empty = true,
                             lastUpdate = "",
                             refresh = false,
                             showUpArrow = false
                         )
                     )
                 }
             } else {
                 delay(800)
//                 Log.v("mError", e.message!!)
                 updateViewState(viewState?.copy(
                     errorType = ErrorType.NoConnection,
                     loading = false,
                     lastUpdate = "",
                     empty = true,
                     refresh = false,
                     showUpArrow = false
                 ))
             }
        }
    }
}