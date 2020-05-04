package com.ashehata.covid_19.externals

import androidx.lifecycle.LifecycleOwner
import com.ashehata.covid_19.dataSource.CoroutinesCacheMe
import com.ashehata.covid_19.showSummary.SummaryRepositoryImpl
import com.ashehata.covid_19.showSummary.SummaryUseCase
import com.ashehata.covid_19.showSummary.SummaryViewModel
import io.coroutines.cache.core.CoroutinesCache
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mModule = module {

    /*
    single { Room.databaseBuilder(get(), LocalDatabase::class.java, "cavents_database").build() }
    single { get<LocalDatabase>().eventsDao() }
     */
    //single {  }
    single { CoroutinesCacheMe(get()) }
    single { createService() }
    factory { SummaryRepositoryImpl(get(), get()) }
    factory { SummaryUseCase(get()) }
    viewModel { SummaryViewModel(get()) }
}