package com.wisnu.kurniawan.wallee.features.dashboard.ui

import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgIcon

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun DashboardNavRail(
    currentDestination: NavDestination?,
    viewModel: DashboardHostViewModel,
    onTabClick: (String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    DashboardNavRail(
        sections = state.sections,
        currentDestination = currentDestination,
        onTabClick = {
            onTabClick(it.route)
        }
    )
}

@Composable
private fun DashboardNavRail(
    sections: List<DashboardSection>,
    currentDestination: NavDestination?,
    onTabClick: (DashboardSection) -> Unit
) {
    NavigationRail(
        modifier = Modifier.safeDrawingPadding(),
        containerColor = Color.Transparent,
    ) {
        sections.forEachIndexed { _, item ->
            val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true

            NavigationRailItem(
                selected = selected,
                onClick = { onTabClick(item) },
                icon = {
                    val color = if (selected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        LocalContentColor.current
                    }
                    PgIcon(imageVector = item.icon, tint = color)
                },
                label = { Text(stringResource(item.title)) },
                colors = NavigationRailItemDefaults.colors(
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    }
}
