package com.wisnu.kurniawan.wallee.features.balance.summary.ui

import androidx.lifecycle.viewModelScope
import com.wisnu.kurniawan.wallee.features.balance.summary.data.IBalanceSummaryEnvironment
import com.wisnu.kurniawan.wallee.foundation.extension.getDefaultAccount
import com.wisnu.kurniawan.wallee.foundation.viewmodel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class BalanceSummaryViewModel @Inject constructor(
    balanceSummaryEnvironment: IBalanceSummaryEnvironment,
) : StatefulViewModel<BalanceSummaryState, BalanceSummaryEffect, BalanceSummaryAction, IBalanceSummaryEnvironment>(BalanceSummaryState.initial(), balanceSummaryEnvironment) {

    init {
        initLoad()
    }

    private fun initLoad() {
        viewModelScope.launch {
            environment.getAccounts()
                .collect { accounts ->
                    setState {
                        copy(
                            totalBalance = accounts.sumOf { it.amount },
                            accountItems = accounts.toAccountItem(),
                            currency = accounts.getDefaultAccount().currency
                        )
                    }
                }
        }
    }

    override fun dispatch(action: BalanceSummaryAction) {

    }

}
