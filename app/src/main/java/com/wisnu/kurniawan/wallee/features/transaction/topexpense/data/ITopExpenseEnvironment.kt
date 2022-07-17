package com.wisnu.kurniawan.wallee.features.transaction.topexpense.data

import com.wisnu.kurniawan.wallee.model.Currency
import com.wisnu.kurniawan.wallee.model.TopTransaction
import kotlinx.coroutines.flow.Flow

interface ITopExpenseEnvironment {
    fun getTopExpense(): Flow<List<TopTransaction>>
    fun getCurrency(): Flow<Currency>
}
