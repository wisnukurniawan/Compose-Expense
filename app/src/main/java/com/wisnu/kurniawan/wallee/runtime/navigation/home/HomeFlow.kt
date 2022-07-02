package com.wisnu.kurniawan.wallee.runtime.navigation.home

sealed class HomeFlow(val name: String) {
    object Root : HomeFlow("home-root") {
        val route = name
    }

    object DashboardScreen : HomeFlow("dashboard-screen") {
        val route = name
    }

    object TransactionSummaryScreen : HomeFlow("transaction-summary-screen") {
        val route = name
    }

    object BalanceSummaryScreen : HomeFlow("balance-summary-screen") {
        val route = name
    }
}


