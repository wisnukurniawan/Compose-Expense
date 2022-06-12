package com.wisnu.kurniawan.wallee.features.localized.di

import com.wisnu.kurniawan.wallee.features.localized.data.ILocalizedEnvironment
import com.wisnu.kurniawan.wallee.features.localized.data.LocalizedEnvironment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class LocalizedModule {

    @Binds
    abstract fun provideEnvironment(
        environment: LocalizedEnvironment
    ): ILocalizedEnvironment

}
