package com.wisnu.kurniawan.wallee.features.account.detail.ui

import androidx.lifecycle.viewModelScope
import com.wisnu.kurniawan.wallee.features.account.detail.data.IAccountDetailEnvironment
import com.wisnu.kurniawan.wallee.foundation.extension.formatAsDecimal
import com.wisnu.kurniawan.wallee.foundation.extension.isDecimalNotExceed
import com.wisnu.kurniawan.wallee.foundation.extension.toggleFormatDisplay
import com.wisnu.kurniawan.wallee.foundation.viewmodel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AccountDetailViewModel @Inject constructor(
    accountDetailEnvironment: IAccountDetailEnvironment
) : StatefulViewModel<AccountDetailState, AccountDetailEffect, AccountDetailAction, IAccountDetailEnvironment>(AccountDetailState(), accountDetailEnvironment) {

    override fun dispatch(action: AccountDetailAction) {
        when (action) {
            is AccountDetailAction.NameChange -> {
                viewModelScope.launch {
                    setState { copy(name = action.name) }
                }
            }
            AccountDetailAction.Save -> {
                // TODO handle duplicate name
            }
            is AccountDetailAction.SelectAccountType -> {
                viewModelScope.launch {
                    setState { copy(accountTypeItems = state.value.accountTypeItems.select(action.selectedAccountType)) }
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
                    val totalAmount = state.value.totalAmount.text
                    val totalAmountFormatted = state.value.currency.toggleFormatDisplay(!action.isFocused, totalAmount)
                    setState { copy(totalAmount = state.value.totalAmount.copy(text = totalAmountFormatted)) }
                }
            }
        }
    }

}
