package com.wisnu.kurniawan.wallee.features.transaction.detail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.wisnu.kurniawan.wallee.R
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgHeaderEditMode
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgHeadlineLabel
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgPageLayout
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgTabLabel

@Composable
fun TransactionDetailScreen(
    navController: NavController,
    viewModel: TransactionViewModel
) {
    val state by viewModel.state.collectAsState()

    TransactionDetailScreen(
        state = state,
        onCancelClick = {
            navController.navigateUp()
        },
        onTransactionTypeSelected = {
            viewModel.dispatch(TransactionAction.SelectTransactionType(it))
        }
    )
}

@Composable
fun TransactionDetailScreen(
    state: TransactionState,
    onCancelClick: () -> Unit,
    onTransactionTypeSelected: (TransactionTypeItem) -> Unit
) {
    PgPageLayout(
        Modifier
            .fillMaxSize()
    ) {
        PgHeaderEditMode(
            isAllowToSave = true,
            title = stringResource(R.string.transaction_edit_add),
            onSaveClick = {},
            onCancelClick = onCancelClick,
        )
        TransactionType(
            transactionTypes = state.transactionTypes,
            onSelected = onTransactionTypeSelected
        )

        Spacer(Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            item {
                PgHeadlineLabel(
                    text = stringResource(R.string.transaction_edit_total),
                    modifier = Modifier.padding(start = 16.dp, bottom = 6.dp)
                )
                Column(
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = MaterialTheme.shapes.medium
                    )
                        .height(200.dp)
                        .fillMaxWidth()
                ) {

                }
            }

            item {
                Spacer(Modifier.height(16.dp))
            }

            item {
                PgHeadlineLabel(
                    text = stringResource(R.string.transaction_edit_general),
                    modifier = Modifier.padding(start = 16.dp, bottom = 6.dp)
                )

                Column(
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = MaterialTheme.shapes.medium
                    )
                        .height(200.dp)
                        .fillMaxWidth()
                ) {

                }
            }

            item {
                Spacer(Modifier.height(16.dp))
            }

            item {
                PgHeadlineLabel(
                    text = stringResource(R.string.transaction_edit_note),
                    modifier = Modifier.padding(start = 16.dp, bottom = 6.dp)
                )
                Column(
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = MaterialTheme.shapes.medium
                    )
                        .height(200.dp)
                        .fillMaxWidth()
                ) {

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun TransactionType(
    transactionTypes: List<TransactionTypeItem>,
    onSelected: (TransactionTypeItem) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 8.dp),
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = MaterialTheme.shapes.extraLarge
                )
                .align(Alignment.Center)
        ) {
            items(transactionTypes) {
                val backgroundColor = if (it.selected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.secondary
                }
                val contentColor = if (it.selected) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSecondary
                }

                Chip(
                    onClick = { onSelected(it) },
                    colors = ChipDefaults.chipColors(
                        backgroundColor = backgroundColor,
                        contentColor = contentColor
                    )
                ) {
                    CompositionLocalProvider(LocalContentColor provides contentColor) {
                        PgTabLabel(stringResource(it.title))
                    }
                }

                if (it.title != R.string.transaction_transfer) {
                    Spacer(Modifier.width(8.dp))
                }
            }
        }
    }

}
