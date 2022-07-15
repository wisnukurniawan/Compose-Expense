package com.wisnu.kurniawan.wallee.runtime.navigation.home

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.wisnu.kurniawan.wallee.features.balance.summary.ui.BalanceSummaryLeftScreen
import com.wisnu.kurniawan.wallee.features.balance.summary.ui.BalanceSummaryScreen
import com.wisnu.kurniawan.wallee.features.balance.summary.ui.BalanceSummaryViewModel

fun NavGraphBuilder.BalanceSummaryNavHost(
    mainNavController: NavController,
) {
    navigation(
        route = BalanceSummaryFlow.Root.route,
        startDestination = BalanceSummaryFlow.BalanceSummaryScreen.route
    ) {
        composable(BalanceSummaryFlow.BalanceSummaryScreen.route) {
            val viewModel = hiltViewModel<BalanceSummaryViewModel>()
            BalanceSummaryScreen(
                mainNavController = mainNavController,
                viewModel = viewModel
            )
        }
    }
}
fun NavGraphBuilder.BalanceSummaryLeftNavHost(
    rightNavController: NavController,
) {
    navigation(
        route = BalanceSummaryFlow.Root.route,
        startDestination = BalanceSummaryFlow.BalanceSummaryScreen.route
    ) {
        composable(BalanceSummaryFlow.BalanceSummaryScreen.route) {
            val viewModel = hiltViewModel<BalanceSummaryViewModel>()
            BalanceSummaryLeftScreen(
                rightNavController = rightNavController,
                viewModel = viewModel
            )
        }
    }
}
