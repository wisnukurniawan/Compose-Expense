package com.wisnu.kurniawan.wallee.features.transaction.detail.ui

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.wisnu.kurniawan.wallee.R
import com.wisnu.kurniawan.wallee.features.transaction.detail.data.ITransactionDetailEnvironment
import com.wisnu.kurniawan.wallee.foundation.extension.ZERO_AMOUNT
import com.wisnu.kurniawan.wallee.foundation.extension.formatAsBigDecimal
import com.wisnu.kurniawan.wallee.foundation.extension.formatAsDecimal
import com.wisnu.kurniawan.wallee.foundation.extension.formatAsDisplayNormalize
import com.wisnu.kurniawan.wallee.foundation.extension.isDecimalNotExceed
import com.wisnu.kurniawan.wallee.foundation.extension.toggleFormatDisplay
import com.wisnu.kurniawan.wallee.foundation.viewmodel.StatefulViewModel
import com.wisnu.kurniawan.wallee.model.Account
import com.wisnu.kurniawan.wallee.model.CategoryType
import com.wisnu.kurniawan.wallee.model.Currency
import com.wisnu.kurniawan.wallee.model.Transaction
import com.wisnu.kurniawan.wallee.model.TransactionType
import com.wisnu.kurniawan.wallee.model.TransactionWithAccount
import com.wisnu.kurniawan.wallee.runtime.navigation.ARG_TRANSACTION_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@HiltViewModel
class TransactionDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    transactionEnvironment: ITransactionDetailEnvironment,
) : StatefulViewModel<TransactionState, TransactionEffect, TransactionAction, ITransactionDetailEnvironment>(TransactionState(), transactionEnvironment) {

    private val transactionId = savedStateHandle.get<String>(ARG_TRANSACTION_ID).orEmpty()

    init {
        initLoad()
    }

    private fun initLoad() {
        viewModelScope.launch {
            if (transactionId.isEmpty()) {
                initLoad(
                    selectedTransactionType = TransactionType.EXPENSE,
                    selectedCategoryType = CategoryType.OTHERS,
                    selectedAccount = state.value.accountItems.selected()?.account,
                    selectedTransferAccount = state.value.transferAccountItems.selected()?.account,
                    initialNote = "",
                    initialCurrency = Currency.INDONESIA,
                    initialTotalAmount = ZERO_AMOUNT,
                    initialDate = environment.getCurrentDate(),
                    initialTransactionCreatedAt = environment.getCurrentDate(),
                    initialTransactionUpdatedAt = null
                )
            } else {
                environment.getTransaction(transactionId)
                    .onStart { setState { copy(isEditMode = true) } }
                    .collect {
                        val note = it.transaction.note
                        val totalAmount = it.transaction.currency.formatAsDisplayNormalize(it.transaction.amount, false)
                        initLoad(
                            selectedTransactionType = it.transaction.type,
                            selectedCategoryType = it.transaction.categoryType,
                            selectedAccount = it.account,
                            selectedTransferAccount = it.transferAccount,
                            initialNote = note,
                            initialCurrency = it.transaction.currency,
                            initialTotalAmount = totalAmount,
                            initialDate = it.transaction.date,
                            initialTransactionCreatedAt = it.transaction.createdAt,
                            initialTransactionUpdatedAt = it.transaction.updatedAt
                        )
                    }
            }
        }
    }

    private suspend fun initLoad(
        selectedTransactionType: TransactionType,
        selectedCategoryType: CategoryType,
        selectedAccount: Account?,
        selectedTransferAccount: Account?,
        initialNote: String,
        initialCurrency: Currency,
        initialTotalAmount: String,
        initialDate: LocalDateTime,
        initialTransactionCreatedAt: LocalDateTime,
        initialTransactionUpdatedAt: LocalDateTime?,
    ) {
        environment.getAccounts()
            .onStart {
                setState {
                    copy(
                        transactionTypeItems = initialTransactionTypes(selectedTransactionType),
                        categoryItems = initialCategoryTypes(selectedCategoryType),
                        note = TextFieldValue(initialNote, TextRange(initialNote.length)),
                        currency = initialCurrency,
                        totalAmount = TextFieldValue(initialTotalAmount, TextRange(initialTotalAmount.length)),
                        transactionDate = initialDate,
                        transactionCreatedAt = initialTransactionCreatedAt,
                        transactionUpdatedAt = initialTransactionUpdatedAt
                    )
                }
            }
            .collect {
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

    private fun defaultSelectedAccount(
        selectedAccount: Account?,
        account: Account,
        itemIndex: Int,
        index: Int
    ) = if (selectedAccount != null) {
        account.id == selectedAccount.id
    } else {
        itemIndex == index
    }

    override fun dispatch(action: TransactionAction) {
        when (action) {
            TransactionAction.Save -> {
                viewModelScope.launch {
                    val transactionWithAccount = TransactionWithAccount(
                        transaction = Transaction(
                            id = transactionId,
                            amount = state.value.totalAmount.formatAsBigDecimal(),
                            date = state.value.transactionDate,
                            type = state.value.selectedTransactionType(),
                            currency = state.value.currency,
                            note = state.value.note.text.trim(),
                            categoryType = getDefaultCategoryType(),
                            createdAt = state.value.transactionCreatedAt,
                            updatedAt = state.value.transactionUpdatedAt,
                        ),
                        account = state.value.accountItems.selected()?.account!!,
                        transferAccount = state.value.transferAccountItems.selected()?.account,
                    )

                    environment.saveTransaction(transactionWithAccount)
                        .collect {
                            setEffect(TransactionEffect.ClosePage)
                        }
                }
            }
            TransactionAction.Delete -> {
                viewModelScope.launch {
                    environment.deleteTransaction(transactionId)
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

    private fun getDefaultCategoryType(): CategoryType {
        return when (state.value.selectedTransactionType()) {
            TransactionType.INCOME -> CategoryType.INCOME
            TransactionType.EXPENSE -> state.value.selectedCategoryType()
            TransactionType.TRANSFER -> CategoryType.UNCATEGORIZED
        }
    }

    private fun initialTransactionTypes(selectedType: TransactionType = TransactionType.EXPENSE): List<TransactionTypeItem> {
        return listOf(
            TransactionTypeItem(R.string.transaction_expense, false, TransactionType.EXPENSE),
            TransactionTypeItem(R.string.transaction_income, false, TransactionType.INCOME),
            TransactionTypeItem(R.string.transaction_transfer, false, TransactionType.TRANSFER)
        ).map {
            it.copy(selected = it.transactionType == selectedType)
        }
    }

    private fun initialCategoryTypes(selectedCategoryType: CategoryType = CategoryType.OTHERS): List<CategoryItem> {
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
                selected = it == selectedCategoryType
            )
        }
    }

    companion object {
        private const val FIRST_INDEX = 0
        private const val SECOND_INDEX = 1
    }
}
