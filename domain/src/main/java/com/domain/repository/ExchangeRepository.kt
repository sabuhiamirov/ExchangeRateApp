package com.domain.repository

import com.domain.dto.WarningExchangeSavedLocalDto
import retrofit2.Response
import java.math.BigDecimal
import java.util.ArrayList

interface ExchangeRepository {

    suspend fun getListQuotes(): Response<ArrayList<String>?>

    suspend fun getExchangeCurrency(
        currentCurrency: String,
        exchangeCurrency: String,
        amount: Double
    ): Response<BigDecimal?>

    suspend fun saveExchangeWarningToLocal(warningExchangeSave: WarningExchangeSavedLocalDto)

    suspend fun getSavedExchangeWarningInfo(): List<WarningExchangeSavedLocalDto>
}