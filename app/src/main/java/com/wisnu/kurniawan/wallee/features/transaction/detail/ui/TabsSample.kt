package com.wisnu.kurniawan.wallee.features.transaction.detail.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wisnu.kurniawan.wallee.foundation.theme.Shapes

@Preview
@Composable
fun FancyIndicatorTabs() {
    var state by remember { mutableStateOf(0) }
    val titles = listOf("TAB 1", "TAB 2", "TAB 3")

    // Reuse the default offset animation modifier, but use our own indicator
    val indicator = @Composable { tabPositions: List<TabPosition> ->
        FancyIndicator(Color.White, Modifier.tabIndicatorOffset(tabPositions[state]))
    }

    Column {
        TabRow(
            selectedTabIndex = state,
            indicator = indicator
        ) {
            titles.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = state == index,
                    onClick = { state = index }
                )
            }
        }
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Fancy indicator tab ${state + 1} selected",
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
fun FancyIndicator(color: Color, modifier: Modifier = Modifier) {
    // Draws a rounded rectangular with border around the Tab, with a 5.dp padding from the edges
    // Color is passed in as a parameter [color]
    Box(
        modifier
            .padding(5.dp)
            .fillMaxSize()
            .border(BorderStroke(2.dp, color), Shapes.small)
    )
}
