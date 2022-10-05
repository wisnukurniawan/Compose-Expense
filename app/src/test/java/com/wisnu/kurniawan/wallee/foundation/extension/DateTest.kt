package com.wisnu.kurniawan.wallee.foundation.extension

import com.wisnu.foundation.coredatetime.formatDate
import com.wisnu.foundation.coredatetime.formatDateTime
import com.wisnu.foundation.coredatetime.formatMonth
import com.wisnu.foundation.coredatetime.toLocalDateTime
import com.wisnu.kurniawan.wallee.DateFactory
import org.junit.Assert
import org.junit.Test

class DateTest {

    @Test
    fun testFormatDateTime() {
        Assert.assertEquals(
            "Fri, 01 Jan 2021",
            DateFactory.constantDate.formatDateTime()
        )
    }

    @Test
    fun testFormatDate() {
        Assert.assertEquals(
            "01 Jan 2021",
            DateFactory.constantDate.formatDate()
        )
    }

    @Test
    fun testFormatMonth() {
        Assert.assertEquals(
            "Jan 2021",
            DateFactory.constantDate.formatMonth()
        )
    }

    @Test
    fun testToLocalDateTime() {
        Assert.assertEquals(
            "2021-01-01T00:00",
            DateFactory.constantDate2.toLocalDateTime().toString()
        )
    }

}
