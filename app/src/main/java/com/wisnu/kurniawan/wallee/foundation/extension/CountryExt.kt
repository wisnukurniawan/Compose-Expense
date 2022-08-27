package com.wisnu.kurniawan.wallee.foundation.extension

import android.content.Context
import android.telephony.TelephonyManager
import java.util.*

/**
 * Returns the upper-case ISO 3166-1 alpha-2 country code of the current registered operator's MCC
 * (Mobile Country Code), or the country code of the default Locale if not available.
 *
 * @param context A context to access the telephony service. If null, only the Locale can be used.
 * @return The upper-case ISO 3166-1 alpha-2 country code, or an empty String if unavailable.
 */
fun getCountryCode(context: Context): String {
    val telephonyManager: TelephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    val countryCode: String = telephonyManager.networkCountryIso
    return if (countryCode.isNotEmpty()) {
        countryCode.uppercase(Locale.US)
    } else {
        Locale.getDefault().country.uppercase(Locale.US)
    }
}
