package com.wisnu.kurniawan.wallee.features.dashboard.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ReceiptLong
import androidx.compose.material.icons.rounded.Wallet
import androidx.lifecycle.viewModelScope
import com.wisnu.foundation.coreviewmodel.StatefulViewModel
import com.wisnu.kurniawan.wallee.R
import com.wisnu.kurniawan.wallee.runtime.navigation.home.BalanceSummaryFlow
import com.wisnu.kurniawan.wallee.runtime.navigation.home.TransactionSummaryFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class DashboardHostViewModel @Inject constructor() : StatefulViewModel<DashboardHostState, Unit, Unit, Unit>(DashboardHostState(), Unit) {

    init {
        initTab()
    }

    override fun dispatch(action: Unit) {

    }

    private fun initTab() {
        viewModelScope.launch {
            setState { copy(sections = initial()) }
        }
    }

    private fun initial(): List<DashboardSection> {
        return listOf(
            DashboardSection(
                SectionType.TRANSACTION,
                R.string.dashboard_transaction,
                Icons.Rounded.ReceiptLong,
                TransactionSummaryFlow.Root.route
            ),
            DashboardSection(
                SectionType.BALANCE,
                R.string.dashboard_balance,
                Icons.Rounded.Wallet,
                BalanceSummaryFlow.Root.route
            ),
        )
    }

}
