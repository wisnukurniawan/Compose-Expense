package com.wisnu.kurniawan.wallee.features.dashboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgIcon

@Composable
fun DashboardBottomBar(
    modifier: Modifier,
    navController: NavController,
    viewModel: DashboardHostViewModel,
) {
    val state by viewModel.state.collectAsState()

    DashboardBottomBar(
        modifier = modifier,
        sections = state.sections,
        onTabClick = {
            viewModel.dispatch(DashboardHostAction.ClickTab(it))
            when (it) {
                SectionType.TRANSACTION -> {
                    // TODO open transaction page
                }
                SectionType.BALANCE -> {
                    // TODO open balance
                }
            }
        }
    )
}

@Composable
private fun DashboardBottomBar(
    modifier: Modifier,
    sections: List<DashboardSection>,
    onTabClick: (SectionType) -> Unit
) {
    NavigationBar(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color.Transparent,
                        MaterialTheme.colorScheme.surface,
                        MaterialTheme.colorScheme.surface,
                        MaterialTheme.colorScheme.surface,
                    )
                )
            )
            .navigationBarsPadding(),
        containerColor = Color.Transparent,
    ) {
        sections.forEachIndexed { _, item ->
            NavigationBarItem(
                icon = {
                    val color = if (item.selected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        LocalContentColor.current
                    }
                    PgIcon(imageVector = item.icon, tint = color)
                },
                label = { Text(stringResource(item.title)) },
                selected = item.selected,
                onClick = { onTabClick(item.sectionType) },
                colors = NavigationBarItemDefaults.colors(
                    selectedTextColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}
