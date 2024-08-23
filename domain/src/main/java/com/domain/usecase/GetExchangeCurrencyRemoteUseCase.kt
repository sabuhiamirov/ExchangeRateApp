package com.domain.usecase

import com.domain.base.BaseRemoteUseCase
import com.domain.di.IO_CONTEXT
import com.domain.repository.ExchangeRepository
import retrofit2.Response
import java.math.BigDecimal
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

class GetExchangeCurrencyRemoteUseCase @Inject constructor(
    @Named(IO_CONTEXT) context: CoroutineContext,
    private val exchangeRepository: ExchangeRepository
) : BaseRemoteUseCase<GetExchangeCurrencyRemoteUseCase.Params, BigDecimal?, BigDecimal?>(context) {

    data class Params(
        val currentCurrency: String,
        val exchangeCurrency: String,
        val amount: Double
    )

    override suspend fun getResponse(params: Params): Response<BigDecimal?> {
        return exchangeRepository.getExchangeCurrency(
            currentCurrency = params.currentCurrency,
            exchangeCurrency = params.exchangeCurrency,
            amount = params.amount
        )
    }

    override suspend fun mapData(data: BigDecimal?): BigDecimal? {
        return data
    }

}