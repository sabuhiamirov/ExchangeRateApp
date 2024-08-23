package com.domain.base

import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

abstract class BaseRemoteUseCase<Params, ReturnType, MapType>(
    private val executionContext: CoroutineContext,
) : BaseUseCase<Params, ReturnType,MapType>(executionContext) {

    protected abstract suspend fun getResponse(params: Params): Response<ReturnType>

    protected abstract suspend fun mapData(data: ReturnType?): MapType?

    override suspend fun execute(params: Params, block: CompletionBlock<MapType>) {

        val request = Request<MapType>().apply(block)

        try {
            val response = withContext(executionContext) {
                getResponse(params)
            }

            if (response.isSuccessful) {
                val payload = withContext(executionContext) {
                    mapData(response.body())
                }

                request.onSuccess(payload)

            } else {
                throw HttpException(response)
            }

        } catch (e: Throwable) {
            request.onError?.invoke(e)
        }

    }
}