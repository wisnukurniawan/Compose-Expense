package com.wisnu.kurniawan.wallee.runtime.navigation

import androidx.compose.material.navigation.bottomSheet
import androidx.compose.runtime.MutableState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.wisnu.kurniawan.wallee.features.account.detail.ui.AccountDetailScreen
import com.wisnu.kurniawan.wallee.features.account.detail.ui.AccountDetailViewModel
import com.wisnu.kurniawan.wallee.features.account.detail.ui.AccountTypeSelectionScreen

fun NavGraphBuilder.AccountDetailNavHost(
    navController: NavHostController,
    bottomSheetConfig: MutableState<BottomSheetConfig>
) {
    navigation(
        route = AccountDetailFlow.Root.route,
        startDestination = AccountDetailFlow.AccountDetail.route
    ) {
        composable(
            route = AccountDetailFlow.AccountDetail.route,
            arguments = AccountDetailFlow.AccountDetail.arguments
        ) {
            val viewModel = hiltViewModel<AccountDetailViewModel>()
            AccountDetailScreen(
                viewModel = viewModel,
                onClosePage = { navController.navigateUp() },
                onCancelClick = { navController.navigateUp() },
                onCategorySectionClick = { navController.navigate(AccountDetailFlow.SelectCategory.route) }
            )
        }

        bottomSheet(AccountDetailFlow.SelectCategory.route) {
            val viewModel = if (navController.previousBackStackEntry != null) {
                hiltViewModel<AccountDetailViewModel>(
                    navController.previousBackStackEntry!!
                )
            } else {
                hiltViewModel()
            }
            bottomSheetConfig.value = DefaultBottomSheetConfig

            AccountTypeSelectionScreen(
                viewModel = viewModel,
                onClick = { navController.navigateUp() }
            )
        }
    }
}
