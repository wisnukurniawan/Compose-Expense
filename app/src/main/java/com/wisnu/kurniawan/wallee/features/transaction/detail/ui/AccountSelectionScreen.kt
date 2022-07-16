package com.wisnu.kurniawan.wallee.features.transaction.detail.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wisnu.kurniawan.wallee.R
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgIcon
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgIconButton
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgModalCell
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgModalLayout
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgModalTitle

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun AccountSelectionScreen(
    viewModel: TransactionDetailViewModel,
    onClick: () -> Unit,
    onAddAccountClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AccountSelectionScreen(
        accountItems = state.accountItems,
        onClick = {
            viewModel.dispatch(TransactionAction.SelectAccount(it.account))
            onClick()
        },
        onAddAccountClick = onAddAccountClick
    )
}

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun TransferAccountSelectionScreen(
    viewModel: TransactionDetailViewModel,
    onClick: () -> Unit,
    onAddAccountClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AccountSelectionScreen(
        accountItems = state.transferAccountItems,
        onClick = {
            viewModel.dispatch(TransactionAction.SelectTransferAccount(it.account))
            onClick()
        },
        onAddAccountClick = onAddAccountClick
    )
}

@Composable
private fun AccountSelectionScreen(
    accountItems: List<AccountItem>,
    onClick: (AccountItem) -> Unit,
    onAddAccountClick: () -> Unit,
) {
    PgModalLayout(
        title = {
            Box {
                PgModalTitle(
                    text = stringResource(R.string.transaction_edit_account),
                    modifier = Modifier.align(Alignment.Center)
                )

                Box(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .align(Alignment.CenterEnd)
                ) {
                    PgIconButton(
                        onClick = onAddAccountClick,
                        modifier = Modifier.size(28.dp)
                    ) {
                        PgIcon(
                            imageVector = Icons.Rounded.Add,
                        )
                    }
                }
            }
        },
        content = {
            items(accountItems) { item ->
                AccountItem(
                    onClick = {
                        onClick(item)
                    },
                    item = item,
                )
                Spacer(Modifier.height(8.dp))
            }
        }
    )
}

@Composable
private fun AccountItem(
    onClick: () -> Unit,
    item: AccountItem,
) {
    PgModalCell(
        onClick = onClick,
        text = item.account.name,
        color = if (item.selected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.surfaceVariant
        },
        textColor = if (item.selected) {
            MaterialTheme.colorScheme.onPrimary
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        },
        rightIcon = if (item.selected) {
            @Composable {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
            }
        } else {
            null
        }
    )
}
