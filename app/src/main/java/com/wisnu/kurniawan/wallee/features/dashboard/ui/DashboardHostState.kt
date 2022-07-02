package com.wisnu.kurniawan.wallee.features.dashboard.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector


@Immutable
data class DashboardHostState(
    val sections: List<DashboardSection> = listOf()
)

@Immutable
data class DashboardSection(
    val sectionType: SectionType,
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
)

enum class SectionType {
    TRANSACTION,
    BALANCE
}
