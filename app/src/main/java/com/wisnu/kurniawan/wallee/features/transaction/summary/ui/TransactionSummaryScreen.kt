package com.wisnu.kurniawan.wallee.features.transaction.summary.ui

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wisnu.kurniawan.wallee.R
import com.wisnu.kurniawan.wallee.foundation.extension.cellShape
import com.wisnu.kurniawan.wallee.foundation.extension.getColor
import com.wisnu.kurniawan.wallee.foundation.extension.getSymbol
import com.wisnu.kurniawan.wallee.foundation.extension.shouldShowDivider
import com.wisnu.kurniawan.wallee.foundation.theme.AlphaDisabled
import com.wisnu.kurniawan.wallee.foundation.theme.DividerAlpha
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgAmountLabel
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgContentTitle
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgContentTitle2
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgDateLabel
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgHeadline1
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgHeadline2
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgHeadlineLabel
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgIcon
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgIconButton
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgPageLayout
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgTextButton
import com.wisnu.kurniawan.wallee.foundation.uicomponent.RoundedLinearProgressIndicator
import com.wisnu.kurniawan.wallee.foundation.uiextension.paddingCell
import com.wisnu.kurniawan.wallee.model.Currency

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun TransactionSummaryScreen(
    viewModel: TransactionSummaryViewModel,
    route: String?,
    arguments: Bundle?,
    onSettingClick: () -> Unit,
    onClickAddTransaction: () -> Unit,
    onLastTransactionItemClick: (String) -> Unit,
    onSeeMoreLastTransactionClick: () -> Unit,
    onSeeMoreTopExpenseClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(route) {
        viewModel.dispatch(TransactionSummaryAction.NavBackStackEntryChanged(route, arguments))
    }

    TransactionSummaryScreen(
        state = state,
        onSettingClick = onSettingClick,
        onClickAddTransaction = onClickAddTransaction,
        onLastTransactionItemClick = { onLastTransactionItemClick(it.transactionId) },
        onSeeMoreLastTransactionClick = onSeeMoreLastTransactionClick,
        onSeeMoreTopExpenseClick = onSeeMoreTopExpenseClick
    )
}

@Composable
private fun TransactionSummaryScreen(
    state: TransactionSummaryState,
    onSettingClick: () -> Unit,
    onClickAddTransaction: () -> Unit,
    onSeeMoreLastTransactionClick: () -> Unit,
    onLastTransactionItemClick: (TransactionItem) -> Unit,
    onSeeMoreTopExpenseClick: () -> Unit,
) {
    if (!state.isLoading) {
        PgPageLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            Header(
                onSettingClick = onSettingClick,
                onClickAddTransaction = onClickAddTransaction
            )
            Content(
                state = state,
                onSeeMoreLastTransactionClick = onSeeMoreLastTransactionClick,
                onLastTransactionItemClick = onLastTransactionItemClick,
                onSeeMoreTopExpenseClick = onSeeMoreTopExpenseClick
            )
        }
    }
}

@Composable
private fun Header(
    onSettingClick: () -> Unit,
    onClickAddTransaction: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PgIconButton(onClick = onSettingClick, color = Color.Transparent) {
            PgIcon(imageVector = Icons.Rounded.Menu)
        }

        PgIconButton(onClick = onClickAddTransaction, color = Color.Transparent) {
            PgIcon(imageVector = Icons.Rounded.Add)
        }
    }
}

@Composable
private fun Content(
    state: TransactionSummaryState,
    onSeeMoreLastTransactionClick: () -> Unit,
    onLastTransactionItemClick: (TransactionItem) -> Unit,
    onSeeMoreTopExpenseClick: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        item {
            MainTitleSection(
                currentMonth = state.currentMonthDisplay()
            )
        }

        item {
            SpacerSection()
        }

        item {
            CashFlowSection(
                cashFlow = state.cashFlow
            )
        }

        item {
            SpacerSection()
        }

        LastTransactionCell(
            data = state.transactionItems,
            onSeeMoreClick = onSeeMoreLastTransactionClick,
            onItemClick = onLastTransactionItemClick,
        )

        item {
            SpacerSection()
        }

        TopExpenseCell(
            data = state.topExpenseItems,
            currency = state.cashFlow.currency,
            onSeeMoreClick = onSeeMoreTopExpenseClick
        )

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun MainTitleSection(
    currentMonth: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        PgHeadlineLabel(
            text = currentMonth,
            modifier = Modifier
        )

        PgHeadline1(
            text = stringResource(R.string.transaction_summary),
            modifier = Modifier
        )
    }
}

@Composable
private fun CashFlowSection(
    cashFlow: CashFlow
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        PgHeadline2(
            text = stringResource(R.string.transaction_cash_flow)
        )

        SpacerHeadline2()

        Column(
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.secondary,
                shape = MaterialTheme.shapes.medium
            )
                .fillMaxWidth()
                .paddingCell()
        ) {
            PgContentTitle(
                text = stringResource(R.string.transaction_this_month),
                modifier = Modifier.padding(bottom = 2.dp)
            )
            PgAmountLabel(
                amount = cashFlow.getTotalAmountDisplay(),
                symbol = cashFlow.currency.getSymbol(),
                color = cashFlow.getTotalAmountColor(
                    MaterialTheme.colorScheme.onBackground
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                CashFlowContent(
                    title = stringResource(R.string.transaction_income),
                    amount = cashFlow.getTotalIncomeDisplay(),
                    currency = cashFlow.currency,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    amountColor = cashFlow.getTotalIncomeColor(
                        MaterialTheme.colorScheme.onBackground
                    )
                )

                Box(
                    Modifier
                        .height(42.dp)
                        .width(1.dp)
                        .background(color = MaterialTheme.colorScheme.onSurface.copy(alpha = DividerAlpha))
                )

                CashFlowContent(
                    title = stringResource(R.string.transaction_expense),
                    amount = cashFlow.getTotalExpenseDisplay(),
                    currency = cashFlow.currency,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    amountColor = cashFlow.getTotalExpenseColor(
                        MaterialTheme.colorScheme.onBackground
                    )
                )
            }
        }
    }
}

