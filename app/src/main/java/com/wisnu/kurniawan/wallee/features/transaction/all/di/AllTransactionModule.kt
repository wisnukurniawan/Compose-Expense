package com.wisnu.kurniawan.wallee.features.transaction.all.di

import com.wisnu.kurniawan.wallee.features.transaction.all.data.AllTransactionEnvironment
import com.wisnu.kurniawan.wallee.features.transaction.all.data.IAllTransactionEnvironment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AllTransactionModule {

    @Binds
    abstract fun provideEnvironment(
        environment: AllTransactionEnvironment
    ): IAllTransactionEnvironment

}
