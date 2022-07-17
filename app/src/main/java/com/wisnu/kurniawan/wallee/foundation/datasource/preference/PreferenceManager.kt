package com.wisnu.kurniawan.wallee.foundation.datasource.preference

import androidx.datastore.core.DataStore
import com.wisnu.kurniawan.wallee.foundation.datasource.preference.model.CredentialPreference
import com.wisnu.kurniawan.wallee.foundation.datasource.preference.model.LanguagePreference
import com.wisnu.kurniawan.wallee.foundation.datasource.preference.model.OnboardingPreference
import com.wisnu.kurniawan.wallee.foundation.datasource.preference.model.ThemePreference
import com.wisnu.kurniawan.wallee.foundation.datasource.preference.model.UserPreference
import com.wisnu.kurniawan.wallee.foundation.di.DiName
import com.wisnu.kurniawan.wallee.foundation.extension.toLanguage
import com.wisnu.kurniawan.wallee.foundation.extension.toTheme
import com.wisnu.kurniawan.wallee.model.Credential
import com.wisnu.kurniawan.wallee.model.Language
import com.wisnu.kurniawan.wallee.model.Theme
import com.wisnu.kurniawan.wallee.model.User
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class PreferenceManager @Inject constructor(
    @Named(DiName.DISPATCHER_IO) private val dispatcher: CoroutineDispatcher,
    private val credentialDataStore: DataStore<CredentialPreference>,
    private val userDataStore: DataStore<UserPreference>,
    private val themeDataStore: DataStore<ThemePreference>,
    private val languageDataStore: DataStore<LanguagePreference>,
    private val onboardingDataStore: DataStore<OnboardingPreference>,
) {

    fun getCredential(): Flow<Credential> {
        return credentialDataStore.data
            .map { Credential(it.token) }
            .catch { emit(Credential(token = "")) }
            .flowOn(dispatcher)
    }

    fun getUser(): Flow<User> {
        return userDataStore.data
            .map { User(it.email) }
            .catch { emit(User(email = "")) }
            .flowOn(dispatcher)
    }

    fun getTheme(): Flow<Theme> {
        return themeDataStore.data.map { it.toTheme() }
            .catch { emit(Theme.SYSTEM) }
            .flowOn(dispatcher)
    }

    fun getLanguage(): Flow<Language> {
        return languageDataStore.data.map { it.toLanguage() }
            .catch { emit(Language.ENGLISH) }
            .flowOn(dispatcher)
    }

    fun hasFinishOnboarding(): Flow<Boolean> {
        return onboardingDataStore.data.map { it.finishOnboarding }
            .catch { emit(false) }
            .flowOn(dispatcher)
    }

    suspend fun setCredential(data: Credential) {
        withContext(dispatcher) {
            credentialDataStore.updateData {
                CredentialPreference(data.token)
            }
        }
    }

    suspend fun setUser(data: User) {
        withContext(dispatcher) {
            userDataStore.updateData {
                UserPreference(data.email)
            }
        }
    }

    suspend fun setTheme(data: Theme) {
        withContext(dispatcher) {
            themeDataStore.updateData {
                ThemePreference(data.value)
            }
        }
    }

    suspend fun setLanguage(data: Language) {
        withContext(dispatcher) {
            languageDataStore.updateData {
                LanguagePreference(data.code)
            }
        }
    }

    suspend fun setFinishOnboarding(finish: Boolean) {
        withContext(dispatcher) {
            onboardingDataStore.updateData {
                OnboardingPreference(finish)
            }
        }
    }

}
