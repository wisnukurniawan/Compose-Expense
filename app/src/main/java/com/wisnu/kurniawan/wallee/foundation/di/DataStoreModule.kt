package com.wisnu.kurniawan.wallee.foundation.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.wisnu.kurniawan.wallee.foundation.datasource.preference.CredentialPreferenceSerializer
import com.wisnu.kurniawan.wallee.foundation.datasource.preference.LanguagePreferenceSerializer
import com.wisnu.kurniawan.wallee.foundation.datasource.preference.OnboardingPreferenceSerializer
import com.wisnu.kurniawan.wallee.foundation.datasource.preference.ThemePreferenceSerializer
import com.wisnu.kurniawan.wallee.foundation.datasource.preference.UserPreferenceSerializer
import com.wisnu.kurniawan.wallee.foundation.datasource.preference.model.CredentialPreference
import com.wisnu.kurniawan.wallee.foundation.datasource.preference.model.LanguagePreference
import com.wisnu.kurniawan.wallee.foundation.datasource.preference.model.OnboardingPreference
import com.wisnu.kurniawan.wallee.foundation.datasource.preference.model.ThemePreference
import com.wisnu.kurniawan.wallee.foundation.datasource.preference.model.UserPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val CREDENTIAL_NAME = "credential-preference.pb"
private const val USER_NAME = "user-preference.pb"
private const val THEME_NAME = "theme-preference.pb"
private const val LANGUAGE_NAME = "language-preference.pb"
private const val ONBOARDING_NAME = "onboarding-preference.pb"

private val Context.credentialDataStore: DataStore<CredentialPreference> by dataStore(
    fileName = CREDENTIAL_NAME,
    serializer = CredentialPreferenceSerializer
)
private val Context.userDataStore: DataStore<UserPreference> by dataStore(
    fileName = USER_NAME,
    serializer = UserPreferenceSerializer
)
private val Context.themeDataStore: DataStore<ThemePreference> by dataStore(
    fileName = THEME_NAME,
    serializer = ThemePreferenceSerializer
)
val Context.languageDatastore: DataStore<LanguagePreference> by dataStore(
    fileName = LANGUAGE_NAME,
    serializer = LanguagePreferenceSerializer
)
val Context.onboardingDatastore: DataStore<OnboardingPreference> by dataStore(
    fileName = ONBOARDING_NAME,
    serializer = OnboardingPreferenceSerializer
)

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun provideCredentialDataStore(@ApplicationContext context: Context): DataStore<CredentialPreference> {
        return context.credentialDataStore
    }

    @Singleton
    @Provides
    fun provideUserDataStore(@ApplicationContext context: Context): DataStore<UserPreference> {
        return context.userDataStore
    }

    @Singleton
    @Provides
    fun provideThemeDataStore(@ApplicationContext context: Context): DataStore<ThemePreference> {
        return context.themeDataStore
    }

    @Singleton
    @Provides
    fun provideLanguageDataStore(@ApplicationContext context: Context): DataStore<LanguagePreference> {
        return context.languageDatastore
    }

    @Singleton
    @Provides
    fun provideOnboardingDataStore(@ApplicationContext context: Context): DataStore<OnboardingPreference> {
        return context.onboardingDatastore
    }

}
