package com.wisnu.kurniawan.wallee.features.transaction.detail.di

import com.wisnu.kurniawan.wallee.features.transaction.detail.data.ITransactionDetailEnvironment
import com.wisnu.kurniawan.wallee.features.transaction.detail.data.TransactionDetailEnvironment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class TransactionDetailModule {

    @Binds
    abstract fun provideEnvironment(
        environment: TransactionDetailEnvironment
    ): ITransactionDetailEnvironment

}
