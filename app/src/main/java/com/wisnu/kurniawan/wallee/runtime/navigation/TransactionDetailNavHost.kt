package com.wisnu.kurniawan.wallee.runtime.navigation

import androidx.compose.runtime.MutableState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.wisnu.kurniawan.wallee.features.transaction.detail.ui.AccountSelectionScreen
import com.wisnu.kurniawan.wallee.features.transaction.detail.ui.CategorySelectionScreen
import com.wisnu.kurniawan.wallee.features.transaction.detail.ui.TransactionDetailScreen
import com.wisnu.kurniawan.wallee.features.transaction.detail.ui.TransactionDetailViewModel
import com.wisnu.kurniawan.wallee.features.transaction.detail.ui.TransferAccountSelectionScreen

@OptIn(ExperimentalMaterialNavigationApi::class)
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
            val viewModel = hiltViewModel<TransactionDetailViewModel>()
            TransactionDetailScreen(navController, viewModel)
        }

        bottomSheet(TransactionDetailFlow.SelectAccount.route) {
            val viewModel = if (navController.previousBackStackEntry != null) {
                hiltViewModel<TransactionDetailViewModel>(
                    navController.previousBackStackEntry!!
                )
            } else {
                hiltViewModel()
            }
            bottomSheetConfig.value = DefaultBottomSheetConfig

            AccountSelectionScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        bottomSheet(TransactionDetailFlow.SelectTransferAccount.route) {
            val viewModel = if (navController.previousBackStackEntry != null) {
                hiltViewModel<TransactionDetailViewModel>(
                    navController.previousBackStackEntry!!
                )
            } else {
                hiltViewModel()
            }
            bottomSheetConfig.value = DefaultBottomSheetConfig

            TransferAccountSelectionScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        bottomSheet(TransactionDetailFlow.SelectCategory.route) {
            val viewModel = if (navController.previousBackStackEntry != null) {
                hiltViewModel<TransactionDetailViewModel>(
                    navController.previousBackStackEntry!!
                )
            } else {
                hiltViewModel()
            }
            bottomSheetConfig.value = DefaultBottomSheetConfig

            CategorySelectionScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}
