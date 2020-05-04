package com.ashehata.covid_19.showSummary

import android.util.Log
import com.ashehata.covid_19.dataSource.CoroutinesCacheMe
import com.ashehata.covid_19.dataSource.RemoteData
import com.ashehata.covid_19.externals.CACHE_KEY
import com.ashehata.covid_19.externals.CACHE_TIME
import com.ashehata.covid_19.models.SummaryResponse
import io.coroutines.cache.core.CachePolicy
import io.coroutines.cache.core.CoroutinesCache
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.SupervisorJob
import java.lang.Exception
import java.util.concurrent.TimeUnit

class SummaryRepositoryImpl(private val cash: CoroutinesCacheMe,
                            private val remoteData: RemoteData) : SummaryRepository {

    override fun getSummary(): Deferred<SummaryResponse>? {
        val result =
            cash.asyncCache({remoteData.getSummery()}, CACHE_KEY, CachePolicy.TimeCache(CACHE_TIME, TimeUnit.HOURS))
        Log.v("bored", result?.isCancelled.toString())
        return result
    }
}