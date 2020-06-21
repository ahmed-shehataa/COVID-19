package com.ashehata.covid_19.externals

import com.ashehata.covid_19.dataSource.CoroutinesCacheMe
import com.ashehata.covid_19.showSummary.SummaryRepositoryImpl
import com.ashehata.covid_19.showSummary.SummaryUseCase
import com.ashehata.covid_19.showSummary.SummaryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mModule = module {

    single { ConnectionStateMonitor(get()) }
    single { CoroutinesCacheMe(get()) }
    single { createService() }
    factory { SummaryRepositoryImpl(get(), get()) }
    factory { SummaryUseCase(get<SummaryRepositoryImpl>()) }
    viewModel { SummaryViewModel(get()) }
}