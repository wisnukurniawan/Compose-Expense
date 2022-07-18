package com.wisnu.kurniawan.wallee.features.splash.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.wisnu.kurniawan.wallee.foundation.viewmodel.HandleEffect
import com.wisnu.kurniawan.wallee.foundation.window.WindowState
import com.wisnu.kurniawan.wallee.runtime.navigation.AuthFlow
import com.wisnu.kurniawan.wallee.runtime.navigation.MainFlow
import com.wisnu.kurniawan.wallee.runtime.navigation.OnboardingFlow
import com.wisnu.kurniawan.wallee.runtime.navigation.home.HomeFlow

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel,
    windowState: WindowState
) {
    val isDualPortrait = windowState.isDualPortrait()

    HandleEffect(
        viewModel = viewModel,
    ) {
        when (it) {
            SplashEffect.NavigateToDashboard -> {
                navController.navigate(HomeFlow.Root.route(isDualPortrait.toString())) {
                    popUpTo(MainFlow.Root.route) {
                        inclusive = true
                    }
                }
            }
            SplashEffect.NavigateToLogin -> {
                navController.navigate(AuthFlow.Root.route) {
                    popUpTo(MainFlow.Root.route) {
                        inclusive = true
                    }
                }
            }
            SplashEffect.NavigateToOnboarding -> {
                navController.navigate(OnboardingFlow.Root.route) {
                    popUpTo(MainFlow.Root.route) {
                        inclusive = true
                    }
                }
            }
        }
    }
}
