package com.wisnu.kurniawan.wallee.features.transaction.topexpense.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wisnu.kurniawan.wallee.R
import com.wisnu.kurniawan.wallee.features.transaction.summary.ui.TopExpenseItem
import com.wisnu.kurniawan.wallee.features.transaction.summary.ui.getAmountDisplay
import com.wisnu.kurniawan.wallee.features.transaction.summary.ui.getTitle
import com.wisnu.kurniawan.wallee.foundation.extension.getColor
import com.wisnu.kurniawan.wallee.foundation.extension.getSymbol
import com.wisnu.kurniawan.wallee.foundation.theme.AlphaDisabled
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgAmountLabel
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgContentTitle
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgEmpty
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgHeadline1
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgPageLayout
import com.wisnu.kurniawan.wallee.foundation.uicomponent.RoundedLinearProgressIndicator
import com.wisnu.kurniawan.wallee.foundation.uiextension.paddingCell
import com.wisnu.kurniawan.wallee.model.Currency

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun TopExpenseScreen(
    viewModel: TopExpenseViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val effect by viewModel.effect.collectAsStateWithLifecycle()

    TopExpenseScreen(
        state = state,
    )
}

@Composable
private fun TopExpenseScreen(
    state: TopExpenseState,
) {
    PgPageLayout(
        modifier = Modifier.fillMaxSize()
    ) {

        Content(
            state = state,
        )
    }
}

@Composable
private fun Content(
    state: TopExpenseState,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        item {
            PgHeadline1(
                text = stringResource(R.string.transaction_top_expenses),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }

        item {
            SpacerSection()
        }

        TopExpenseCell(
            data = state.topExpenseItems,
            currency = state.currency,
        )

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

private inline fun LazyListScope.TopExpenseCell(
    data: List<TopExpenseItem>,
    currency: Currency,
) {
    if (data.isEmpty()) {
        item {
            PgEmpty(
                stringResource(R.string.all_transaction_empty),
                modifier = Modifier.height(500.dp)
            )
        }
    } else {
        itemsIndexed(
            items = data,
            key = { _, item -> item.categoryType }
        ) { _, item ->
            TopExpenseItemCell(
                title = item.getTitle(),
                amount = item.getAmountDisplay(currency),
                amountSymbol = currency.getSymbol(),
                progress = item.progress,
                progressColor = item.categoryType.getColor(),
            )
        }
    }
}

@Composable
private fun TopExpenseItemCell(
    title: String,
    amount: String,
    amountSymbol: String,
    progress: Float,
    progressColor: Color,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Column(
            Modifier.fillMaxWidth()
                .paddingCell()
        ) {
            PgContentTitle(
                text = title,
                modifier = Modifier.padding(bottom = 2.dp)
            )

            PgAmountLabel(
                amount = amount,
                symbol = amountSymbol,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp),
            )

            RoundedLinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth().height(24.dp),
                trackColor = MaterialTheme.colorScheme.onBackground.copy(alpha = AlphaDisabled),
                color = progressColor
            )
        }
    }
}


@Composable
private fun SpacerSection() {
    Spacer(modifier = Modifier.height(16.dp))
}
