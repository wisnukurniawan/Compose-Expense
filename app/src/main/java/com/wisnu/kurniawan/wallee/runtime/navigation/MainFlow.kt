package com.wisnu.kurniawan.wallee.runtime.navigation

import androidx.navigation.navArgument

sealed class MainFlow(val name: String) {
    object Root : MainFlow("main-root") {
        val route = name
    }

    object RootEmpty : MainFlow("root-empty") {
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
