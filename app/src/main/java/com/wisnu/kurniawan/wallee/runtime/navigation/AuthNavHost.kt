package com.wisnu.kurniawan.wallee.runtime.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.wisnu.kurniawan.wallee.features.login.ui.LoginScreen
import com.wisnu.kurniawan.wallee.features.login.ui.LoginViewModel
import com.wisnu.kurniawan.wallee.runtime.navigation.home.HomeFlow

fun NavGraphBuilder.AuthNavHost(navController: NavHostController) {
    navigation(
        startDestination = AuthFlow.LoginScreen.route,
        route = AuthFlow.Root.route
    ) {
        composable(route = AuthFlow.LoginScreen.route) {
            val viewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(
                viewModel = viewModel,
                onLogin = {
                    navController.navigate(HomeFlow.Root.route) {
                        popUpTo(AuthFlow.LoginScreen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}
