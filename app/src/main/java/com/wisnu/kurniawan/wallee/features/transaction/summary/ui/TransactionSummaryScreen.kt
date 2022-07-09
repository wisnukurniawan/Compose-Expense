package com.wisnu.kurniawan.wallee.features.transaction.summary.ui

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
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
import com.wisnu.kurniawan.wallee.foundation.theme.DividerAlpha
import com.wisnu.kurniawan.wallee.foundation.theme.Expense
import com.wisnu.kurniawan.wallee.foundation.theme.Income
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgAmountLabel2
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgContentTitle
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgContentTitle2
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgHeadline1
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgHeadline2
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgHeadlineLabel
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgIcon
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgIconButton
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgPageLayout
import com.wisnu.kurniawan.wallee.foundation.uiextension.collectAsEffectWithLifecycle
import com.wisnu.kurniawan.wallee.model.Currency
import com.wisnu.kurniawan.wallee.runtime.navigation.SettingFlow
import com.wisnu.kurniawan.wallee.runtime.navigation.TransactionDetailFlow

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun TransactionSummaryScreen(
    mainNavController: NavController,
    viewModel: TransactionSummaryViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val effect by viewModel.effect.collectAsEffectWithLifecycle()

    TransactionSummaryScreen(
        state = state,
        onSettingClick = { mainNavController.navigate(SettingFlow.Root.route) },
        onClickAddTransaction = { mainNavController.navigate(TransactionDetailFlow.Root.route()) }
    )
}

@Composable
private fun TransactionSummaryScreen(
    state: TransactionSummaryState,
    onSettingClick: () -> Unit,
    onClickAddTransaction: () -> Unit,
) {
    PgPageLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        Header(onSettingClick, onClickAddTransaction)
        Body(state)
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
private fun Body(
    state: TransactionSummaryState,
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

        item {
            LastTransactionSection(
                lastTransactionItems = state.lastTransactionItems
            )
        }

        item {
            SpacerSection()
        }

        item {
            TopExpenseSection(
                topExpenseItems = state.topExpenseItems
            )
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
                .padding(16.dp)
        ) {
            PgContentTitle(
                text = stringResource(R.string.transaction_this_month),
                modifier = Modifier.padding(bottom = 2.dp)
            )
            PgAmountLabel2(
                amount = cashFlow.totalAmount,
                symbol = cashFlow.currency.getSymbol()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                CashFlowContent(
                    title = stringResource(R.string.transaction_income),
                    amount = cashFlow.totalIncome,
                    currency = cashFlow.currency,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    titleColor = Income
                )

                Box(
                    Modifier
                        .height(42.dp)
                        .width(1.dp)
                        .background(color = MaterialTheme.colorScheme.onSurface.copy(alpha = DividerAlpha))
                )

                CashFlowContent(
                    title = stringResource(R.string.transaction_expense),
                    amount = cashFlow.totalExpense,
                    currency = cashFlow.currency,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    titleColor = Expense
                )
            }
        }
    }
}

@Composable
private fun CashFlowContent(
    modifier: Modifier,
    title: String,
    titleColor: Color,
    amount: String,
    currency: Currency
) {
    Column(modifier = modifier) {
        PgContentTitle2(
            text = title,
            color = titleColor,
            modifier = Modifier.padding(bottom = 2.dp)
        )
        PgAmountLabel2(
            amount = amount,
            symbol = currency.getSymbol()
        )
    }
}

@Composable
private fun LastTransactionSection(
    lastTransactionItems: List<LastTransactionItem>
) {

}

@Composable
private fun TopExpenseSection(
    topExpenseItems: List<TopExpenseItem>
) {

}

@Composable
private fun SpacerSection() {
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun SpacerHeadline2() {
    Spacer(Modifier.height(10.dp))
}
