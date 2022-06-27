package com.wisnu.kurniawan.wallee.foundation.extension

import com.wisnu.kurniawan.wallee.features.transaction.detail.ui.TransactionTypeItem

fun List<TransactionTypeItem>.select(selectedTransactionTypeItem: TransactionTypeItem): List<TransactionTypeItem> {
    return map {
        it.copy(selected = it.title == selectedTransactionTypeItem.title)
    }
}
