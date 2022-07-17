package com.wisnu.kurniawan.wallee.foundation.datasource.preference

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.wisnu.kurniawan.wallee.foundation.datasource.preference.model.OnboardingPreference
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object OnboardingPreferenceSerializer : Serializer<OnboardingPreference> {

    override val defaultValue: OnboardingPreference = OnboardingPreference(false)

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun readFrom(input: InputStream): OnboardingPreference {
        try {
            return OnboardingPreference.ADAPTER.decode(input)
        } catch (exception: IOException) {
            throw CorruptionException("Cannot read proto", exception)
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun writeTo(t: OnboardingPreference, output: OutputStream) {
        OnboardingPreference.ADAPTER.encode(output, t)
    }

}
