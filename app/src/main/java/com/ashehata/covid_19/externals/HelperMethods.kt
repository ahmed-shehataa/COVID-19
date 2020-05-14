package com.ashehata.covid_19.externals

import com.ashehata.covid_19.dataSource.RemoteData
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun createService(): RemoteData {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.covid19api.com")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
    return retrofit.create(RemoteData::class.java)
}