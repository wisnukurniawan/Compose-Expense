package com.wisnu.kurniawan.wallee.features.transaction.summary.ui

import com.wisnu.kurniawan.wallee.features.transaction.summary.data.ITransactionSummaryEnvironment
import com.wisnu.kurniawan.wallee.foundation.viewmodel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransactionSummaryViewModel @Inject constructor(
    transactionSummaryEnvironment: ITransactionSummaryEnvironment,
) : StatefulViewModel<TransactionSummaryState, TransactionSummaryEffect, TransactionSummaryAction, ITransactionSummaryEnvironment>(TransactionSummaryState.initial(), transactionSummaryEnvironment) {

    override fun dispatch(action: TransactionSummaryAction) {

    }

}
