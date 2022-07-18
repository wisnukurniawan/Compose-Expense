package com.wisnu.kurniawan.wallee.foundation.extension

import com.wisnu.kurniawan.wallee.model.Currency
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(JUnitParamsRunner::class)
class CurrencyTest {

    @Test
    @Parameters(
        value = [
            "0|0",
            "0.06|0\\,06",
            "123|123",
            "123.4|123\\,4",
            "1234.56|1.234\\,56",
            "12345678.9|12.345.678\\,9",
            "12345678.93|12.345.678\\,93",
            "123456789|123.456.789",
            "123456789222|123.456.789.222",
        ]
    )
    fun testFormatAsDisplay(
        output: String,
        input: String
    ) {
        Assert.assertEquals(
            input,
            Currency("IDR", "ID").formatAsDisplay(
                output.toBigDecimal()
            )
        )
    }

    @Test
    @Parameters(
        value = [
            "0|0",
            "0.06|0\\,06",
            "123|123",
            "123.4|123\\,4",
            "1234.56|1.234\\,56",
            "12345678.9|12.345.678\\,9",
            "12345678.93|12.345.678\\,93",
            "123456789|123.456.789",
            "123456789222|123.456.789.222",
            "555000.88|555.000\\,88",
        ],
    )
    fun testDisplayToDecimal(
        output: String,
        input: String
    ) {
        Assert.assertEquals(
            output,
            Currency("IDR", "ID").parseAsDecimal(
                input,
                false
            ).toString()
        )
    }
}
