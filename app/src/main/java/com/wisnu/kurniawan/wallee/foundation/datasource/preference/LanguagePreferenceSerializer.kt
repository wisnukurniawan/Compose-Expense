package com.wisnu.kurniawan.wallee.foundation.datasource.preference

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.wisnu.kurniawan.wallee.foundation.datasource.preference.model.LanguagePreference
import com.wisnu.kurniawan.wallee.model.Language
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object LanguagePreferenceSerializer : Serializer<LanguagePreference> {

    override val defaultValue: LanguagePreference = LanguagePreference.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): LanguagePreference {
        try {
            return LanguagePreference.parseFrom(input)
        } catch (exception: IOException) {
            throw CorruptionException("Cannot read proto", exception)
        }
    }

    override suspend fun writeTo(t: LanguagePreference, output: OutputStream) = t.writeTo(output)

}
