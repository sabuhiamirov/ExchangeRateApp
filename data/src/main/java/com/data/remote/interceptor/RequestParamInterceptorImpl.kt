package com.data.remote.interceptor

import com.data.BuildConfig
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
        builder.addHeader("x-rapidapi-key", "26858f3b1bmsh7cf847bfc0279ddp1763d0jsnfa4b7920f966")
        builder.addHeader("x-rapidapi-host", "currency-exchange.p.rapidapi.com")

        return chain.proceed(builder.build())
    }
}