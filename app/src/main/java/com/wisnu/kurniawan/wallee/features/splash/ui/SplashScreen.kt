package com.wisnu.kurniawan.wallee.features.splash.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.wisnu.kurniawan.wallee.foundation.uiextension.collectAsEffectWithLifecycle
import com.wisnu.kurniawan.wallee.foundation.window.WindowState
import com.wisnu.kurniawan.wallee.runtime.navigation.AuthFlow
import com.wisnu.kurniawan.wallee.runtime.navigation.MainFlow
import com.wisnu.kurniawan.wallee.runtime.navigation.home.HomeFlow

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel
) {
    val effect by viewModel.effect.collectAsEffectWithLifecycle()
    when (effect) {
        SplashEffect.NavigateToDashboard -> {
            LaunchedEffect(effect) {
                navController.navigate(HomeFlow.Root.route) {
                    popUpTo(MainFlow.Root.route) {
                        inclusive = true
                    }
                }
            }
        }
        SplashEffect.NavigateToLogin -> {
            LaunchedEffect(effect) {
                navController.navigate(AuthFlow.Root.route) {
                    popUpTo(MainFlow.Root.route) {
                        inclusive = true
                    }
                }
            }
        }
        null -> {}
    }
}

@Composable
fun SplashLargeScreen(
    navController: NavController,
    viewModel: SplashViewModel,
    windowState: WindowState
) {
    val effect by viewModel.effect.collectAsEffectWithLifecycle()
    val isDualPortrait = windowState.isDualPortrait()
    when (effect) {
        SplashEffect.NavigateToDashboard -> {
            LaunchedEffect(effect) {
                navController.navigate(HomeFlow.Root.route(isDualPortrait.toString())) {
                    popUpTo(MainFlow.Root.route) {
                        inclusive = true
                    }
                }
            }
        }
        SplashEffect.NavigateToLogin -> {
            LaunchedEffect(effect) {
                navController.navigate(AuthFlow.Root.route) {
                    popUpTo(MainFlow.Root.route) {
                        inclusive = true
                    }
                }
            }
        }
        null -> {}
    }
}
