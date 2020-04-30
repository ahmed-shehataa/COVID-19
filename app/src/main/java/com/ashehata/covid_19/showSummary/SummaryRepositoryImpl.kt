package com.ashehata.covid_19.showSummary

import android.util.Log
import com.ashehata.covid_19.dataSource.LocalData
import com.ashehata.covid_19.dataSource.RemoteData
import com.ashehata.covid_19.models.SummaryResponse

class SummaryRepositoryImpl(/*private val local: LocalData,*/
                            private val remote: RemoteData) : SummaryRepository {

    init {

    }

    override suspend fun getSummary(): SummaryResponse {
        return remote.getSummery()
    }
}