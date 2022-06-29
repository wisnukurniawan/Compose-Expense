package com.wisnu.kurniawan.wallee.features.transaction.detail.ui

import androidx.lifecycle.viewModelScope
import com.wisnu.kurniawan.wallee.R
import com.wisnu.kurniawan.wallee.features.transaction.detail.data.ITransactionEnvironment
import com.wisnu.kurniawan.wallee.foundation.extension.formatAsDecimal
import com.wisnu.kurniawan.wallee.foundation.extension.isDecimalNotExceed
import com.wisnu.kurniawan.wallee.foundation.extension.toggleFormatDisplay
import com.wisnu.kurniawan.wallee.foundation.viewmodel.StatefulViewModel
import com.wisnu.kurniawan.wallee.model.TransactionType
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
            is TransactionAction.ChangeTotal -> {
                viewModelScope.launch {
                    runCatching {
                        action.totalAmount.formatAsDecimal().apply {
                            if (this.isDecimalNotExceed()) {
                                setState { copy(totalAmount = this@apply) }
                            }
                        }
                    }
                }
            }
            is TransactionAction.FocusChangeTotal -> {
                viewModelScope.launch {
                    val totalAmount = state.value.totalAmount.text
                    val totalAmountFormatted = state.value.currency.toggleFormatDisplay(!action.isFocused, totalAmount)
                    setState { copy(totalAmount = state.value.totalAmount.copy(text = totalAmountFormatted)) }
                }
            }
            is TransactionAction.ChangeNote -> {
                viewModelScope.launch {
                    setState { copy(note = action.note) }
                }
            }
        }
    }

    private fun initialTransactionTypes(): List<TransactionTypeItem> {
        return listOf(
            TransactionTypeItem(R.string.transaction_expense, true, TransactionType.EXPENSE),
            TransactionTypeItem(R.string.transaction_income, false, TransactionType.INCOME),
            TransactionTypeItem(R.string.transaction_transfer, false, TransactionType.TRANSFER)
        )
    }
}
