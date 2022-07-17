package com.wisnu.kurniawan.wallee.runtime.navigation

import androidx.navigation.navArgument

sealed class MainFlow(val name: String) {
    object Root : MainFlow("main-root") {
        val route = name
    }
}

sealed class OnboardingFlow(val name: String) {
    object Root : OnboardingFlow("onboarding-root") {
        val route = name
    }

    object OnboardingScreen : OnboardingFlow("onboarding-screen") {
        val route = name
    }
}

sealed class AuthFlow(val name: String) {
    object Root : AuthFlow("auth-root") {
        val route = name
    }

    object LoginScreen : AuthFlow("login-screen") {
        val route = name
    }
}

sealed class TransactionDetailFlow(val name: String) {
    object Root : TransactionDetailFlow("transaction-detail-root") {
        val route = "$name?$ARG_TRANSACTION_ID={$ARG_TRANSACTION_ID}"

        fun route(transactionId: String = ""): String {
            return "$name?$ARG_TRANSACTION_ID=${transactionId}"
        }
    }

    object TransactionDetail : TransactionDetailFlow("transaction-detail-screen") {
        val arguments = listOf(
            navArgument(ARG_TRANSACTION_ID) {
                defaultValue = ""
            }
        )

        val route = "$name?$ARG_TRANSACTION_ID={$ARG_TRANSACTION_ID}"
    }

    object SelectAccount : TransactionDetailFlow("select-account-screen") {
        val route = name
    }

    object SelectTransferAccount : TransactionDetailFlow("select-transfer-account-screen") {
        val route = name
    }

    object SelectCategory : TransactionDetailFlow("select-category-screen") {
        val route = name
    }
}

sealed class AccountDetailFlow(val name: String) {
    object Root : AccountDetailFlow("account-detail-root") {
        val route = "$name?$ARG_ACCOUNT_ID={$ARG_ACCOUNT_ID}"

        fun route(accountId: String = ""): String {
            return "$name?$ARG_ACCOUNT_ID=${accountId}"
        }
    }

    object AccountDetail : AccountDetailFlow("account-detail-screen") {
        val arguments = listOf(
            navArgument(ARG_ACCOUNT_ID) {
                defaultValue = ""
            }
        )

        val route = "$name?$ARG_ACCOUNT_ID={$ARG_ACCOUNT_ID}"
    }

    object SelectCategory : AccountDetailFlow("account-category-screen") {
        val route = name
    }
}

sealed class SettingFlow(val name: String) {
    object Root : SettingFlow("setting-root") {
        val route = name
    }

    object Setting : SettingFlow("setting-screen") {
        val route = name
    }

    object Theme : SettingFlow("theme-screen") {
        val route = name
    }

    object Logout : SettingFlow("logout-screen") {
        val route = name
    }

    object Language : SettingFlow("language-screen") {
        val route = name
    }
}

const val ARG_TRANSACTION_ID = "transactionId"
const val ARG_ACCOUNT_ID = "accountId"
const val ARG_IS_DUAL_PORTRAIT = "isDualPortrait"

// Problem
// a lot of nav, how to prevent duplication aka idempotency
// track changes easyly when move one nav to another nav
// see all nav structure in one file
// see all deeplink easyly
// see all root easyly
// see all argument easyly
abstract class NavigationDestination(prefix: String) {
    internal abstract val routeName: String

    val route = prefix + routeName

}
