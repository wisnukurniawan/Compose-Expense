package com.wisnu.kurniawan.wallee.features.transaction.detail.ui

import androidx.lifecycle.viewModelScope
import com.wisnu.kurniawan.wallee.R
import com.wisnu.kurniawan.wallee.features.transaction.detail.data.ITransactionEnvironment
import com.wisnu.kurniawan.wallee.foundation.extension.select
import com.wisnu.kurniawan.wallee.foundation.viewmodel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class TransactionViewModel @Inject constructor(
    transactionEnvironment: ITransactionEnvironment,
) : StatefulViewModel<TransactionState, Unit, TransactionAction, ITransactionEnvironment>(TransactionState(), transactionEnvironment) {

    init {
        viewModelScope.launch {
            setState { copy(transactionTypes = initialTransactionTypes()) }
        }
    }

    override fun dispatch(action: TransactionAction) {
        when (action) {
            is TransactionAction.SelectTransactionType -> {
                viewModelScope.launch {
                    setState { copy(transactionTypes = transactionTypes.select(action.selectedTransactionItem)) }
                }
            }
        }
    }

    private fun initialTransactionTypes(): List<TransactionTypeItem> {
        return listOf(
            TransactionTypeItem(R.string.transaction_outcome, true),
            TransactionTypeItem(R.string.transaction_income, false),
            TransactionTypeItem(R.string.transaction_transfer, false)
        )
    }

}
