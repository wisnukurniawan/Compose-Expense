package com.wisnu.kurniawan.wallee.features.balance.summary.ui

import androidx.lifecycle.viewModelScope
import com.wisnu.kurniawan.wallee.features.balance.summary.data.IBalanceSummaryEnvironment
import com.wisnu.kurniawan.wallee.foundation.viewmodel.StatefulViewModel
import com.wisnu.kurniawan.wallee.foundation.wrapper.DateTimeProviderImpl
import com.wisnu.kurniawan.wallee.model.Account
import com.wisnu.kurniawan.wallee.model.AccountType
import com.wisnu.kurniawan.wallee.model.Currency
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class BalanceSummaryViewModel @Inject constructor(
    balanceSummaryEnvironment: IBalanceSummaryEnvironment,
) : StatefulViewModel<BalanceSummaryState, BalanceSummaryEffect, BalanceSummaryAction, IBalanceSummaryEnvironment>(BalanceSummaryState.initial(), balanceSummaryEnvironment) {

    init {
        viewModelScope.launch {
            setState {
                copy(
                    accountItems = listOf(
                        AccountItem(
                            totalTransaction = 1,
                            account = Account(
                                id = "1",
                                currency = Currency.INDONESIA,
                                amount = "12000".toBigDecimal(),
                                name = "Name",
                                type = AccountType.CASH,
                                createdAt = DateTimeProviderImpl().now(),
                                updatedAt = DateTimeProviderImpl().now(),
                                transactions = listOf()
                            )
                        ),
                        AccountItem(
                            totalTransaction = 0,
                            account = Account(
                                id = "2",
                                currency = Currency.INDONESIA,
                                amount = "12000".toBigDecimal(),
                                name = "Name",
                                type = AccountType.E_WALLET,
                                createdAt = DateTimeProviderImpl().now(),
                                updatedAt = null,
                                transactions = listOf()
                            )
                        ),
                        AccountItem(
                            totalTransaction = 20,
                            account = Account(
                                id = "3",
                                currency = Currency.INDONESIA,
                                amount = "12000".toBigDecimal(),
                                name = "Name",
                                type = AccountType.BANK,
                                createdAt = DateTimeProviderImpl().now(),
                                updatedAt = DateTimeProviderImpl().now(),
                                transactions = listOf()
                            )
                        )
                    )
                )
            }
        }
    }

    override fun dispatch(action: BalanceSummaryAction) {

    }

}
