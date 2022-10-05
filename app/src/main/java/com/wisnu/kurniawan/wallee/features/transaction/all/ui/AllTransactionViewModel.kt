package com.wisnu.kurniawan.wallee.features.transaction.all.ui

import androidx.lifecycle.viewModelScope
import com.wisnu.foundation.coreviewmodel.StatefulViewModel
import com.wisnu.kurniawan.wallee.features.transaction.all.data.IAllTransactionEnvironment
import com.wisnu.kurniawan.wallee.features.transaction.summary.ui.toLastTransactionItems
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AllTransactionViewModel @Inject constructor(
    allTransactionEnvironment: IAllTransactionEnvironment,
) : StatefulViewModel<AllTransactionState, AllTransactionEffect, AllTransactionAction, IAllTransactionEnvironment>(AllTransactionState(), allTransactionEnvironment) {

    init {
        initLoad()
    }

    private fun initLoad() {
        viewModelScope.launch {
            environment.getTransactions()
                .collect {
                    setState { copy(transactionItems = it.toLastTransactionItems()) }
                }
        }
    }

    override fun dispatch(action: AllTransactionAction) {

    }

}
