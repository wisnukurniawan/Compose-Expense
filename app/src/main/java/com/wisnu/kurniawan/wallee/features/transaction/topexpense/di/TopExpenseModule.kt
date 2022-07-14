package com.wisnu.kurniawan.wallee.features.transaction.topexpense.di

import com.wisnu.kurniawan.wallee.features.transaction.topexpense.data.ITopExpenseEnvironment
import com.wisnu.kurniawan.wallee.features.transaction.topexpense.data.TopExpenseEnvironment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class TopExpenseModule {

    @Binds
    abstract fun provideEnvironment(
        environment: TopExpenseEnvironment
    ): ITopExpenseEnvironment

}
