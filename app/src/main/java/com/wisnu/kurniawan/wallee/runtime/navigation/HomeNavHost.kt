package com.wisnu.kurniawan.wallee.runtime.navigation

import androidx.compose.runtime.MutableState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.wisnu.kurniawan.wallee.features.dashboard.ui.DashboardScreen
import com.wisnu.kurniawan.wallee.features.dashboard.ui.DashboardViewModel

fun NavGraphBuilder.HomeNavHost(
    navController: NavHostController,
    bottomSheetConfig: MutableState<MainBottomSheetConfig>
) {
    navigation(startDestination = HomeFlow.DashboardScreen.route, route = HomeFlow.Root.route) {
        composable(HomeFlow.DashboardScreen.route) {
            val viewModel = hiltViewModel<DashboardViewModel>()

            DashboardScreen(
                navController = navController,
                viewModel = viewModel,
            )
        }
        HomeBottomSheetNavHost(
            bottomSheetConfig = bottomSheetConfig,
            navController = navController
        )
    }
}

@OptIn(ExperimentalMaterialNavigationApi::class)
private fun NavGraphBuilder.HomeBottomSheetNavHost(
    bottomSheetConfig: MutableState<MainBottomSheetConfig>,
    navController: NavHostController
) {

}
