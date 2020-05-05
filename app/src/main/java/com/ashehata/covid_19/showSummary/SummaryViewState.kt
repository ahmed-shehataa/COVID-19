package com.ashehata.covid_19.showSummary

import com.ashehata.covid_19.externals.ErrorType
import com.ashehata.covid_19.models.Countries
import com.ashehata.covid_19.models.Global

data class SummaryViewState(
    //var summary: SummaryResponse,
    var global: Global? = null,
    var countries: List<Countries>? = null,
    var errorType: ErrorType = ErrorType.NoError,
    var loading: Boolean = false,
    var refresh: Boolean = false,
    var empty: Boolean = false,
    var lastUpdate: String = "",
    var sortType: String = "",
    var searchCountryPosition: Int? = null,
    var showUpArrow: Boolean = false
)