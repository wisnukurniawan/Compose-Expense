package com.wisnu.kurniawan.wallee.runtime.navigation.home

sealed class HomeFlow(val name: String) {
    object Root : HomeFlow("home-root") {
        val route = name
    }

    object DashboardScreen : HomeFlow("dashboard-screen") {
        val route = name
    }
}

sealed class BalanceSummaryFlow(val name: String) {
    object Root : BalanceSummaryFlow("balance-summary-root") {
        val route = name
    }

    object BalanceSummaryScreen : BalanceSummaryFlow("balance-summary-screen") {
        val route = name
    }
}


sealed class TransactionSummaryFlow(val name: String) {
    object Root : TransactionSummaryFlow("transaction-summary-root") {
        val route = name
    }

    object TransactionSummaryScreen : TransactionSummaryFlow("transaction-summary-screen") {
        val route = name
    }
}


