package com.wisnu.kurniawan.wallee.features.balance.summary.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.wisnu.kurniawan.wallee.R
import com.wisnu.kurniawan.wallee.foundation.extension.getSymbol
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgAmountLabel1
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgContentTitle
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgHeadline1
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgHeadline2
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgPageLayout
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgTextButton
import com.wisnu.kurniawan.wallee.foundation.uiextension.collectAsEffectWithLifecycle

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun BalanceSummaryScreen(
    mainNavController: NavController,
    viewModel: BalanceSummaryViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val effect by viewModel.effect.collectAsEffectWithLifecycle()

    BalanceSummaryScreen(
        state = state
    )
}

@Composable
private fun BalanceSummaryScreen(state: BalanceSummaryState) {
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

            AccountCell()

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
            .padding(16.dp)
    ) {
        PgContentTitle(
            text = stringResource(R.string.balance_all_time),
            modifier = Modifier.padding(bottom = 2.dp)
        )
        PgAmountLabel1(
            modifier = Modifier,
            amount = totalBalance,
            symbol = symbol,
            color = totalBalanceColor
        )
    }
}

private fun LazyGridScope.AccountCell() {
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
                onClick = {

                }
            )
        }
    }

    item {
        Box(
            Modifier
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = MaterialTheme.shapes.medium
                )
                .size(width = 150.dp, height = 100.dp)
                .padding(16.dp)
        )
    }

    item {
        Box(
            Modifier
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = MaterialTheme.shapes.medium
                )
                .size(width = 150.dp, height = 100.dp)
                .padding(16.dp)
        )
    }
}

@Composable
private fun SpacerSection() {
    Spacer(modifier = Modifier.height(8.dp))
}
