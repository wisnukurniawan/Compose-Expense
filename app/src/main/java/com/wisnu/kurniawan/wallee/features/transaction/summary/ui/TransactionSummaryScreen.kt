package com.wisnu.kurniawan.wallee.features.transaction.summary.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
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
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgHeadline1
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgHeadlineLabel
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgIcon
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgIconButton
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgPageLayout
import com.wisnu.kurniawan.wallee.foundation.uiextension.collectAsEffectWithLifecycle
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
            CashFlowSection(
                cashFlow = state.cashFlow
            )
        }

        item {
            LastTransactionSection(
                lastTransactionItems = state.lastTransactionItems
            )
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
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
    ) {
        PgHeadlineLabel(
            text = currentMonth
        )

        PgHeadline1(
            text = stringResource(R.string.summary)
        )
    }
}

@Composable
private fun CashFlowSection(
    cashFlow: CashFlow
) {

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
