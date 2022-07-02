package com.wisnu.kurniawan.wallee.runtime.navigation.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.wisnu.kurniawan.wallee.features.balance.summary.ui.BalanceSummaryScreen

fun NavGraphBuilder.BalanceSummaryNavHost(
    mainNavController: NavController,
) {
    navigation(
        route = BalanceSummaryFlow.Root.route,
        startDestination = BalanceSummaryFlow.BalanceSummaryScreen.route
    ) {
        composable(BalanceSummaryFlow.BalanceSummaryScreen.route) {
            BalanceSummaryScreen()
        }
    }
}
