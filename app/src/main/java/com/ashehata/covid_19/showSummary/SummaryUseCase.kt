package com.ashehata.covid_19.showSummary

import com.ashehata.covid_19.models.SummaryResponse


class SummaryUseCase(private val repositoryImpl: SummaryRepositoryImpl) {

    suspend fun getSummary(): SummaryResponse/*: SummaryViewState */{

        return repositoryImpl.getSummary()
        /*
        return SummaryViewState(
            null,
            null,
            null,
            false,
            false,
            false,
            ""
        )

         */
    }
}