package com.wisnu.kurniawan.wallee.runtime.navigation.home

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import com.wisnu.kurniawan.wallee.features.transaction.all.ui.AllTransactionScreen
import com.wisnu.kurniawan.wallee.features.transaction.all.ui.AllTransactionViewModel
import com.wisnu.kurniawan.wallee.features.transaction.summary.ui.TransactionSummaryScreen
import com.wisnu.kurniawan.wallee.features.transaction.summary.ui.TransactionSummaryViewModel
import com.wisnu.kurniawan.wallee.features.transaction.topexpense.ui.TopExpenseScreen
import com.wisnu.kurniawan.wallee.features.transaction.topexpense.ui.TopExpenseViewModel
import com.wisnu.kurniawan.wallee.runtime.navigation.SettingFlow
import com.wisnu.kurniawan.wallee.runtime.navigation.TransactionDetailFlow

fun NavGraphBuilder.TransactionSummaryNavHost(
    mainNavController: NavController,
    navController: NavController,
) {
    navigation(
        route = TransactionSummaryFlow.Root.route,
        startDestination = TransactionSummaryFlow.TransactionSummaryScreen.route
    ) {
        composable(TransactionSummaryFlow.TransactionSummaryScreen.route) {
            val viewModel = hiltViewModel<TransactionSummaryViewModel>()
            TransactionSummaryScreen(
                viewModel = viewModel,
                route = null,
                arguments = null,
                onSettingClick = { mainNavController.navigate(SettingFlow.Root.route) },
                onClickAddTransaction = { mainNavController.navigate(TransactionDetailFlow.Root.route()) },
                onLastTransactionItemClick = { mainNavController.navigate(TransactionDetailFlow.Root.route(it)) },
                onSeeMoreLastTransactionClick = { navController.navigate(TransactionSummaryFlow.AllTransactionScreen.route) },
                onSeeMoreTopExpenseClick = { navController.navigate(TransactionSummaryFlow.TopExpenseScreen.route) }
            )
        }
        composable(TransactionSummaryFlow.AllTransactionScreen.route) {
            val viewModel = hiltViewModel<AllTransactionViewModel>()
            AllTransactionScreen(
                viewModel = viewModel,
                onTransactionItemClick = {
                    mainNavController.navigate(TransactionDetailFlow.Root.route(it))
                }
            )
        }
        composable(TransactionSummaryFlow.TopExpenseScreen.route) {
            val viewModel = hiltViewModel<TopExpenseViewModel>()
            TopExpenseScreen(
                viewModel = viewModel
            )
        }
    }
}

fun NavGraphBuilder.TransactionSummaryLeftNavHost(
    mainNavController: NavController,
    rightNavController: NavController,
) {
    navigation(
        route = TransactionSummaryFlow.Root.route,
        startDestination = TransactionSummaryFlow.TransactionSummaryScreen.route
    ) {
        composable(TransactionSummaryFlow.TransactionSummaryScreen.route) {
            val viewModel = hiltViewModel<TransactionSummaryViewModel>()
            val navBackStackEntry by rightNavController.currentBackStackEntryAsState()

            TransactionSummaryScreen(
                viewModel = viewModel,
                route = navBackStackEntry?.destination?.route,
                arguments = navBackStackEntry?.arguments,
                onSettingClick = { mainNavController.navigate(SettingFlow.Root.route) },
                onClickAddTransaction = {
                    rightNavController.navigate(TransactionDetailFlow.Root.route()) {
                        popUpTo(TransactionSummaryFlow.RootEmpty.route)
                    }
                },
                onSeeMoreLastTransactionClick = {
                    rightNavController.navigate(TransactionSummaryFlow.RootAllTransaction.route) {
                        popUpTo(TransactionSummaryFlow.RootEmpty.route)
                    }
                },
                onLastTransactionItemClick = {
                    rightNavController.navigate(TransactionDetailFlow.Root.route(it)) {
                        popUpTo(TransactionSummaryFlow.RootEmpty.route)
                    }
                },
                onSeeMoreTopExpenseClick = {
                    rightNavController.navigate(TransactionSummaryFlow.RootTopExpense.route) {
                        popUpTo(TransactionSummaryFlow.RootEmpty.route)
                    }
                }
            )
        }
    }
}

fun NavGraphBuilder.TopExpenseScreenRightNavHost() {
    navigation(
        route = TransactionSummaryFlow.RootTopExpense.route,
        startDestination = TransactionSummaryFlow.TopExpenseScreen.route
    ) {
        composable(TransactionSummaryFlow.TopExpenseScreen.route) {
            val viewModel = hiltViewModel<TopExpenseViewModel>()
            TopExpenseScreen(
                viewModel = viewModel
            )
        }
    }
}

fun NavGraphBuilder.AllTransactionScreenRightNavHost(
    rightNavController: NavController,
) {
    navigation(
        route = TransactionSummaryFlow.RootAllTransaction.route,
        startDestination = TransactionSummaryFlow.AllTransactionScreen.route
    ) {
        composable(TransactionSummaryFlow.AllTransactionScreen.route) {
            val viewModel = hiltViewModel<AllTransactionViewModel>()
            AllTransactionScreen(
                viewModel = viewModel,
                onTransactionItemClick = {
                    rightNavController.navigate(TransactionDetailFlow.Root.route(it))
                },
            )
        }
    }
}
