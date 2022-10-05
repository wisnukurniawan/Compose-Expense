package com.wisnu.kurniawan.wallee.features.account.detail.ui

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.wisnu.foundation.coreviewmodel.StatefulViewModel
import com.wisnu.kurniawan.wallee.features.account.detail.data.IAccountDetailEnvironment
import com.wisnu.kurniawan.wallee.features.balance.summary.data.AccountBalance
import com.wisnu.kurniawan.wallee.foundation.extension.MAX_TOTAL_AMOUNT
import com.wisnu.kurniawan.wallee.foundation.extension.formatAsBigDecimal
import com.wisnu.kurniawan.wallee.foundation.extension.formattedAmount
import com.wisnu.kurniawan.wallee.model.AccountType
import com.wisnu.kurniawan.wallee.runtime.navigation.ARG_ACCOUNT_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@HiltViewModel
class AccountDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    accountDetailEnvironment: IAccountDetailEnvironment
) : StatefulViewModel<AccountDetailState, AccountDetailEffect, AccountDetailAction, IAccountDetailEnvironment>(AccountDetailState(), accountDetailEnvironment) {

    private val accountId = savedStateHandle.get<String>(ARG_ACCOUNT_ID).orEmpty()
    private val isDeleteInProgress: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val isSaveInProgress: MutableStateFlow<Boolean> = MutableStateFlow(false)

    init {
        initLoad()
        initSaveAction()
        initDeleteAction()
    }

    private fun initLoad() {
        viewModelScope.launch {
            if (accountId.isNotBlank()) {
                environment.getAccount(accountId)
                    .onStart { setState { copy(isEditMode = true) } }
                    .collect {
                        setState {
                            val name = it.name
                            val totalAmount = it.amount.toString()
                            copy(
                                accountTypeItems = initialAccountTypeItems(it.type),
                                name = TextFieldValue(name, TextRange(name.length)),
                                totalAmount = TextFieldValue(totalAmount, TextRange(totalAmount.length)),
                                currency = it.currency,
                                createdAt = it.createdAt,
                                id = it.id
                            )
                        }
                    }
            } else {
                environment.getAccount(accountId)
                    .collect {
                        setState {
                            copy(
                                accountTypeItems = initialAccountTypeItems(),
                                currency = it.currency
                            )
                        }
                    }
            }
        }
    }

    private fun initSaveAction() {
        viewModelScope.launch {
            isSaveInProgress
                .filter { it }
                .distinctUntilChanged { old, new -> old != new }
                .collect {
                    try {
                        val account = AccountBalance(
                            id = accountId,
                            currency = state.value.currency,
                            amount = state.value.totalAmount.formatAsBigDecimal(),
                            name = state.value.name.text.trim(),
                            type = state.value.selectedAccountType(),
                            createdAt = state.value.createdAt
                        )
                        environment.saveAccount(account, state.value.adjustBalanceReasonItems.selected())
                            .collect {
                                setState { copy(shouldShowDuplicateNameError = false) }
                                setEffect(AccountDetailEffect.ClosePage)
                            }
                    } catch (e: Exception) {
                        isSaveInProgress.value = false
                        setState { copy(shouldShowDuplicateNameError = true) }
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
                    environment.deleteAccount(accountId)
                    setEffect(AccountDetailEffect.ClosePage)
                }
        }
    }

    override fun dispatch(action: AccountDetailAction) {
        when (action) {
            is AccountDetailAction.NameChange -> {
                viewModelScope.launch {
                    setState { copy(name = action.name) }
                }
            }
            AccountDetailAction.Save -> {
                isSaveInProgress.value = true
            }
            AccountDetailAction.Delete -> {
                isDeleteInProgress.value = true
            }
            is AccountDetailAction.SelectAccountType -> {
                viewModelScope.launch {
                    setState { copy(accountTypeItems = accountTypeItems.select(action.selectedAccountType)) }
                }
            }
            is AccountDetailAction.TotalAmountAction.Change -> {
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
            is AccountDetailAction.ClickBalanceReason -> {
                viewModelScope.launch {
                    setState { copy(adjustBalanceReasonItems = adjustBalanceReasonItems.select(action.reason)) }
                }
            }
        }
    }

    private fun initialAccountTypeItems(selectedAccountType: AccountType = AccountType.CASH): List<AccountTypeItem> {
        return listOf(
            AccountType.CASH,
            AccountType.BANK,
            AccountType.INVESTMENT,
            AccountType.E_WALLET,
            AccountType.OTHERS
        ).map {
            AccountTypeItem(it, selected = it == selectedAccountType)
        }
    }

}
