package com.wisnu.kurniawan.wallee.features.transaction.summary.data

import com.wisnu.foundation.coredatetime.generateThisMonthDateTimeRange
import com.wisnu.kurniawan.wallee.foundation.datasource.local.LocalManager
import com.wisnu.kurniawan.wallee.foundation.wrapper.DateTimeProvider
import com.wisnu.kurniawan.wallee.model.Account
import com.wisnu.kurniawan.wallee.model.TopTransaction
import com.wisnu.kurniawan.wallee.model.Transaction
import com.wisnu.kurniawan.wallee.model.TransactionType
import com.wisnu.kurniawan.wallee.model.TransactionWithAccount
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class TransactionSummaryEnvironment @Inject constructor(
    private val localManager: LocalManager,
    private val dateTimeProvider: DateTimeProvider
) : ITransactionSummaryEnvironment {

    override fun getCashFlow(): Flow<Triple<Account, List<Transaction>, List<Transaction>>> {
        val (startDate, endDate) = dateTimeProvider.now().generateThisMonthDateTimeRange()
        return combine(
            localManager.getDefaultAccount(),
            localManager.getTransactions(
                startDate = startDate,
                endDate = endDate,
                type = TransactionType.EXPENSE
            ),
            localManager.getTransactions(
                startDate = startDate,
                endDate = endDate,
                type = TransactionType.INCOME
            ),
        ) { account, expenseList, incomeList ->
            Triple(
                account,
                expenseList,
                incomeList
            )
        }
    }

    override fun getLastTransaction(): Flow<List<TransactionWithAccount>> {
        val (startDate, endDate) = dateTimeProvider.now().generateThisMonthDateTimeRange()
        return localManager.getTransactionWithAccounts(
            startDate = startDate,
            endDate = endDate,
            limit = DEFAULT_LIMIT
        )
    }

    override fun getTopExpense(): Flow<List<TopTransaction>> {
        val (startDate, endDate) = dateTimeProvider.now().generateThisMonthDateTimeRange()
        return localManager.getTopTransactions(
            startDate = startDate,
            endDate = endDate,
            type = TransactionType.EXPENSE,
            limit = DEFAULT_LIMIT
        )
    }

    companion object {
        private const val DEFAULT_LIMIT = 5
    }

}

