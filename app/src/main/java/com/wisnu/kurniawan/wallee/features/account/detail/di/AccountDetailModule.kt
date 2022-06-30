package com.wisnu.kurniawan.wallee.features.account.detail.di

import com.wisnu.kurniawan.wallee.features.account.detail.data.AccountDetailEnvironment
import com.wisnu.kurniawan.wallee.features.account.detail.data.IAccountDetailEnvironment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class AccountDetailModule {

    @Binds
    abstract fun provideEnvironment(
        environment: AccountDetailEnvironment
    ): IAccountDetailEnvironment

}
