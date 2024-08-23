package com.domain.usecase

import com.domain.base.BaseLocalUseCase
import com.domain.di.IO_CONTEXT
import com.domain.dto.WarningExchangeSavedLocalDto
import com.domain.repository.ExchangeRepository
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

class SavedExchangeRateLocalUseCase
@Inject constructor(
    @Named(IO_CONTEXT) context: CoroutineContext,
    private val exchangeRepository: ExchangeRepository
) : BaseLocalUseCase<SavedExchangeRateLocalUseCase.Params, Unit, Unit>(context) {

    data class Params(
        val warningExchangeList : WarningExchangeSavedLocalDto
    )
    override suspend fun getResponse(params: Params) : Unit {


        return exchangeRepository.saveExchangeWarningToLocal(params.warningExchangeList)
    }

    override suspend fun mapData(data: Unit?) = Unit
}