package com.wisnu.kurniawan.wallee.features.onboarding.di

import com.wisnu.kurniawan.wallee.features.onboarding.data.IOnboardingEnvironment
import com.wisnu.kurniawan.wallee.features.onboarding.data.OnboardingEnvironment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class OnboardingModule {

    @Binds
    abstract fun provideEnvironment(
        environment: OnboardingEnvironment
    ): IOnboardingEnvironment

}
