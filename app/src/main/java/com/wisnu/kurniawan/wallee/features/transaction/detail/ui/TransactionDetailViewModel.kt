package com.wisnu.kurniawan.wallee.features.transaction.detail.ui

import androidx.lifecycle.viewModelScope
import com.wisnu.kurniawan.wallee.R
import com.wisnu.kurniawan.wallee.features.transaction.detail.data.ITransactionDetailEnvironment
import com.wisnu.kurniawan.wallee.foundation.extension.formatAsDecimal
import com.wisnu.kurniawan.wallee.foundation.extension.isDecimalNotExceed
import com.wisnu.kurniawan.wallee.foundation.extension.toggleFormatDisplay
import com.wisnu.kurniawan.wallee.foundation.viewmodel.StatefulViewModel
import com.wisnu.kurniawan.wallee.model.Account
import com.wisnu.kurniawan.wallee.model.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class TransactionDetailViewModel @Inject constructor(
    transactionEnvironment: ITransactionDetailEnvironment,
) : StatefulViewModel<TransactionState, Unit, TransactionAction, ITransactionDetailEnvironment>(TransactionState(), transactionEnvironment) {

    init {
        viewModelScope.launch {
            setState { copy(transactionTypeItems = initialTransactionTypes()) }
            environment.getAccounts()
                .collect {
                    val selectedAccount = state.value.accountItems.selected()
                    setState {
                        copy(
                            accountItems = it.mapIndexed { index, account ->
                                val defaultSelectedAccount = defaultSelectedAccount(selectedAccount, account, index)
                                AccountItem(account = account, selected = defaultSelectedAccount)
                            }
                        )
                    }
                }
        }
    }

    private fun defaultSelectedAccount(
        selectedAccount: AccountItem?,
        account: Account,
        index: Int
    ) = if (selectedAccount != null) {
        account.id == selectedAccount.account.id
    } else {
        index == FIRST_INDEX
    }

    override fun dispatch(action: TransactionAction) {
        when (action) {
            is TransactionAction.SelectTransactionType -> {
                viewModelScope.launch {
                    setState { copy(transactionTypeItems = transactionTypeItems.select(action.selectedTransactionItem)) }
                }
            }
            is TransactionAction.TotalAmountAction.Change -> {
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
            is TransactionAction.TotalAmountAction.FocusChange -> {
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
            is TransactionAction.SelectAccount -> {
                viewModelScope.launch {
                    setState { copy(accountItems = state.value.accountItems.select(action.selectedAccount)) }
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

    companion object {
        private const val FIRST_INDEX = 0
        private const val SECOND_INDEX = 1
    }
}
