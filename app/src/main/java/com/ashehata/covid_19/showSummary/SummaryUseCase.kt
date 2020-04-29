package com.ashehata.covid_19.showSummary

import SummaryResponse

data class SummaryUseCase(
    var summaryList: List<SummaryResponse>,
    var error: Throwable,
    var loading: Boolean,
    var refresh: Boolean,
    var sortType: String
)