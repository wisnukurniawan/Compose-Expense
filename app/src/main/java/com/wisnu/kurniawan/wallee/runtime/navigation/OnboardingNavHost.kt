package com.wisnu.kurniawan.wallee.runtime.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.wisnu.kurniawan.wallee.features.onboarding.ui.OnboardingScreen
import com.wisnu.kurniawan.wallee.features.onboarding.ui.OnboardingViewModel
import com.wisnu.kurniawan.wallee.runtime.navigation.home.HomeFlow

fun NavGraphBuilder.OnboardingNavHost(navController: NavHostController) {
    navigation(
        route = OnboardingFlow.Root.route,
        startDestination = OnboardingFlow.OnboardingScreen.route,
    ) {
        composable(route = OnboardingFlow.OnboardingScreen.route) {
            val viewModel = hiltViewModel<OnboardingViewModel>()
            OnboardingScreen(
                viewModel = viewModel,
                onClosePage = {
                    navController.navigate(HomeFlow.Root.route) {
                        popUpTo(OnboardingFlow.Root.route) {
                            inclusive = true
                        }
                    }
                },
                onSettingClick = {
                    navController.navigate(SettingFlow.Root.route)
                }
            )
        }
    }
}
