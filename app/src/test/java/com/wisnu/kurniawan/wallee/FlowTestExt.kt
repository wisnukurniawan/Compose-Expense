package com.wisnu.kurniawan.wallee

import app.cash.turbine.test
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.Flow
import org.junit.Assert

@ExperimentalTime
suspend fun <T> Flow<T>.expect(expected: Any) {
    test {
        Assert.assertEquals(
            expected,
            awaitItem()
        )
        cancelAndConsumeRemainingEvents()
    }
}
