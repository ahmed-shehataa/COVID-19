package com.ashehata.covid_19.dataSource

import com.ashehata.covid_19.models.SummaryResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface RemoteData {

    // when use Deferred .. delete suspend keyword
    @GET("/summary")
    fun getSummery(): Deferred<SummaryResponse>
}