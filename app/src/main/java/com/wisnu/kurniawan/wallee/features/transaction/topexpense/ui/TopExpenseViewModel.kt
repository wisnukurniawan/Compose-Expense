package com.wisnu.kurniawan.wallee.features.transaction.topexpense.ui

import androidx.lifecycle.viewModelScope
import com.wisnu.foundation.coreviewmodel.StatefulViewModel
import com.wisnu.kurniawan.wallee.features.transaction.summary.ui.toTopExpenseItems
import com.wisnu.kurniawan.wallee.features.transaction.topexpense.data.ITopExpenseEnvironment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@HiltViewModel
class TopExpenseViewModel @Inject constructor(
    topExpenseEnvironment: ITopExpenseEnvironment
) : StatefulViewModel<TopExpenseState, TopExpenseEffect, TopExpenseAction, ITopExpenseEnvironment>(TopExpenseState(), topExpenseEnvironment) {

    init {
        initLoad()
    }

    private fun initLoad() {
        viewModelScope.launch {
            combine(
                environment.getTopExpense(),
                environment.getCurrency()
            ) { topExpenses, currency ->
                setState {
                    copy(
                        topExpenseItems = topExpenses.toTopExpenseItems(),
                        currency = currency
                    )
                }
            }.collect()
        }
    }

    override fun dispatch(action: TopExpenseAction) {

    }

}
