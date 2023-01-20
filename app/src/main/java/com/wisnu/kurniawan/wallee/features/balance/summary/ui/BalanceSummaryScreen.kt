package com.wisnu.kurniawan.wallee.features.balance.summary.ui

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wisnu.kurniawan.wallee.R
import com.wisnu.kurniawan.wallee.foundation.extension.getSymbol
import com.wisnu.kurniawan.wallee.foundation.theme.AlphaDisabled
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgAmountLabel
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgContentTitle
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgDateLabel
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgHeadline1
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgHeadline2
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgPageLayout
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgTextButton
import com.wisnu.kurniawan.wallee.foundation.uiextension.paddingCell
import com.wisnu.kurniawan.wallee.model.Account

@Composable
fun BalanceSummaryScreen(
    viewModel: BalanceSummaryViewModel,
    route: String?,
    arguments: Bundle?,
    onClickAccount: (String) -> Unit,
    onClickAddAccount: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(route) {
        viewModel.dispatch(BalanceSummaryAction.NavBackStackEntryChanged(route, arguments))
    }

    BalanceSummaryScreen(
        state = state,
        onClickAccount = {
            onClickAccount(it.id)
        },
        onClickAddAccount = onClickAddAccount
    )
}

@Composable
private fun BalanceSummaryScreen(
    state: BalanceSummaryState,
    onClickAccount: (Account) -> Unit,
    onClickAddAccount: () -> Unit,
) {
    PgPageLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Adaptive(minSize = 150.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                MainTitleSection()
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                AllTimeSection(
                    totalBalance = state.getTotalBalanceDisplay(),
                    totalBalanceColor = state.getTotalBalanceColor(
                        MaterialTheme.colorScheme.onBackground
                    ),
                    symbol = state.currency.getSymbol()
                )
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                SpacerSection()
            }

            AccountCell(
                accountItems = state.accountItems,
                onClickAccount = onClickAccount,
                onClickAddAccount = onClickAddAccount
            )

            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(modifier = Modifier.height(72.dp))
            }
        }
    }
}

@Composable
private fun MainTitleSection() {
    PgHeadline1(
        text = stringResource(R.string.dashboard_balance),
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
private fun AllTimeSection(
    totalBalance: String,
    totalBalanceColor: Color,
    symbol: String
) {
    Column(
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.secondary,
            shape = MaterialTheme.shapes.medium
        )
            .fillMaxWidth()
            .paddingCell()
    ) {
        PgContentTitle(
            text = stringResource(R.string.balance_all_time),
            modifier = Modifier.padding(bottom = 2.dp)
        )
        PgAmountLabel(
            modifier = Modifier,
            amount = totalBalance,
            symbol = symbol,
            color = totalBalanceColor
        )
    }
}

private inline fun LazyGridScope.AccountCell(
    accountItems: List<AccountItem>,
    noinline onClickAccount: (Account) -> Unit,
    noinline onClickAddAccount: () -> Unit,
) {
    item(span = { GridItemSpan(maxLineSpan) }) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PgHeadline2(text = stringResource(R.string.transaction_edit_account))
            PgTextButton(
                text = stringResource(R.string.add),
                modifier = Modifier.align(Alignment.Bottom),
                onClick = onClickAddAccount
            )
        }
    }

    items(
        items = accountItems,
        key = { it.account.id }
    ) {
        AccountCellItem(
            accountName = it.account.name,
            totalAmount = it.account.getTotalBalanceDisplay(),
            amountSymbol = it.account.currency.getSymbol(),
            amountColor = it.account.getTotalBalanceColor(
                MaterialTheme.colorScheme.onBackground
            ),
            totalTransaction = stringResource(R.string.balance_total_transaction, it.totalTransaction),
            updatedAt = it.account.getDateTimeDisplay(),
            isSelected = it.isSelected,
            textColor = it.getTextColor(),
            onClick = { onClickAccount(it.account) }
        )
    }
}

@Composable
private fun AccountCellItem(
    accountName: String,
    totalAmount: String,
    amountSymbol: String,
    totalTransaction: String,
    updatedAt: String,
    amountColor: Color,
    textColor: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .size(width = 150.dp, height = 120.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
        color = if (isSelected) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.secondary
        }
    ) {
        Column(
            modifier = Modifier
                .paddingCell()
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                PgContentTitle(
                    text = accountName,
                    modifier = Modifier.padding(bottom = 2.dp),
                    color = textColor
                )
                PgAmountLabel(
                    modifier = Modifier,
                    amount = totalAmount,
                    symbol = amountSymbol,
                    color = amountColor
                )
            }

            Column {
                PgContentTitle(
                    text = totalTransaction,
                    color = textColor.copy(AlphaDisabled),
                    modifier = Modifier.padding(bottom = 2.dp)
                )
                PgDateLabel(
                    text = updatedAt,
                    color = textColor
                )
            }
        }
    }
}

@Composable
private fun SpacerSection() {
    Spacer(modifier = Modifier.height(8.dp))
}
