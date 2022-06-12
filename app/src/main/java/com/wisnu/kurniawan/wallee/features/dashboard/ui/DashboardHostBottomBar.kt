package com.wisnu.kurniawan.wallee.features.dashboard.ui

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController

// TODO adjust background color
@Composable
fun DashboardBottomBar(
    navController: NavController,
    viewModel: DashboardHostViewModel,
) {
    val state by viewModel.state.collectAsState()

    DashboardBottomBar(
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
    sections: List<DashboardSection>,
    onTabClick: (SectionType) -> Unit
) {
    NavigationBar(
        modifier = Modifier.navigationBarsPadding(),
    ) {
        sections.forEachIndexed { _, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(stringResource(item.title)) },
                selected = item.selected,
                onClick = { onTabClick(item.sectionType) }
            )
        }
    }
}
