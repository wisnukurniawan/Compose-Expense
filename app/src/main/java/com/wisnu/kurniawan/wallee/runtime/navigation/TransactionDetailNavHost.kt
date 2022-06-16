package com.wisnu.kurniawan.wallee.runtime.navigation

import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.wisnu.kurniawan.wallee.features.transaction.detail.ui.TransactionDetailScreen

fun NavGraphBuilder.TransactionDetailNavHost(
    navController: NavHostController,
    bottomSheetConfig: MutableState<BottomSheetConfig>
) {
    navigation(
        startDestination = TransactionDetailFlow.TransactionDetail.route,
        route = TransactionDetailFlow.Root.route
    ) {
        composable(
            route = TransactionDetailFlow.TransactionDetail.route,
            arguments = TransactionDetailFlow.TransactionDetail.arguments
        ) {
            TransactionDetailScreen(navController)
        }
    }
}
