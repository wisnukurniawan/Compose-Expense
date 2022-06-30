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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.wisnu.kurniawan.wallee.R
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgIcon
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgIconButton
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgModalCell
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgModalLayout
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgModalTitle
import com.wisnu.kurniawan.wallee.model.TransactionType
import com.wisnu.kurniawan.wallee.runtime.navigation.AccountDetailFlow

@Composable
fun AccountSelectionScreen(
    navController: NavController,
    viewModel: TransactionDetailViewModel,
) {
    val state by viewModel.state.collectAsState()

    AccountSelectionScreen(
        accountItems = state.accountItems,
        disabledAccount = if (state.selectedTransactionType() == TransactionType.TRANSFER) {
            state.transferAccountItems.selected()
        } else {
            null
        },
        onClick = {
            viewModel.dispatch(TransactionAction.SelectAccount(it.account))
            navController.navigateUp()
        },
        onAddAccountClick = {
            navController.navigate(AccountDetailFlow.Root.route())
        }
    )
}

@Composable
fun TransferAccountSelectionScreen(
    navController: NavController,
    viewModel: TransactionDetailViewModel,
) {
    val state by viewModel.state.collectAsState()

    AccountSelectionScreen(
        accountItems = state.transferAccountItems,
        disabledAccount = state.accountItems.selected(),
        onClick = {
            viewModel.dispatch(TransactionAction.SelectTransferAccount(it.account))
            navController.navigateUp()
        },
        onAddAccountClick = {
            navController.navigate(AccountDetailFlow.Root.route())
        }
    )
}

@Composable
private fun AccountSelectionScreen(
    accountItems: List<AccountItem>,
    disabledAccount: AccountItem?,
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
                    enabled = disabledAccount?.account != item.account
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
    enabled: Boolean
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
        enabled = enabled,
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
