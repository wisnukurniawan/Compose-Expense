package com.wisnu.kurniawan.wallee.features.transaction.summary.ui

import androidx.lifecycle.viewModelScope
import com.wisnu.kurniawan.wallee.features.transaction.summary.data.CashFlow
import com.wisnu.kurniawan.wallee.features.transaction.summary.data.ITransactionSummaryEnvironment
import com.wisnu.kurniawan.wallee.foundation.viewmodel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class TransactionSummaryViewModel @Inject constructor(
    transactionSummaryEnvironment: ITransactionSummaryEnvironment,
) : StatefulViewModel<TransactionSummaryState, TransactionSummaryEffect, TransactionSummaryAction, ITransactionSummaryEnvironment>(TransactionSummaryState.initial(), transactionSummaryEnvironment) {

    init {
        initLoadCashFlow()
        initLoadLastTransaction()
        initLoadTopExpense()
    }

    private fun initLoadCashFlow() {
        viewModelScope.launch {
            environment.getCashFlow()
                .collect { (account, expenseList, incomeList) ->
                    val totalExpense = expenseList.sumOf { it.amount }
                    val totalIncome = incomeList.sumOf { it.amount }
                    val totalAmount = totalIncome - totalExpense

                    setState {
                        copy(
                            cashFlow = CashFlow(
                                totalAmount = totalAmount,
                                totalIncome = totalIncome,
                                totalExpense = totalExpense,
                                currency = account.currency
                            )
                        )
                    }
                }
        }
    }

    private fun initLoadLastTransaction() {
        viewModelScope.launch {
            environment.getLastTransaction()
                .collect {
                    setState { copy(lastTransactionItems = it.toLastTransactionItems()) }
                }
        }
    }

    private fun initLoadTopExpense() {
        viewModelScope.launch {
            environment.getTopExpense()
                .collect {
                    setState { copy(topExpenseItems = it.toTopExpenseItems()) }
                }
        }
    }

    override fun dispatch(action: TransactionSummaryAction) {

    }

}
