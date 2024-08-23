package com.domain.usecase

import com.domain.base.BaseRemoteUseCase
import com.domain.di.IO_CONTEXT
import com.domain.repository.ExchangeRepository
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext


class GetListQuotesRemoteUseCase
@Inject constructor(
    @Named(IO_CONTEXT) context: CoroutineContext,
    private val exchangeRepository: ExchangeRepository
) : BaseRemoteUseCase<Unit, ArrayList<String>?, ArrayList<String>>(
    context,
) {

    override suspend fun getResponse(params: Unit): Response<ArrayList<String>?> {
        return exchangeRepository.getListQuotes()
    }

    override suspend fun mapData(data: ArrayList<String>?): ArrayList<String>? {
       return data
    }
}