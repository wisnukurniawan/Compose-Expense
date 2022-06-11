package com.wisnu.kurniawan.wallee.foundation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @Provides
    @Named(DiName.DISPATCHER_IO)
    fun provideDispatcherIo(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @Named(DiName.DISPATCHER_MAIN)
    fun provideDispatcherMain(): CoroutineDispatcher {
        return Dispatchers.Main
    }
}
