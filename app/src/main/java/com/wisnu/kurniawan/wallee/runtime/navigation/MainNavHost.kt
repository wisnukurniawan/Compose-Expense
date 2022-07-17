package com.wisnu.kurniawan.wallee.runtime.navigation

import androidx.compose.material.ModalBottomSheetDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.wisnu.kurniawan.wallee.features.splash.ui.SplashScreen
import com.wisnu.kurniawan.wallee.features.splash.ui.SplashViewModel
import com.wisnu.kurniawan.wallee.foundation.uiextension.rememberBottomSheetNavigator
import com.wisnu.kurniawan.wallee.foundation.window.WindowState
import com.wisnu.kurniawan.wallee.runtime.navigation.home.HomeNavHost
import com.wisnu.kurniawan.wallee.runtime.navigation.home.LargeHomeNavHost

const val MinLargeScreenWidth = 585
const val BASE_DEEPLINK = "wallee://com.wisnu.kurniawan"

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun MainNavHost(windowState: WindowState) {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val bottomSheetConfig = remember { mutableStateOf(DefaultBottomSheetConfig) }

    val smallestScreenWidthDp = LocalConfiguration.current.smallestScreenWidthDp

    val isLargeScreen = smallestScreenWidthDp > MinLargeScreenWidth

    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        sheetShape = bottomSheetConfig.value.sheetShape,
        scrimColor = if (bottomSheetConfig.value.showScrim) {
            ModalBottomSheetDefaults.scrimColor
        } else {
            Color.Transparent
        }
    ) {
        if (isLargeScreen) {
            LargeScreenNavHost(bottomSheetNavigator, bottomSheetConfig, windowState)
        } else {
            SmallScreenNavHost(bottomSheetNavigator, bottomSheetConfig, windowState)
        }
    }
}

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
private fun SmallScreenNavHost(
    bottomSheetNavigator: BottomSheetNavigator,
    bottomSheetConfig: MutableState<BottomSheetConfig>,
    windowState: WindowState
) {
    val navController = rememberNavController(bottomSheetNavigator)
    NavHost(
        navController = navController,
        startDestination = MainFlow.Root.route
    ) {
        composable(route = MainFlow.Root.route) {
            val viewModel = hiltViewModel<SplashViewModel>()
            SplashScreen(navController = navController, viewModel = viewModel, windowState = windowState)
        }

        AuthNavHost(navController)

        OnboardingNavHost(navController)

        HomeNavHost(navController)

        TransactionDetailNavHost(navController, bottomSheetConfig)

        AccountDetailNavHost(navController, bottomSheetConfig)

        SettingNavHost(navController, bottomSheetConfig)
    }
}

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
private fun LargeScreenNavHost(
    bottomSheetNavigator: BottomSheetNavigator,
    bottomSheetConfig: MutableState<BottomSheetConfig>,
    windowState: WindowState
) {
    val navController = rememberNavController(bottomSheetNavigator)
    NavHost(
        navController = navController,
        startDestination = MainFlow.Root.route
    ) {
        composable(route = MainFlow.Root.route) {
            val viewModel = hiltViewModel<SplashViewModel>()
            SplashScreen(navController = navController, viewModel = viewModel, windowState = windowState)
        }

        AuthNavHost(navController)

        OnboardingNavHost(navController)

        LargeHomeNavHost(navController)

        SettingNavHost(navController, bottomSheetConfig)
    }
}
