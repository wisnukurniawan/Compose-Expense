package com.wisnu.kurniawan.wallee.runtime.navigation.home

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import com.wisnu.kurniawan.wallee.features.balance.summary.ui.BalanceSummaryScreen
import com.wisnu.kurniawan.wallee.features.balance.summary.ui.BalanceSummaryViewModel
import com.wisnu.kurniawan.wallee.runtime.navigation.AccountDetailFlow

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
                viewModel = viewModel,
                route = null,
                arguments = null,
                onClickAccount = {
                    mainNavController.navigate(AccountDetailFlow.Root.route(it))
                },
                onClickAddAccount = {
                    mainNavController.navigate(AccountDetailFlow.Root.route())
                }
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
            val navBackStackEntry by rightNavController.currentBackStackEntryAsState()

            BalanceSummaryScreen(
                viewModel = viewModel,
                route = navBackStackEntry?.destination?.route,
                arguments = navBackStackEntry?.arguments,
                onClickAccount = {
                    rightNavController.navigate(AccountDetailFlow.Root.route(it)) {
                        popUpTo(TransactionSummaryFlow.RootEmpty.route)
                    }
                },
                onClickAddAccount = {
                    rightNavController.navigate(AccountDetailFlow.Root.route()) {
                        popUpTo(TransactionSummaryFlow.RootEmpty.route)
                    }
                }
            )
        }
    }
}
