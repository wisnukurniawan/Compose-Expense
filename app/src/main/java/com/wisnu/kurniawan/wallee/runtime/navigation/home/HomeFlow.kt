package com.wisnu.kurniawan.wallee.runtime.navigation.home

sealed class HomeFlow(val name: String) {
    object Root : HomeFlow("home-root") {
        val route = name
    }

    object DashboardScreen : HomeFlow("dashboard-screen") {
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
