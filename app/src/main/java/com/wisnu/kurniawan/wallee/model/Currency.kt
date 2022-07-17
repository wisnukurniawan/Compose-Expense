package com.wisnu.kurniawan.wallee.model

/**
 * Currency code follow ISO 4217
 * https://en.wikipedia.org/wiki/ISO_4217
 */
data class Currency(
    val currencyCode: String,
    val countryCode: String
) {
    companion object {
        val DEFAULT = Currency("USD", "US")
    }
}
