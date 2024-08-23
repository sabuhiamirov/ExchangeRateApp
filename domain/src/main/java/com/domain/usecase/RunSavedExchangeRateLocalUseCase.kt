package com.domain.usecase

import com.domain.base.BaseLocalUseCase
import com.domain.di.IO_CONTEXT
import com.domain.dto.WarningExchangeSavedLocalDto
import com.domain.repository.ExchangeRepository
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

class RunSavedExchangeRateLocalUseCase @Inject constructor(
    @Named(IO_CONTEXT) context: CoroutineContext,
    private val exchangeRepository: ExchangeRepository,
) : BaseLocalUseCase<Unit, List<WarningExchangeSavedLocalDto>, List<WarningExchangeSavedLocalDto>>(
    context
) {
    override suspend fun getResponse(params: Unit): List<WarningExchangeSavedLocalDto> {
        return exchangeRepository.getSavedExchangeWarningInfo()
    }

    override suspend fun mapData(data: List<WarningExchangeSavedLocalDto>?): List<WarningExchangeSavedLocalDto> {
        return data ?: listOf()
    }


}