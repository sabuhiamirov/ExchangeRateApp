package com.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


interface RequestParamInterceptor : Interceptor {}

class RequestParamInterceptorImpl
@Inject
constructor() : RequestParamInterceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()

        //  TODO MOVE VALUE TO BUILD CONFIG
        builder.addHeader("x-rapidapi-key", "2ba298ae8emsh7aed3707ad9b700p12c80djsnebd5aeb56df8")
        builder.addHeader("x-rapidapi-host", "currency-exchange.p.rapidapi.com")

        return chain.proceed(builder.build())
    }
}