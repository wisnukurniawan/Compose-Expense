package com.wisnu.kurniawan.wallee.features.transaction.detail.data

import com.google.firebase.analytics.FirebaseAnalytics
import com.wisnu.foundation.libanalyticsmanager.AnalyticsManager

import com.wisnu.kurniawan.wallee.foundation.datasource.local.LocalManager
import com.wisnu.kurniawan.wallee.foundation.extension.isAmountChanged
import com.wisnu.kurniawan.wallee.foundation.extension.isChanged
import com.wisnu.kurniawan.wallee.foundation.wrapper.DateTimeProvider
import com.wisnu.kurniawan.wallee.foundation.wrapper.IdProvider
import com.wisnu.kurniawan.wallee.model.Account
import com.wisnu.kurniawan.wallee.model.AccountRecord
import com.wisnu.kurniawan.wallee.model.CategoryType
import com.wisnu.kurniawan.wallee.model.Transaction
import com.wisnu.kurniawan.wallee.model.TransactionRecord
import com.wisnu.kurniawan.wallee.model.TransactionType
import com.wisnu.kurniawan.wallee.model.TransactionWithAccount
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.inject.Inject
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.take

class TransactionDetailEnvironment @Inject constructor(
    private val localManager: LocalManager,
    private val dateTimeProvider: DateTimeProvider,
    private val idProvider: IdProvider,
    private val analyticManager: AnalyticsManager
) : ITransactionDetailEnvironment {

    override fun getAccounts(): Flow<List<Account>> {
        return localManager.getAccounts()
    }

    override fun getTransaction(id: String): Flow<TransactionWithAccount> {
        return localManager.getTransactionWithAccount(id)
    }

    override fun getCurrentDate(): LocalDateTime {
        return dateTimeProvider.now()
    }

    override fun getTopCategory(): Flow<List<CategoryType>> {
        return localManager.getTopCategory(4)
    }

    override fun trackSaveTransactionButtonClicked() {
        analyticManager.trackEvent(
            FirebaseAnalytics.Event.SELECT_CONTENT,
            mapOf(
                FirebaseAnalytics.Param.SCREEN_NAME to "transaction_detail",
                FirebaseAnalytics.Param.ITEM_NAME to "button_save_transaction",
            ),
        )
    }

    /**
     * This function handle two scenario:
     *  Create scenario
     *      Insert new transaction
     *      Adjust amount based on transaction amount
     *  Edit scenario
     *      Record transaction
     *      Update transaction
     *      Adjust amount based on transaction amount
     */
    override suspend fun saveTransaction(transactionWithAccount: TransactionWithAccount): Flow<Account> {
        val newTransaction = transactionWithAccount.transaction
        val account = transactionWithAccount.account
        val transferAccount = transactionWithAccount.transferAccount

        return if (newTransaction.id.isEmpty()) {
            // Create transaction scenario
            insertNewTransaction(
                account = account,
                transferAccount = transferAccount,
                newTransaction = newTransaction
            )
        } else {
            // Edit transaction scenario
            updateTransaction(
                account = account,
                transferAccount = transferAccount,
                newTransaction = newTransaction
            )
        }
    }

    private suspend fun insertNewTransaction(
        account: Account,
        transferAccount: Account?,
        newTransaction: Transaction
    ): Flow<Account> {
        // Adjust amount based on transaction amount
        return updateAccount(
            account = account,
            transferAccount = transferAccount,
            transactionType = newTransaction.type,
            newAmount = newTransaction.amount
        )
            .onStart {
                // Insert new transaction
                localManager.insertTransaction(
                    accountId = account.id,
                    transferAccountId = transferAccount?.id,
                    transaction = newTransaction.copy(
                        id = idProvider.generate(),
                        createdAt = dateTimeProvider.now(),
                        amount = newTransaction.amount,
                    )
                )
            }
    }

    @OptIn(FlowPreview::class)
    private fun updateTransaction(
        account: Account,
        transferAccount: Account?,
        newTransaction: Transaction
    ): Flow<Account> {
        return localManager.getTransaction(newTransaction.id).take(1)
            .flatMapConcat { transaction ->
                getTransactionAmountRecord(
                    getAccountRecord = { localManager.getAccountRecord(account.id).take(1) },
                    getTransactionRecord = { localManager.getTransactionRecord(it, newTransaction.id).take(1) },
                    currentAmount = transaction.amount
                )
                    .map {
                        Pair(
                            transaction,
                            it
                        )
                    }
            }
            .onEach { (transaction, _) ->
                // Record transaction
                recordTransaction(
                    transaction = transaction,
                    newTransaction = newTransaction
                )

                // Update transaction
                updateTransaction(
                    accountId = account.id,
                    transferAccountId = transferAccount?.id,
                    transaction = transaction,
                    newTransaction = newTransaction
                )
            }
            .flatMapConcat { (_, transactionAmountRecord) ->
                // Adjust amount based on transaction amount
                updateAccount(
                    account = account,
                    transferAccount = transferAccount,
                    transactionType = newTransaction.type,
                    newAmount = newTransaction.amount - transactionAmountRecord
                )
            }
    }

    private fun updateAccount(
        account: Account,
        transferAccount: Account?,
        transactionType: TransactionType,
        newAmount: BigDecimal,
    ): Flow<Account> {
        val (accountAmount, transferAccountAmount) = calculateNewAccountAmount(
            transactionType = transactionType,
            accountAmount = account.amount,
            transferAccountAmount = transferAccount?.amount,
            newAmount = newAmount,
        )

        return updateAccount(
            account,
            transferAccount,
            accountAmount,
            transferAccountAmount
        )
    }

    private suspend fun recordTransaction(transaction: Transaction, newTransaction: Transaction) {
        if (transaction.isAmountChanged(newTransaction)) {
            localManager.insertTransactionRecord(
                TransactionRecord(
                    id = idProvider.generate(),
                    transactionId = transaction.id,
                    amount = transaction.amount,
                    createdAt = dateTimeProvider.now()
                )
            )
        }
    }

    private suspend fun updateTransaction(
        accountId: String,
        transferAccountId: String?,
        transaction: Transaction,
        newTransaction: Transaction
    ) {
        if (transaction.isChanged(newTransaction)) {
            localManager.updateTransaction(
                accountId,
                transferAccountId,
                newTransaction.copy(
                    updatedAt = dateTimeProvider.now()
                )
            )
        }
    }

    @OptIn(FlowPreview::class)
    override suspend fun deleteTransaction(id: String): Flow<Boolean> {
        return localManager.getTransactionWithAccount(id)
            .take(1)
            .flatMapConcat { transactionWithAccount ->
                val transaction = transactionWithAccount.transaction
                val account = transactionWithAccount.account
                val transferAccount = transactionWithAccount.transferAccount
                val (accountAmount, transferAccountAmount) = calculateNewAccountAmountReverse(
                    transactionType = transaction.type,
                    accountAmount = account.amount,
                    transferAccountAmount = transferAccount?.amount,
                    newAmount = transaction.amount,
                )

                // Adjust account amount
                updateAccount(
                    account,
                    transferAccount,
                    accountAmount,
                    transferAccountAmount
                )
            }
            .onEach {
                // Delete transaction
                localManager.deleteTransaction(id)
            }
            .map { true }
    }

    private fun updateAccount(
        account: Account,
        transferAccount: Account?,
        accountAmount: BigDecimal,
        transferAccountAmount: BigDecimal?,
    ): Flow<Account> {
        return if (transferAccount != null && transferAccountAmount != null) {
            merge(
                updateAccount(
                    account = account,
                    newAmount = accountAmount
                ),
                updateAccount(
                    account = transferAccount,
                    newAmount = transferAccountAmount
                )
            )
        } else {
            updateAccount(
                account = account,
                newAmount = accountAmount
            )
        }
    }

    private fun updateAccount(account: Account, newAmount: BigDecimal): Flow<Account> {
        return localManager.getAccount(account.id).take(1)
            .onEach {
                localManager.updateAccount(
                    it.copy(amount = newAmount)
                )
            }
    }

    // Calculation
    private fun calculateNewAccountAmountReverse(
        transactionType: TransactionType,
        accountAmount: BigDecimal,
        transferAccountAmount: BigDecimal?,
        newAmount: BigDecimal,
    ): Pair<BigDecimal, BigDecimal?> {
        return when (transactionType) {
            TransactionType.EXPENSE -> {
                Pair(
                    transferAccountAmount(accountAmount, newAmount),
                    null
                )
            }
            TransactionType.INCOME -> {
                Pair(
                    reduceAccountAmount(accountAmount, newAmount),
                    null
                )
            }
            TransactionType.TRANSFER -> {
                if (transferAccountAmount != null) {
                    Pair(
                        transferAccountAmount(accountAmount, newAmount),
                        reduceAccountAmount(transferAccountAmount, newAmount)
                    )
                } else {
                    Pair(
                        transferAccountAmount(accountAmount, newAmount),
                        null
                    )
                }
            }
        }
    }

    private fun calculateNewAccountAmount(
        transactionType: TransactionType,
        accountAmount: BigDecimal,
        transferAccountAmount: BigDecimal?,
        newAmount: BigDecimal,
    ): Pair<BigDecimal, BigDecimal?> {
        return when (transactionType) {
            TransactionType.EXPENSE -> {
                Pair(
                    // Reducing the account amount
                    reduceAccountAmount(accountAmount, newAmount),
                    null
                )
            }
            TransactionType.INCOME -> {
                Pair(
                    // Increase the account amount
                    transferAccountAmount(accountAmount, newAmount),
                    null
                )
            }
            TransactionType.TRANSFER -> {
                if (transferAccountAmount != null) {
                    Pair(
                        // Reducing the account amount
                        reduceAccountAmount(accountAmount, newAmount),
                        // Increase the transfer account amount
                        transferAccountAmount(transferAccountAmount, newAmount)
                    )
                } else {
                    Pair(
                        // Reducing the account amount
                        reduceAccountAmount(accountAmount, newAmount),
                        null
                    )
                }
            }
        }
    }

    private fun reduceAccountAmount(currentAmount: BigDecimal, newAmount: BigDecimal): BigDecimal {
        return currentAmount - newAmount
    }

    private fun transferAccountAmount(currentAmount: BigDecimal, newAmount: BigDecimal): BigDecimal {
        return currentAmount + newAmount
    }

    @OptIn(FlowPreview::class)
    private fun getTransactionAmountRecord(
        getAccountRecord: () -> Flow<AccountRecord?>,
        getTransactionRecord: (LocalDateTime) -> Flow<TransactionRecord?>,
        currentAmount: BigDecimal
    ): Flow<BigDecimal> {
        return getAccountRecord()
            .flatMapConcat {
                if (it != null) {
                    getTransactionRecord(it.createdAt)
                } else {
                    flowOf(null)
                }
            }
            .map { it?.amount ?: currentAmount }
    }

}
