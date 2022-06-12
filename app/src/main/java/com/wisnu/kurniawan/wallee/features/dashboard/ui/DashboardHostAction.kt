package com.wisnu.kurniawan.wallee.features.dashboard.ui

sealed interface DashboardHostAction {

    data class ClickTab(val sectionType: SectionType) : DashboardHostAction

}
