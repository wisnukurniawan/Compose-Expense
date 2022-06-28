package com.wisnu.kurniawan.wallee.foundation.extension

import com.wisnu.kurniawan.wallee.model.Currency
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class CurrencyTest {

    @ParameterizedTest
    @CsvSource(
        value = [
            "0; 0",
            "0.06; 0,06",
            "123; 123",
            "123.4; 123,4",
            "1234.56; 1.234,56",
            "12345678.9; 12.345.678,9",
            "12345678.93; 12.345.678,93",
            "123456789; 123.456.789",
            "123456789222; 123.456.789.222",
        ],
        delimiter = ';'
    )
    fun testFormatAsDisplay(
        amount: String,
        formattedAmount: String
    ) {
        Assertions.assertEquals(
            formattedAmount,
            Currency.INDONESIA.formatAsDisplay(
                amount.toBigDecimal(),
                ""
            )
        )
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "0; 0",
            "0.06; 0,06",
            "123; 123",
            "123.4; 123,4",
            "1234.56; 1.234,56",
            "12345678.9; 12.345.678,9",
            "12345678.93; 12.345.678,93",
            "123456789; 123.456.789",
            "123456789222; 123.456.789.222",
        ],
        delimiter = ';'
    )
    fun testDisplayToDecimal(
        amount: String,
        formattedAmount: String
    ) {
        Assertions.assertEquals(
            amount,
            Currency.INDONESIA.parseAsDecimal(
                formattedAmount,
                ""
            ).toString()
        )
    }

}
