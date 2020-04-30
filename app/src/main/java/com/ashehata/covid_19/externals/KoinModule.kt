package com.ashehata.covid_19.externals

import com.ashehata.covid_19.showSummary.SummaryRepositoryImpl
import com.ashehata.covid_19.showSummary.SummaryUseCase
import com.ashehata.covid_19.showSummary.SummaryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mModule = module {

    /*
    single { Room.databaseBuilder(get(), LocalDatabase::class.java, "cavents_database").build() }
    single { get<LocalDatabase>().eventsDao() }
     */
    //single {  }
    single { createService() }
    factory { SummaryRepositoryImpl(/*get(),*/ get()) }
    factory { SummaryUseCase(get()) }
    viewModel { SummaryViewModel(get()) }
}