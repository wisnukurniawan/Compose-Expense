package com.wisnu.kurniawan.wallee

import app.cash.turbine.test
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.Flow
import org.junit.jupiter.api.Assertions

@ExperimentalTime
suspend fun <T> Flow<T>.expect(expected: Any) {
    test {
        Assertions.assertEquals(
            expected,
            awaitItem()
        )
        cancelAndConsumeRemainingEvents()
    }
}
