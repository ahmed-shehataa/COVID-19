package com.ashehata.covid_19.showSummary

import com.ashehata.covid_19.dataSource.RemoteData
import com.ashehata.covid_19.externals.CACHE_KEY
import com.ashehata.covid_19.externals.CACHE_TIME
import com.ashehata.covid_19.models.SummaryResponse
import io.coroutines.cache.core.CachePolicy
import io.coroutines.cache.core.CoroutinesCache
import kotlinx.coroutines.Deferred
import java.util.concurrent.TimeUnit

class SummaryRepositoryImpl(private val cash: CoroutinesCache,
                            private val remoteData: RemoteData) : SummaryRepository {

    override suspend fun getSummary(): SummaryResponse {
        return cash.asyncCache({remoteData.getSummery()}, CACHE_KEY, CachePolicy.TimeCache(CACHE_TIME, TimeUnit.HOURS)).await()
    }
}