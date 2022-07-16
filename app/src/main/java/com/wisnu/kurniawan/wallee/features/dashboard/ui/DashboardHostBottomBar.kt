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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgIcon

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun DashboardBottomBar(
    modifier: Modifier,
    currentDestination: NavDestination?,
    viewModel: DashboardHostViewModel,
    onTabClick: (String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    DashboardBottomBar(
        modifier = modifier,
        sections = state.sections,
        currentDestination = currentDestination,
        onTabClick = {
            onTabClick(it.route)
        }
    )
}

@Composable
private fun DashboardBottomBar(
    modifier: Modifier,
    sections: List<DashboardSection>,
    currentDestination: NavDestination?,
    onTabClick: (DashboardSection) -> Unit
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
            val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true

            NavigationBarItem(
                icon = {
                    val color = if (selected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        LocalContentColor.current
                    }
                    PgIcon(imageVector = item.icon, tint = color)
                },
                label = { Text(stringResource(item.title)) },
                selected = selected,
                onClick = { onTabClick(item) },
                colors = NavigationBarItemDefaults.colors(
                    selectedTextColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}
