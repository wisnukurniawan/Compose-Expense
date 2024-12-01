package com.wisnu.kurniawan.wallee.foundation.datasource.preference

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.wisnu.kurniawan.wallee.foundation.datasource.preference.model.OnboardingPreference
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object OnboardingPreferenceSerializer : Serializer<OnboardingPreference> {

    override val defaultValue: OnboardingPreference = OnboardingPreference.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): OnboardingPreference {
        try {
            return OnboardingPreference.parseFrom(input)
        } catch (exception: IOException) {
            throw CorruptionException("Cannot read proto", exception)
        }
    }

    override suspend fun writeTo(t: OnboardingPreference, output: OutputStream) = t.writeTo(output)

}
