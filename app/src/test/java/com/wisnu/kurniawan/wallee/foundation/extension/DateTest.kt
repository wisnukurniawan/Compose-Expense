package com.wisnu.kurniawan.wallee.foundation.extension

import com.wisnu.kurniawan.wallee.DateFactory
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class DateTest {

    @Test
    fun testFormatDateTime() {
        Assertions.assertEquals(
            "Fri, 01 Jan 2021",
            DateFactory.constantDate.formatDateTime()
        )
    }

    @Test
    fun testFormatDate() {
        Assertions.assertEquals(
            "01 Jan 2021",
            DateFactory.constantDate.formatDate()
        )
    }

    @Test
    fun testFormatMonth() {
        Assertions.assertEquals(
            "Jan 2021",
            DateFactory.constantDate.formatMonth()
        )
    }

    @Test
    fun testToLocalDateTime() {
        Assertions.assertEquals(
            "2021-01-01T00:00",
            DateFactory.constantDate2.toLocalDateTime().toString()
        )
    }

}
