package com.wisnu.kurniawan.wallee.runtime.navigation.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.wisnu.kurniawan.wallee.features.transaction.summary.ui.TransactionSummaryScreen

fun NavGraphBuilder.TransactionSummaryNavHost(
    mainNavController: NavController,
) {
    navigation(
        route = TransactionSummaryFlow.Root.route,
        startDestination = TransactionSummaryFlow.TransactionSummaryScreen.route
    ) {
        composable(TransactionSummaryFlow.TransactionSummaryScreen.route) {
            TransactionSummaryScreen(
                mainNavController = mainNavController,
            )
        }
    }
}
