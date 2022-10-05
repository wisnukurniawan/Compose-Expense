package com.wisnu.kurniawan.wallee.features.transaction.detail.ui

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.wisnu.foundation.coreviewmodel.StatefulViewModel
import com.wisnu.kurniawan.wallee.R
import com.wisnu.kurniawan.wallee.features.transaction.detail.data.ITransactionDetailEnvironment
import com.wisnu.kurniawan.wallee.foundation.extension.MAX_TOTAL_AMOUNT
import com.wisnu.kurniawan.wallee.foundation.extension.ZERO_AMOUNT
import com.wisnu.kurniawan.wallee.foundation.extension.formatAsBigDecimal
import com.wisnu.kurniawan.wallee.foundation.extension.formattedAmount
import com.wisnu.kurniawan.wallee.foundation.extension.getDefaultAccount
import com.wisnu.kurniawan.wallee.foundation.extension.select
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@HiltViewModel
class TransactionDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    transactionEnvironment: ITransactionDetailEnvironment,
) : StatefulViewModel<TransactionState, TransactionEffect, TransactionAction, ITransactionDetailEnvironment>(TransactionState(), transactionEnvironment) {

    private val transactionId = savedStateHandle.get<String>(ARG_TRANSACTION_ID).orEmpty()
    private val isDeleteInProgress: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val isSaveInProgress: MutableStateFlow<Boolean> = MutableStateFlow(false)

    init {
        initLoad()
        initSaveAction()
        initDeleteAction()
    }

    private fun initLoad() {
        viewModelScope.launch {
            environment.getTopCategory()
                .collect {
                    val default = mapOf(R.string.category_all to getCategoryTypes())
                    val categories = if (it.isNotEmpty()) {
                        mapOf(R.string.category_recent to it) + default
                    } else {
                        default
                    }
                    setState {
                        copy(categories = categories)
                    }
                }
        }
        viewModelScope.launch {
            if (transactionId.isEmpty()) {
                setEffect(TransactionEffect.ShowAmountKeyboard)
            }
        }
        viewModelScope.launch {
            if (transactionId.isEmpty()) {
                initLoad(
                    selectedTransactionType = TransactionType.EXPENSE,
                    selectedCategoryType = CategoryType.OTHERS,
                    selectedAccount = null,
                    selectedTransferAccount = null,
                    initialNote = "",
                    initialTotalAmount = ZERO_AMOUNT,
                    initialDate = environment.getCurrentDate(),
                    initialTransactionCreatedAt = environment.getCurrentDate(),
                    initialTransactionUpdatedAt = null
                )
            } else {
                environment.getTransaction(transactionId)
                    .onStart { setState { copy(isEditMode = true) } }
                    .collect {
                        initLoad(
                            selectedTransactionType = it.transaction.type,
                            selectedCategoryType = it.transaction.categoryType,
                            selectedAccount = it.account,
                            selectedTransferAccount = it.transferAccount,
                            initialNote = it.transaction.note,
                            initialCurrency = it.transaction.currency,
                            initialTotalAmount = it.transaction.amount.toString(),
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
        initialCurrency: Currency? = null,
        initialTotalAmount: String,
        initialDate: LocalDateTime,
        initialTransactionCreatedAt: LocalDateTime,
        initialTransactionUpdatedAt: LocalDateTime?,
    ) {
        environment.getAccounts()
            .onStart {
                setState {
                    copy(
                        transactionType = selectedTransactionType,
                        categoryType = selectedCategoryType,
                        note = TextFieldValue(initialNote, TextRange(initialNote.length)),
                        totalAmount = TextFieldValue(initialTotalAmount, TextRange(initialTotalAmount.length)),
                        transactionDate = initialDate,
                        transactionCreatedAt = initialTransactionCreatedAt,
                        transactionUpdatedAt = initialTransactionUpdatedAt
                    )
                }
            }
            .collect {
                setState {
                    val selectedAccount = selectedAccount ?: it.getDefaultAccount()
                    copy(
                        accounts = it,
                        selectedAccount = selectedAccount,
                        selectedTransferAccount = selectedTransferAccount ?: it.select(except = selectedAccount),
                        currency = initialCurrency ?: it.getDefaultAccount().currency
                    )
                }
            }
    }

    private fun initSaveAction() {
        viewModelScope.launch {
            isSaveInProgress
                .filter { it }
                .distinctUntilChanged { old, new -> old != new }
                .collect {
                    val transactionWithAccount = TransactionWithAccount(
                        transaction = Transaction(
                            id = transactionId,
                            amount = state.value.totalAmount.formatAsBigDecimal(),
                            date = state.value.transactionDate,
                            type = state.value.transactionType,
                            currency = state.value.currency,
                            note = state.value.note.text.trim(),
                            categoryType = getDefaultCategoryType(),
                            createdAt = state.value.transactionCreatedAt,
                            updatedAt = state.value.transactionUpdatedAt,
                        ),
                        account = state.value.selectedAccount,
                        transferAccount = state.value.selectedTransferAccount,
                    )

                    environment.saveTransaction(transactionWithAccount)
                        .collect {
                            setEffect(TransactionEffect.ClosePage)
                        }
                }
        }
    }

    private fun initDeleteAction() {
        viewModelScope.launch {
            isDeleteInProgress
                .filter { it }
                .distinctUntilChanged { old, new -> old != new }
                .collect {
                    environment.deleteTransaction(transactionId)
                        .collect {
                            setEffect(TransactionEffect.ClosePage)
                        }
                }
        }
    }

    override fun dispatch(action: TransactionAction) {
        when (action) {
            TransactionAction.Save -> {
                environment.trackSaveTransactionButtonClicked()
                isSaveInProgress.value = true
            }
            TransactionAction.Delete -> {
                isDeleteInProgress.value = true
            }
            is TransactionAction.SelectTransactionType -> {
                viewModelScope.launch {
                    setState { copy(transactionType = action.type) }
                }
            }
            is TransactionAction.TotalAmountAction.Change -> {
                viewModelScope.launch {
                    val amount = action.totalAmount.text.formattedAmount()
                    if (amount <= MAX_TOTAL_AMOUNT) {
                        setState {
                            copy(
                                totalAmount = action.totalAmount.copy(
                                    text = amount.toString(),
                                    selection = TextRange(amount.toString().length)
                                )
                            )
                        }
                    }
                }
            }
            is TransactionAction.ChangeNote -> {
                viewModelScope.launch {
                    setState { copy(note = action.note) }
                }
            }
            is TransactionAction.SelectAccount -> {
                viewModelScope.launch {
                    setState {
                        copy(
                            selectedAccount = action.selectedAccount,
                            selectedTransferAccount = state.value.accounts.select(except = action.selectedAccount)
                        )
                    }
                }
            }
            is TransactionAction.SelectTransferAccount -> {
                viewModelScope.launch {
                    setState {
                        copy(
                            selectedTransferAccount = action.selectedAccount,
                            selectedAccount = state.value.accounts.select(except = action.selectedAccount)!!
                        )
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
                    setState { copy(categoryType = action.selectedCategoryType) }
                }
            }
        }
    }

    private fun getDefaultCategoryType(): CategoryType {
        return when (state.value.transactionType) {
            TransactionType.INCOME -> CategoryType.INCOME
            TransactionType.EXPENSE -> state.value.categoryType
            TransactionType.TRANSFER -> CategoryType.UNCATEGORIZED
        }
    }
}
