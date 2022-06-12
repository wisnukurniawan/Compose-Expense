package com.wisnu.kurniawan.wallee.runtime.navigation

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
