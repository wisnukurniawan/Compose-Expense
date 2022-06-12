package com.wisnu.kurniawan.wallee.runtime.navigation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ModalBottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.wisnu.kurniawan.wallee.features.dashboard.ui.DashboardBottomBar
import com.wisnu.kurniawan.wallee.features.dashboard.ui.DashboardHostViewModel
import com.wisnu.kurniawan.wallee.features.dashboard.ui.DashboardScreen
import com.wisnu.kurniawan.wallee.features.dashboard.ui.DashboardViewModel
import com.wisnu.kurniawan.wallee.foundation.uiextension.rememberBottomSheetNavigator
import com.wisnu.kurniawan.wallee.runtime.navigation.BottomSheetConfig
import com.wisnu.kurniawan.wallee.runtime.navigation.DefaultBottomSheetConfig

@OptIn(
    ExperimentalMaterialNavigationApi::class,
    ExperimentalMaterial3Api::class
)
fun NavGraphBuilder.HomeNavHost(
    navController: NavHostController
) {
    navigation(startDestination = HomeFlow.DashboardScreen.route, route = HomeFlow.Root.route) {
        composable(HomeFlow.DashboardScreen.route) {
            val bottomSheetNavigator = rememberBottomSheetNavigator()
            val bottomSheetConfig = remember { mutableStateOf(DefaultBottomSheetConfig) }
            val homeNavController = rememberNavController(bottomSheetNavigator)

            ModalBottomSheetLayout(
                bottomSheetNavigator = bottomSheetNavigator,
                sheetShape = bottomSheetConfig.value.sheetShape,
                scrimColor = if (bottomSheetConfig.value.showScrim) {
                    ModalBottomSheetDefaults.scrimColor
                } else {
                    Color.Transparent
                }
            ) {
                Column {
                    Box(Modifier.fillMaxSize().weight(1F)) {
                        SmallScreenNavHost(
                            homeNavController,
                            bottomSheetConfig,
                        )

                        val viewModel = hiltViewModel<DashboardHostViewModel>()
                        DashboardBottomBar(
                            modifier = Modifier.align(Alignment.BottomStart),
                            navController = homeNavController,
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SmallScreenNavHost(
    navController: NavHostController,
    bottomSheetConfig: MutableState<BottomSheetConfig>,
) {
    NavHost(
        navController = navController,
        startDestination = HomeFlow.DashboardScreen.route,
    ) {
        composable(HomeFlow.DashboardScreen.route) {
            val viewModel = hiltViewModel<DashboardViewModel>()
            DashboardScreen(
                navController = navController,
                viewModel = viewModel,
            )
        }

        SettingNavHost(navController, bottomSheetConfig)
    }
}
