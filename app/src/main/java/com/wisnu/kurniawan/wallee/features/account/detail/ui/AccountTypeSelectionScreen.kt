package com.wisnu.kurniawan.wallee.features.account.detail.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wisnu.kurniawan.wallee.R
import com.wisnu.kurniawan.wallee.foundation.extension.getLabel
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgModalCell
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgModalLayout
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgModalTitle

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun AccountTypeSelectionScreen(
    viewModel: AccountDetailViewModel,
    onClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AccountTypeSelectionScreen(
        accountItems = state.accountTypeItems,
        onClick = {
            viewModel.dispatch(AccountDetailAction.SelectAccountType(it.type))
            onClick()
        }
    )
}

@Composable
private fun AccountTypeSelectionScreen(
    accountItems: List<AccountTypeItem>,
    onClick: (AccountTypeItem) -> Unit,
) {
    PgModalLayout(
        title = {
            PgModalTitle(
                text = stringResource(R.string.account_edit_select_category),
            )
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
    item: AccountTypeItem,
) {
    PgModalCell(
        onClick = onClick,
        text = stringResource(item.type.getLabel()),
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
