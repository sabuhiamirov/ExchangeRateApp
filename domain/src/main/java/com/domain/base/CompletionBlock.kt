package com.domain.base

import kotlin.coroutines.CoroutineContext


typealias CompletionBlock<T> = BaseUseCase.Request<T>.() -> Unit

abstract class BaseUseCase<Params, ReturnType, MapType>(
    private val executionContext: CoroutineContext
) {

    abstract suspend fun execute(params: Params, block: CompletionBlock<MapType>)

    class Request <T> {
        var onSuccess: (T?) -> Unit = {}
        var onError: ((Throwable) -> Unit)? = null
    }

}