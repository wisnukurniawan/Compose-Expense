package com.wisnu.kurniawan.wallee.runtime.navigation.home

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.wisnu.kurniawan.wallee.features.transaction.all.ui.AllTransactionRightScreen
import com.wisnu.kurniawan.wallee.features.transaction.all.ui.AllTransactionScreen
import com.wisnu.kurniawan.wallee.features.transaction.all.ui.AllTransactionViewModel
import com.wisnu.kurniawan.wallee.features.transaction.summary.ui.TransactionSummaryLeftScreen
import com.wisnu.kurniawan.wallee.features.transaction.summary.ui.TransactionSummaryScreen
import com.wisnu.kurniawan.wallee.features.transaction.summary.ui.TransactionSummaryViewModel
import com.wisnu.kurniawan.wallee.features.transaction.topexpense.ui.TopExpenseScreen
import com.wisnu.kurniawan.wallee.features.transaction.topexpense.ui.TopExpenseViewModel

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
                mainNavController = mainNavController,
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(TransactionSummaryFlow.AllTransactionScreen.route) {
            val viewModel = hiltViewModel<AllTransactionViewModel>()
            AllTransactionScreen(
                mainNavController = mainNavController,
                viewModel = viewModel
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
            TransactionSummaryLeftScreen(
                mainNavController = mainNavController,
                rightNavController = rightNavController,
                viewModel = viewModel
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
            AllTransactionRightScreen(
                rightNavController = rightNavController,
                viewModel = viewModel
            )
        }
    }
}
