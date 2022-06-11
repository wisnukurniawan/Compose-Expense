package com.wisnu.kurniawan.wallee.features.dashboard.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController

@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: DashboardViewModel,
) {
    val state by viewModel.state.collectAsState()

    DashboardScreen()
}

@Composable
private fun DashboardScreen(

) {

}

