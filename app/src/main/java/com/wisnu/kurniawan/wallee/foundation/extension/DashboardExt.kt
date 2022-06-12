package com.wisnu.kurniawan.wallee.foundation.extension

import com.wisnu.kurniawan.wallee.features.dashboard.ui.DashboardSection
import com.wisnu.kurniawan.wallee.features.dashboard.ui.SectionType

fun List<DashboardSection>.select(section: SectionType): List<DashboardSection> {
    return map {
        it.copy(selected = it.sectionType == section)
    }
}
