package com.wisnu.kurniawan.wallee.features.transaction.detail.ui

import androidx.lifecycle.viewModelScope
import com.wisnu.kurniawan.wallee.R
import com.wisnu.kurniawan.wallee.features.transaction.detail.data.ITransactionDetailEnvironment
import com.wisnu.kurniawan.wallee.features.transaction.detail.data.TransactionDetail
import com.wisnu.kurniawan.wallee.foundation.extension.formatAsBigDecimal
import com.wisnu.kurniawan.wallee.foundation.extension.formatAsDecimal
import com.wisnu.kurniawan.wallee.foundation.extension.isDecimalNotExceed
import com.wisnu.kurniawan.wallee.foundation.extension.toggleFormatDisplay
import com.wisnu.kurniawan.wallee.foundation.viewmodel.StatefulViewModel
import com.wisnu.kurniawan.wallee.model.Account
import com.wisnu.kurniawan.wallee.model.CategoryType
import com.wisnu.kurniawan.wallee.model.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class TransactionDetailViewModel @Inject constructor(
    transactionEnvironment: ITransactionDetailEnvironment,
) : StatefulViewModel<TransactionState, TransactionEffect, TransactionAction, ITransactionDetailEnvironment>(TransactionState(), transactionEnvironment) {

    init {
        viewModelScope.launch {
            setState {
                copy(
                    transactionTypeItems = initialTransactionTypes(),
                    categoryItems = initialCategoryTypes()
                )
            }
            environment.getAccounts()
                .collect {
                    val selectedAccount = state.value.accountItems.selected()
                    val selectedTransferAccount = state.value.transferAccountItems.selected()
                    setState {
                        copy(
                            accountItems = it.mapIndexed { index, account ->
                                val defaultSelectedAccount = defaultSelectedAccount(selectedAccount, account, index, FIRST_INDEX)
                                AccountItem(
                                    account = account,
                                    selected = defaultSelectedAccount
                                )
                            },
                            transferAccountItems = it.mapIndexed { index, account ->
                                val defaultSelectedAccount = defaultSelectedAccount(selectedTransferAccount, account, index, SECOND_INDEX)
                                AccountItem(
                                    account = account,
                                    selected = defaultSelectedAccount
                                )
                            }
                        )
                    }
                }
        }
    }

    private fun defaultSelectedAccount(
        selectedAccount: AccountItem?,
        account: Account,
        itemIndex: Int,
        index: Int
    ) = if (selectedAccount != null) {
        account.id == selectedAccount.account.id
    } else {
        itemIndex == index
    }

    override fun dispatch(action: TransactionAction) {
        when (action) {
            TransactionAction.Save -> {
                viewModelScope.launch {
                    val transactionDetail = when (state.value.selectedTransactionType()) {
                        TransactionType.INCOME -> {
                            TransactionDetail.Income(
                                amount = state.value.totalAmount.formatAsBigDecimal(),
                                account = state.value.accountItems.selected()?.account!!,
                                date = state.value.transactionDate,
                                type = state.value.selectedTransactionType(),
                                currency = state.value.currency,
                                note = state.value.note.text.trim(),
                                categoryType = CategoryType.INCOME
                            )
                        }
                        TransactionType.EXPENSE -> {
                            TransactionDetail.Expense(
                                amount = state.value.totalAmount.formatAsBigDecimal(),
                                account = state.value.accountItems.selected()?.account!!,
                                date = state.value.transactionDate,
                                type = state.value.selectedTransactionType(),
                                note = state.value.note.text.trim(),
                                currency = state.value.currency,
                                categoryType = state.value.selectedCategoryType()
                            )
                        }
                        TransactionType.TRANSFER -> {
                            TransactionDetail.Transfer(
                                amount = state.value.totalAmount.formatAsBigDecimal(),
                                account = state.value.accountItems.selected()?.account!!,
                                date = state.value.transactionDate,
                                type = state.value.selectedTransactionType(),
                                note = state.value.note.text.trim(),
                                currency = state.value.currency,
                                transferAccount = state.value.transferAccountItems.selected()?.account!!,
                                categoryType = CategoryType.UNCATEGORIZED
                            )
                        }
                    }

                    environment.saveTransaction(transactionDetail)
                        .collect {
                            setEffect(TransactionEffect.ClosePage)
                        }
                }
            }
            is TransactionAction.SelectTransactionType -> {
                viewModelScope.launch {
                    setState { copy(transactionTypeItems = transactionTypeItems.select(action.selectedTransactionItem)) }

                    if (action.selectedTransactionItem.transactionType == TransactionType.TRANSFER) {
                        val selectedAccount = state.value.transferAccountItems.selected()
                        if (selectedAccount == null) {
                            state.value.accountItems.find { !it.selected }?.let {
                                setState { copy(transferAccountItems = transferAccountItems.select(it.account)) }
                            }
                        }
                    }
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
                    val totalAmountText = state.value.totalAmount.text
                    val totalAmountFormatted = state.value.currency.toggleFormatDisplay(!action.isFocused, totalAmountText)
                    setState { copy(totalAmount = totalAmount.copy(text = totalAmountFormatted)) }
                }
            }
            is TransactionAction.ChangeNote -> {
                viewModelScope.launch {
                    setState { copy(note = action.note) }
                }
            }
            is TransactionAction.SelectAccount -> {
                viewModelScope.launch {
                    setState { copy(accountItems = accountItems.select(action.selectedAccount)) }
                    updateSelection(action.selectedAccount, state.value.transferAccountItems) {
                        setState { copy(transferAccountItems = transferAccountItems.select(it)) }
                    }
                }
            }
            is TransactionAction.SelectTransferAccount -> {
                viewModelScope.launch {
                    setState { copy(transferAccountItems = transferAccountItems.select(action.selectedAccount)) }
                    updateSelection(action.selectedAccount, state.value.accountItems) {
                        setState { copy(accountItems = accountItems.select(it)) }
                    }
                }
            }
            is TransactionAction.SelectDate -> {
                viewModelScope.launch {
                    setState { copy(transactionDate = LocalDateTime.of(action.selectedDate, LocalTime.now())) }
                }
            }
            is TransactionAction.SelectCategory -> {
                viewModelScope.launch {
                    setState { copy(categoryItems = categoryItems.select(action.selectedCategoryType)) }
                }
            }
        }
    }

    private fun updateSelection(selectedAccount: Account, accountItems: List<AccountItem>, newSelectedAccount: (Account) -> Unit) {
        if (selectedAccount == accountItems.selected()?.account) {
            accountItems.notSelected()?.account?.let {
                newSelectedAccount(it)
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

    private fun initialCategoryTypes(): List<CategoryItem> {
        return listOf(
            CategoryType.MONTHLY_FEE,
            CategoryType.ADMIN_FEE,
            CategoryType.PETS,
            CategoryType.DONATION,
            CategoryType.EDUCATION,
            CategoryType.FINANCIAL,
            CategoryType.ENTERTAINMENT,
            CategoryType.CHILDREN_NEEDS,
            CategoryType.HOUSEHOLD_NEEDS,
            CategoryType.SPORT,
            CategoryType.OTHERS,
            CategoryType.FOOD,
            CategoryType.PARKING,
            CategoryType.FUEL,
            CategoryType.MOVIE,
            CategoryType.AUTOMOTIVE,
            CategoryType.TAX,
            CategoryType.INCOME,
            CategoryType.BUSINESS_EXPENSES,
            CategoryType.SELF_CARE,
            CategoryType.LOAN,
            CategoryType.SERVICE,
            CategoryType.SHOPPING,
            CategoryType.BILLS,
            CategoryType.TAXI,
            CategoryType.CASH_WITHDRAWAL,
            CategoryType.PHONE,
            CategoryType.TOP_UP,
            CategoryType.PUBLIC_TRANSPORTATION,
            CategoryType.TRAVEL,
            CategoryType.UNCATEGORIZED,
        ).map {
            CategoryItem(
                it,
                selected = it == CategoryType.OTHERS
            )
        }
    }

    companion object {
        private const val FIRST_INDEX = 0
        private const val SECOND_INDEX = 1
    }
}
