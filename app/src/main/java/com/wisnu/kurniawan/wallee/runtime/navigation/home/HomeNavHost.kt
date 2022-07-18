package com.wisnu.kurniawan.wallee.runtime.navigation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.ModalBottomSheetDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.wisnu.kurniawan.wallee.features.dashboard.ui.DashboardBottomBar
import com.wisnu.kurniawan.wallee.features.dashboard.ui.DashboardHostViewModel
import com.wisnu.kurniawan.wallee.features.dashboard.ui.DashboardNavRail
import com.wisnu.kurniawan.wallee.foundation.uiextension.navigateToTopLevel
import com.wisnu.kurniawan.wallee.foundation.uiextension.rememberBottomSheetNavigator
import com.wisnu.kurniawan.wallee.runtime.navigation.ARG_IS_DUAL_PORTRAIT
import com.wisnu.kurniawan.wallee.runtime.navigation.AccountDetailNavHost
import com.wisnu.kurniawan.wallee.runtime.navigation.BottomSheetConfig
import com.wisnu.kurniawan.wallee.runtime.navigation.DefaultBottomSheetConfig
import com.wisnu.kurniawan.wallee.runtime.navigation.TransactionDetailNavHost

fun NavGraphBuilder.HomeNavHost(
    navController: NavHostController,
) {
    navigation(
        route = HomeFlow.Root.route,
        startDestination = HomeFlow.DashboardScreen.route
    ) {
        composable(HomeFlow.DashboardScreen.route) {
            SmallScreen(navController)
        }
    }
}

fun NavGraphBuilder.LargeHomeNavHost(
    navController: NavHostController,
) {
    navigation(
        route = HomeFlow.Root.route,
        startDestination = HomeFlow.DashboardScreen.route,
    ) {
        composable(
            route = HomeFlow.DashboardScreen.route,
            arguments = HomeFlow.DashboardScreen.arguments
        ) {
            val isDualPortrait = it.arguments?.getString(ARG_IS_DUAL_PORTRAIT).toBoolean()
            if (isDualPortrait) {
                LargeScreen(navController, 1F, 1F)
            } else {
                LargeScreen(navController, 0.45F, 0.55F)
            }
        }
    }
}

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
private fun SmallScreen(
    navController: NavHostController,
) {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val bottomSheetConfig = remember { mutableStateOf(DefaultBottomSheetConfig) }
    val homeNavController = rememberNavController(bottomSheetNavigator)
    val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val viewModel = hiltViewModel<DashboardHostViewModel>()

    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        sheetShape = bottomSheetConfig.value.sheetShape,
        scrimColor = if (bottomSheetConfig.value.showScrim) {
            ModalBottomSheetDefaults.scrimColor
        } else {
            Color.Transparent
        }
    ) {
        Box(Modifier.fillMaxSize()) {
            NavHostSmall(
                navController,
                homeNavController,
            )

            DashboardBottomBar(
                modifier = Modifier.align(Alignment.BottomStart),
                viewModel = viewModel,
                currentDestination = currentDestination,
                onTabClick = {
                    homeNavController.navigateToTopLevel(it)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
private fun LargeScreen(
    mainNavController: NavHostController,
    weightLeft: Float,
    weightRight: Float,
) {
    val leftBottomSheetNavigator = rememberBottomSheetNavigator()
    val leftBottomSheetConfig = remember { mutableStateOf(DefaultBottomSheetConfig) }
    val leftNavController = rememberNavController(leftBottomSheetNavigator)

    val rightBottomSheetNavigator = rememberBottomSheetNavigator()
    val rightBottomSheetConfig = remember { mutableStateOf(DefaultBottomSheetConfig) }
    val rightNavController = rememberNavController(rightBottomSheetNavigator)

    val navBackStackEntry by leftNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val viewModel = hiltViewModel<DashboardHostViewModel>()

    Row(modifier = Modifier.fillMaxSize()) {
        // Left column
        Box(modifier = Modifier.fillMaxHeight().weight(weightLeft)) {
            ModalBottomSheetLayout(
                bottomSheetNavigator = leftBottomSheetNavigator,
                sheetShape = leftBottomSheetConfig.value.sheetShape,
                scrimColor = if (leftBottomSheetConfig.value.showScrim) {
                    ModalBottomSheetDefaults.scrimColor
                } else {
                    Color.Transparent
                }
            ) {
                Row(
                    Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(
                            WindowInsets.safeDrawing.only(
                                WindowInsetsSides.Horizontal
                            )
                        )
                ) {
                    DashboardNavRail(
                        viewModel = viewModel,
                        currentDestination = currentDestination,
                        onTabClick = {
                            // TODO should is make the right nav back?
                            leftNavController.navigateToTopLevel(it)
                        }
                    )

                    NavHostLeft(
                        mainNavController = mainNavController,
                        leftNavController = leftNavController,
                        rightNavController = rightNavController
                    )
                }
            }
        }

        // Divider
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .background(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
        )

        // Right column
        Box(modifier = Modifier.fillMaxHeight().weight(weightRight)) {
            ModalBottomSheetLayout(
                bottomSheetNavigator = rightBottomSheetNavigator,
                sheetShape = rightBottomSheetConfig.value.sheetShape,
                scrimColor = if (rightBottomSheetConfig.value.showScrim) {
                    ModalBottomSheetDefaults.scrimColor
                } else {
                    Color.Transparent
                }
            ) {
                NavHostLargeRight(
                    rightNavController = rightNavController,
                    rightBottomSheetConfig = rightBottomSheetConfig
                )
            }
        }
    }
}

@Composable
private fun NavHostSmall(
    mainNavController: NavController,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = TransactionSummaryFlow.Root.route,
    ) {
        TransactionSummaryNavHost(
            mainNavController = mainNavController,
            navController = navController,
        )

        BalanceSummaryNavHost(
            mainNavController = mainNavController,
        )
    }
}

@Composable
private fun NavHostLeft(
    mainNavController: NavController,
    leftNavController: NavHostController,
    rightNavController: NavHostController,
) {
    NavHost(
        navController = leftNavController,
        startDestination = TransactionSummaryFlow.Root.route,
    ) {
        TransactionSummaryLeftNavHost(
            mainNavController = mainNavController,
            rightNavController = rightNavController,
        )

        BalanceSummaryLeftNavHost(
            rightNavController = rightNavController,
        )
    }
}

@Composable
private fun NavHostLargeRight(
    rightNavController: NavHostController,
    rightBottomSheetConfig: MutableState<BottomSheetConfig>,
) {
    NavHost(
        navController = rightNavController,
        startDestination = TransactionSummaryFlow.RootEmpty.route,
    ) {
        composable(route = TransactionSummaryFlow.RootEmpty.route) {

        }

        TopExpenseScreenRightNavHost()

        AllTransactionScreenRightNavHost(
            rightNavController = rightNavController,
        )

        TransactionDetailNavHost(
            navController = rightNavController,
            bottomSheetConfig = rightBottomSheetConfig
        )

        AccountDetailNavHost(
            navController = rightNavController,
            bottomSheetConfig = rightBottomSheetConfig
        )
    }
}
