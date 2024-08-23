package com.domain.base

import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

abstract class BaseLocalUseCase <Params, ReturnType, MapType>(
    private val executionContext: CoroutineContext,
) : BaseUseCase<Params, ReturnType, MapType>(executionContext){

    protected abstract suspend fun getResponse(params: Params): ReturnType?

    protected abstract suspend fun mapData(data: ReturnType?): MapType

    override suspend fun execute(params: Params, block: CompletionBlock<MapType>) {

        val request = Request<MapType>().apply(block)

        try {

            val response = withContext(executionContext) {
                getResponse(params)
            }

            val payload = withContext(executionContext) {
                mapData(response)
            }

            request.onSuccess(payload)

        } catch (e: Throwable) {
            request.onError?.invoke(e)
        }
    }
}