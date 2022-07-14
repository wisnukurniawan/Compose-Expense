package com.wisnu.kurniawan.wallee.features.account.detail.ui

import androidx.lifecycle.viewModelScope
import com.wisnu.kurniawan.wallee.features.account.detail.data.IAccountDetailEnvironment
import com.wisnu.kurniawan.wallee.foundation.extension.formatAsBigDecimal
import com.wisnu.kurniawan.wallee.foundation.extension.formatAsDecimal
import com.wisnu.kurniawan.wallee.foundation.extension.isDecimalNotExceed
import com.wisnu.kurniawan.wallee.foundation.extension.toggleFormatDisplay
import com.wisnu.kurniawan.wallee.foundation.viewmodel.StatefulViewModel
import com.wisnu.kurniawan.wallee.model.AccountType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AccountDetailViewModel @Inject constructor(
    accountDetailEnvironment: IAccountDetailEnvironment
) : StatefulViewModel<AccountDetailState, AccountDetailEffect, AccountDetailAction, IAccountDetailEnvironment>(AccountDetailState(), accountDetailEnvironment) {

    init {
        viewModelScope.launch {
            setState { copy(accountTypeItems = initialAccountTypeItems()) }
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
                viewModelScope.launch {
                    try {
                        environment.saveAccount(
                            currency = state.value.currency,
                            amount = state.value.totalAmount.formatAsBigDecimal(),
                            name = state.value.name.text.trim(),
                            type = state.value.selectedAccountType()
                        )
                        setState { copy(shouldShowDuplicateNameError = false) }
                        setEffect(AccountDetailEffect.ClosePage)
                    } catch (e: Exception) {
                        setState { copy(shouldShowDuplicateNameError = true) }
                    }
                }
            }
            is AccountDetailAction.SelectAccountType -> {
                viewModelScope.launch {
                    setState { copy(accountTypeItems = accountTypeItems.select(action.selectedAccountType)) }
                }
            }
            is AccountDetailAction.TotalAmountAction.Change -> {
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
            is AccountDetailAction.TotalAmountAction.FocusChange -> {
                viewModelScope.launch {
                    val totalAmountText = state.value.totalAmount.text
                    val totalAmountFormatted = state.value.currency.toggleFormatDisplay(!action.isFocused, totalAmountText)
                    setState { copy(totalAmount = totalAmount.copy(text = totalAmountFormatted)) }
                }
            }
        }
    }

    private fun initialAccountTypeItems(): List<AccountTypeItem> {
        return listOf(
            AccountType.CASH,
            AccountType.BANK,
            AccountType.INVESTMENT,
            AccountType.E_WALLET,
            AccountType.OTHERS
        ).map {
            AccountTypeItem(it, selected = it == AccountType.CASH)
        }
    }

}
