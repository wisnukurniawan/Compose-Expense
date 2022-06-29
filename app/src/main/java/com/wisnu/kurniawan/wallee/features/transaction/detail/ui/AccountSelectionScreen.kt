package com.wisnu.kurniawan.wallee.features.transaction.detail.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.wisnu.kurniawan.wallee.R
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgModalCell
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgModalLayout
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgModalTitle

@Composable
fun AccountSelectionScreen(
    navController: NavController,
    viewModel: TransactionDetailViewModel,
) {
    val state by viewModel.state.collectAsState()

    PgModalLayout(
        title = {
            PgModalTitle(
                text = stringResource(R.string.transaction_edit_account)
            )
        },
        content = {
            items(state.accountItems) { item ->
                RepeatItem(
                    onClick = {
                        viewModel.dispatch(TransactionAction.SelectAccount(item.account))
                        navController.navigateUp()
                    },
                    item = item
                )
                Spacer(Modifier.height(8.dp))
            }
        }
    )
}

@Composable
private fun RepeatItem(
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
