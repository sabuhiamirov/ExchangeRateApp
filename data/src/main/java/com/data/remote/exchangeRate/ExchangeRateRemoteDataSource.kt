package com.data.remote.exchangeRate

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.math.BigDecimal
import java.util.ArrayList

interface ExchangeRateRemoteDataSource {

    @GET("/listquotes")
    suspend fun getListQuotes() : Response<ArrayList<String>?>

    @GET("/exchange")
    suspend fun getExchangeCurrency(
        @Query("from") currentCurrency : String,
        @Query("to") exchangeCurrency : String,
        @Query("q") amount : Double
    ): Response<BigDecimal?>

}