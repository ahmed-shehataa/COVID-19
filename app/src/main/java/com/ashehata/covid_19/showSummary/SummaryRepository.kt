package com.ashehata.covid_19.showSummary

import com.ashehata.covid_19.models.SummaryResponse

interface SummaryRepository {

    suspend fun getSummary(): SummaryResponse
}