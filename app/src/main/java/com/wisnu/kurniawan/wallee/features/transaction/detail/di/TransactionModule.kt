package com.wisnu.kurniawan.wallee.features.transaction.detail.di

import com.wisnu.kurniawan.wallee.features.transaction.detail.data.ITransactionEnvironment
import com.wisnu.kurniawan.wallee.features.transaction.detail.data.TransactionEnvironment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class TransactionModule {

    @Binds
    abstract fun provideEnvironment(
        environment: TransactionEnvironment
    ): ITransactionEnvironment

}
