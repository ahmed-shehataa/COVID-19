package com.ashehata.covid_19.showSummary

import Countries
import Global
import SummaryResponse

data class SummaryUseCase(
//    var summary: SummaryResponse,
    var global: Global,
    var countries: List<Countries>,
    var error: Throwable,
    var loading: Boolean,
    var refresh: Boolean,
    var sortType: String
)