package com.ashehata.covid_19.dataSource

import io.coroutines.cache.core.CachePolicy
import io.coroutines.cache.core.isValidCache
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.coroutines.cache.core.CoroutinesCache
import io.coroutines.cache.dao.Cache
import io.coroutines.cache.dao.RealmDatabase
import kotlinx.coroutines.*
import java.lang.IllegalStateException
import java.util.*
import kotlin.collections.HashMap
import kotlin.coroutines.CoroutineContext

open class CoroutinesCacheMe(private var context: Context, test: Boolean = false, lifecycleOwner: LifecycleOwner? = null): CoroutinesCache(context, test, lifecycleOwner){

    private val executionJob: Job  by lazy { SupervisorJob() }

    override val coroutineContext: CoroutineContext by lazy {
        Dispatchers.Default + executionJob
    }
}
