package com.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

const val IO_CONTEXT = "IO_CONTEXT"

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Named(IO_CONTEXT)
    fun provideCoroutineContext() : CoroutineContext = Dispatchers.IO

}