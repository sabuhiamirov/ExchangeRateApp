package com.data.repositoryImpl

import com.data.local.warningExchangeRate.WarningExchangeLocalDataSource
import com.data.remote.exchangeRate.ExchangeRateRemoteDataSource
import com.domain.dto.WarningExchangeSavedLocalDto
import com.domain.repository.ExchangeRepository
import retrofit2.Response
import java.math.BigDecimal
import java.util.ArrayList
import javax.inject.Inject

class ExchangeRateRepositoryImpl @Inject constructor(
    private val exchangeRemoteDataSource: ExchangeRateRemoteDataSource,
    private val warningExchangeLocalDataSource: WarningExchangeLocalDataSource
) : ExchangeRepository {

    override suspend fun getListQuotes(): Response<ArrayList<String>?> {
        return exchangeRemoteDataSource.getListQuotes()
    }

    override suspend fun getExchangeCurrency(
        currentCurrency: String,
        exchangeCurrency: String,
        amount: Double
    ): Response<BigDecimal?> {
        return exchangeRemoteDataSource.getExchangeCurrency(
            currentCurrency,
            exchangeCurrency,
            amount
        )
    }

    override suspend fun saveExchangeWarningToLocal(warningExchangeSave: WarningExchangeSavedLocalDto) {
        return warningExchangeLocalDataSource.insertWarningExchangeCurrency(warningExchangeSave)
    }

    override suspend fun getSavedExchangeWarningInfo(): List<WarningExchangeSavedLocalDto> {
        return warningExchangeLocalDataSource.warningExchangeSavedLocalDto
    }
}