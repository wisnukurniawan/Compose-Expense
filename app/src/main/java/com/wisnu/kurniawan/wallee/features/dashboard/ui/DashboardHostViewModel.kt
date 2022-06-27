package com.wisnu.kurniawan.wallee.features.dashboard.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ReceiptLong
import androidx.compose.material.icons.rounded.Wallet
import androidx.lifecycle.viewModelScope
import com.wisnu.kurniawan.wallee.R
import com.wisnu.kurniawan.wallee.foundation.extension.select
import com.wisnu.kurniawan.wallee.foundation.viewmodel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class DashboardHostViewModel @Inject constructor() : StatefulViewModel<DashboardHostState, Unit, DashboardHostAction, Unit>(DashboardHostState(), Unit) {

    init {
        initTab()
    }

    override fun dispatch(action: DashboardHostAction) {
        when (action) {
            is DashboardHostAction.ClickTab -> {
                updateSelectedTab(action.sectionType)
            }
        }
    }

    private fun updateSelectedTab(sectionType: SectionType) {
        viewModelScope.launch {
            setState { copy(sections = sections.select(sectionType)) }
        }
    }

    private fun initTab() {
        viewModelScope.launch {
            setState { copy(sections = initial()) }
        }
    }

    private fun initial(): List<DashboardSection> {
        return listOf(
            DashboardSection(SectionType.TRANSACTION, R.string.dashboard_transaction, Icons.Rounded.ReceiptLong, true),
            DashboardSection(SectionType.BALANCE, R.string.dashboard_balance, Icons.Rounded.Wallet, false),
        )
    }

}
