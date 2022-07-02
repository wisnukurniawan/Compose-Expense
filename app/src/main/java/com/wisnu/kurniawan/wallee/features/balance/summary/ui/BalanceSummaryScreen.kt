package com.wisnu.kurniawan.wallee.features.balance.summary.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun BalanceSummaryScreen() {
    Box {
        Text(
            text = "Balance summary",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
