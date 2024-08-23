package com.data.di

import android.content.Context
import androidx.room.Room
import com.data.local.warningExchangeRate.WarningExchangeDao
import com.data.local.warningExchangeRate.WarningExchangeDatabase
import com.data.local.warningExchangeRate.WarningExchangeLocalDataSource
import com.data.local.warningExchangeRate.WarningExchangeLocalDataSourceImpl
import com.data.remote.exchangeRate.ExchangeRateRemoteDataSource
import com.data.remote.interceptor.RequestParamInterceptor
import com.data.remote.interceptor.RequestParamInterceptorImpl
import com.data.repositoryImpl.ExchangeRateRepositoryImpl
import com.domain.repository.ExchangeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    //----------Repository Start--------------------------------------------------------------------

    @Provides
    fun provideExchangeRepository(
        exchangeRemoteDataSource: ExchangeRateRemoteDataSource,
        warningExchangeLocalDataSource: WarningExchangeLocalDataSource
    ): ExchangeRepository = ExchangeRateRepositoryImpl(
        exchangeRemoteDataSource =   exchangeRemoteDataSource,
        warningExchangeLocalDataSource = warningExchangeLocalDataSource
    )

    //----------Repository End----------------------------------------------------------------------




    //----------Remote Data Source Start------------------------------------------------------------

    @Provides
    fun provideExchangeRemoteDataSource(retrofit: Retrofit)
            : ExchangeRateRemoteDataSource = retrofit.create(ExchangeRateRemoteDataSource::class.java)

    //----------Remote Data Source End------------------------------------------------------------



    //----------Local Data Source Start-------------------------------------------------------------
    @Provides
    fun provideWarningExchangeLocalDataSource(warningExchangeDao: WarningExchangeDao)
            : WarningExchangeLocalDataSource = WarningExchangeLocalDataSourceImpl(
        warningExchangeDao =  warningExchangeDao)



    //----------Local Data Source End-------------------------------------------------------------



    @Singleton
    @Provides
    fun provideWarningExchangeDataBase(context: Context): WarningExchangeDatabase = Room.databaseBuilder(
        context,
        WarningExchangeDatabase::class.java,
        "warning_exchange_table"
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideWarningExchangeDao(warningExchangeDatabase: WarningExchangeDatabase): WarningExchangeDao =
        warningExchangeDatabase.warningExchangeDao()

    //----------Interceptor Start-------------------------------------------------------------------

    @Singleton
    @Provides
    fun provideRequestParamInterceptor(): RequestParamInterceptor = RequestParamInterceptorImpl()

}