@Composable
private fun CashFlowContent(
    modifier: Modifier,
    title: String,
    amount: String,
    amountColor: Color,
    currency: Currency
) {
    Column(modifier = modifier) {
        PgContentTitle2(
            text = title,
            modifier = Modifier.padding(bottom = 2.dp)
        )
        PgAmountLabel(
            amount = amount,
            color = amountColor,
            symbol = currency.getSymbol()
        )
    }
}

private inline fun LazyListScope.LastTransactionCell(
    data: List<TransactionItem>,
    noinline onSeeMoreClick: () -> Unit,
    noinline onItemClick: (TransactionItem) -> Unit,
) {
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PgHeadline2(text = stringResource(R.string.transaction_last))
            PgTextButton(
                text = stringResource(R.string.show_more),
                modifier = Modifier.align(Alignment.Bottom),
                onClick = onSeeMoreClick
            )
        }

        SpacerHeadline2()
    }

    if (data.isEmpty()) {
        item {
            Empty(
                title = stringResource(R.string.transaction_last_no_data_title),
                message = stringResource(R.string.transaction_last_no_data_message)
            )
        }
    } else {
        val size = data.size
        itemsIndexed(
            items = data,
            key = { _, item -> item.transactionId }
        ) { index, item ->
            TransactionItemCell(
                title = item.getTitle(),
                account = item.getAccountDisplay(),
                dateTime = item.getDateTimeDisplay(),
                amount = item.getAmountDisplay(),
                amountSymbol = item.currency.getSymbol(),
                amountColor = item.getAmountColor(
                    MaterialTheme.colorScheme.onBackground
                ),
                note = item.note,
                shape = cellShape(index, size),
                shouldShowDivider = shouldShowDivider(index, size),
                isSelected = item.isSelected,
                onClick = { onItemClick(item) },
                textColor = item.getTextColor()
            )
        }
    }
}

@Composable
private fun TransactionItemCell(
    title: String,
    account: String,
    dateTime: String,
    amount: String,
    amountSymbol: String,
    note: String,
    textColor: Color,
    amountColor: Color,
    shape: Shape,
    shouldShowDivider: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(shape)
            .clickable(onClick = onClick),
        shape = shape,
        color = if (isSelected) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.secondary
        }
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, top = 8.dp, bottom = 2.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
            ) {
                PgContentTitle(
                    text = title,
                    modifier = Modifier.padding(end = 4.dp),
                    color = textColor
                )

                PgDateLabel(
                    text = dateTime,
                    color = textColor
                )
            }

            PgAmountLabel(
                amount = amount,
                symbol = amountSymbol,
                color = amountColor,
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, bottom = 2.dp, end = 16.dp),
            )

            PgContentTitle(
                text = account,
                color = textColor.copy(AlphaDisabled),
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, bottom = 2.dp, end = 16.dp),
            )

            PgContentTitle(
                text = note,
                color = textColor.copy(AlphaDisabled),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
            )

            if (shouldShowDivider) {
                PgDivider(
                    needSpacer = !isSelected,
                    color = if (isSelected) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = DividerAlpha)
                    }
                )
            }
        }
    }
}


private inline fun LazyListScope.TopExpenseCell(
    data: List<TopExpenseItem>,
    currency: Currency,
    noinline onSeeMoreClick: () -> Unit,
) {
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PgHeadline2(text = stringResource(R.string.transaction_top_expenses))
            PgTextButton(
                text = stringResource(R.string.show_more),
                modifier = Modifier.align(Alignment.Bottom),
                onClick = onSeeMoreClick
            )
        }
        SpacerHeadline2()
    }

    if (data.isEmpty()) {
        item {
            Empty(
                title = stringResource(R.string.transaction_last_no_data_title),
                message = stringResource(R.string.transaction_top_expenses_no_data_message)
            )
        }
    } else {
        val size = data.size
        itemsIndexed(
            items = data,
            key = { _, item -> item.categoryType }
        ) { index, item ->
            TopExpenseItemCell(
                title = item.getTitle(),
                amount = item.getAmountDisplay(currency),
                amountSymbol = currency.getSymbol(),
                progress = item.progress,
                progressColor = item.categoryType.getColor(),
                shape = cellShape(index, size),
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
    shape: Shape
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = shape,
        color = MaterialTheme.colorScheme.secondary,
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
private fun Empty(
    title: String,
    message: String
) {
    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
        Column(
            Modifier
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = MaterialTheme.shapes.medium
                )
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            PgContentTitle(
                text = title,
                modifier = Modifier.padding(bottom = 2.dp)
            )
            PgContentTitle(
                text = message,
                color = MaterialTheme.colorScheme.onBackground.copy(AlphaDisabled)
            )
        }
    }
}

@Composable
private fun SpacerSection() {
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun SpacerHeadline2() {
    Spacer(Modifier.height(10.dp))
}

@Composable
private fun PgDivider(
    needSpacer: Boolean,
    color: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = DividerAlpha),
) {
    Row {
        if (needSpacer) {
            Spacer(
                Modifier
                    .width(16.dp)
                    .height(1.dp)
                    .background(color = MaterialTheme.colorScheme.secondary)
            )
        }
        Divider(color = color)
    }
}
