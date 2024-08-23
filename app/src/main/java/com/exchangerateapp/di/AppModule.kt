package com.exchangerateapp.di

import android.app.Application
import android.content.Context
import androidx.hilt.work.WorkerAssistedFactory
import androidx.work.ListenableWorker
import com.data.remote.interceptor.RequestParamInterceptorImpl
import com.data.utils.BigDecimalDefaultScaleDeserializer
import com.exchangerateapp.BuildConfig
import com.exchangerateapp.ExchangeRateApp
import com.google.gson.GsonBuilder
import com.presentation.helper.UploadWorker
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigDecimal
import java.time.Duration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


  // The endpoints have limited access, allowing only 10 requests per hour.


    @Provides
    fun provideActivity() = ExchangeRateApp()

    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun providesRetrofitInstance(
        requestParamInterceptor: RequestParamInterceptorImpl,
    ): Retrofit {

        //  TODO
        val BASE_URL = "https://currency-exchange.p.rapidapi.com"

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .addInterceptor(requestParamInterceptor)
            .readTimeout(Duration.ofMinutes(1))
            .writeTimeout(Duration.ofMinutes(1))
            .build()

        val gson = GsonBuilder()
            .registerTypeAdapter(BigDecimal::class.java, BigDecimalDefaultScaleDeserializer())
            .create()

        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

}