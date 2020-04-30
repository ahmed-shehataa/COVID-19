package com.ashehata.covid_19.dataSource

import com.ashehata.covid_19.models.SummaryResponse
import retrofit2.http.GET

interface RemoteData {

    @GET("/summary")
    suspend fun getSummery(): SummaryResponse
}