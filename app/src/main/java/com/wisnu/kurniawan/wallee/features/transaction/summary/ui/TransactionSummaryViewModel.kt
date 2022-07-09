package com.wisnu.kurniawan.wallee.features.transaction.summary.ui

import androidx.lifecycle.viewModelScope
import com.wisnu.kurniawan.wallee.features.transaction.summary.data.ITransactionSummaryEnvironment
import com.wisnu.kurniawan.wallee.foundation.viewmodel.StatefulViewModel
import com.wisnu.kurniawan.wallee.foundation.wrapper.DateTimeProviderImpl
import com.wisnu.kurniawan.wallee.model.CategoryType
import com.wisnu.kurniawan.wallee.model.Currency
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class TransactionSummaryViewModel @Inject constructor(
    transactionSummaryEnvironment: ITransactionSummaryEnvironment,
) : StatefulViewModel<TransactionSummaryState, TransactionSummaryEffect, TransactionSummaryAction, ITransactionSummaryEnvironment>(TransactionSummaryState.initial(), transactionSummaryEnvironment) {

    init {
        viewModelScope.launch {
            val currency = Currency.INDONESIA
            val totalExpense = 30000000L.toBigDecimal()
            val totalIncome = 31000000L.toBigDecimal()
            val totalAmount = totalIncome - totalExpense
            setState {
                copy(
                    cashFlow = CashFlow(
                        totalAmount = totalAmount,
                        totalExpense = -totalExpense,
                        totalIncome = totalIncome,
                        currency = currency
                    ),
                    topExpenseItems = listOf(
                        TopExpenseItem(
                            amount = "4000099999999".toBigDecimal(),
                            categoryType = CategoryType.OTHERS,
                            currency = currency,
                            progress = 0.5f
                        ),
                        TopExpenseItem(
                            amount = "4000099999999".toBigDecimal(),
                            categoryType = CategoryType.UNCATEGORIZED,
                            currency = currency,
                            progress = 0.3f
                        ),
                        TopExpenseItem(
                            amount = "4000099999999".toBigDecimal(),
                            categoryType = CategoryType.INCOME,
                            currency = currency,
                            progress = 0.2f
                        ),
                        TopExpenseItem(
                            amount = "4000099999999".toBigDecimal(),
                            categoryType = CategoryType.ADMIN_FEE,
                            currency = currency,
                            progress = 0.1f
                        )
                    ),
                    lastTransactionItems = listOf(
                        LastTransactionItem(
                            transactionId = "1",
                            amount = "4000099999999".toBigDecimal(),
                            categoryType = CategoryType.OTHERS,
                            date = DateTimeProviderImpl().now(),
                            accountName = "Cash",
                            currency = currency,
                            note = "lsfja;lsjdflakjsdflkajs;ldfja slkfdja;lksjfl;asjfl;kasjd f;laksjdf;lkajsdklfj as;ldfj a;klsdj f;klasj dfkl;ja slk;f jasf"
                        ),
                        LastTransactionItem(
                            transactionId = "2",
                            amount = "4000099999999".toBigDecimal(),
                            categoryType = CategoryType.OTHERS,
                            date = DateTimeProviderImpl().now(),
                            accountName = "Cash",
                            currency = currency,
                            note = "-"
                        ),
                        LastTransactionItem(
                            transactionId = "3",
                            amount = "4000099999999".toBigDecimal(),
                            categoryType = CategoryType.OTHERS,
                            date = DateTimeProviderImpl().now(),
                            accountName = "Cash",
                            currency = currency,
                            note = "-"
                        )
                    )
                )
            }
        }
    }

    override fun dispatch(action: TransactionSummaryAction) {

    }

}
