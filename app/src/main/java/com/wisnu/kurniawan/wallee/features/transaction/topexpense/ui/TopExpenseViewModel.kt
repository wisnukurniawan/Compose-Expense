package com.wisnu.kurniawan.wallee.features.transaction.topexpense.ui

import androidx.lifecycle.viewModelScope
import com.wisnu.kurniawan.wallee.features.transaction.summary.ui.toTopExpenseItems
import com.wisnu.kurniawan.wallee.features.transaction.topexpense.data.ITopExpenseEnvironment
import com.wisnu.kurniawan.wallee.foundation.viewmodel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
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
            environment.getTopExpense()
                .collect {
                    setState { copy(topExpenseItems = it.toTopExpenseItems()) }
                }
        }
    }

    override fun dispatch(action: TopExpenseAction) {

    }

}
