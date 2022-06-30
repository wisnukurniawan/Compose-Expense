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

const val ARG_TRANSACTION_ID = "listId"
