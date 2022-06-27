package com.wisnu.kurniawan.wallee.features.transaction.detail.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.wisnu.kurniawan.wallee.R
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgBackHeader
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgPageLayout
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgTab
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgTabRow
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgTitleBar

@Composable
fun TransactionDetailScreen(
    navController: NavController
) {
    val state = TransactionState(
        tabs = listOf(
            TabItem(R.string.transaction_outcome, false),
            TabItem(R.string.transaction_income, true),
            TabItem(R.string.transaction_transfer, false),
        )
    )

    TransactionDetailScreen(
        state = state,
        onClickBack = {
            navController.navigateUp()
        },
        onTabSelected = {

        }
    )
}

@Composable
fun TransactionDetailScreen(
    state: TransactionState,
    onClickBack: () -> Unit,
    onTabSelected: (TabItem) -> Unit
) {
    PgPageLayout(
        Modifier
            .fillMaxSize()
    ) {
        PgBackHeader(
            onClickBack = onClickBack,
            text = stringResource(R.string.transaction_edit_add)
        )

        TransactionTab(
            modifier = Modifier,
            tabItems = state.tabs,
            selectedTabIndex = state.selectedTabIndex,
            onTabSelected = onTabSelected
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {

        }
    }
}

@Composable
private fun TransactionTab(
    modifier: Modifier = Modifier,
    tabItems: List<TabItem>,
    selectedTabIndex: Int,
    onTabSelected: (TabItem) -> Unit
) {
    PgTabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier,
    ) {
        tabItems.forEach { item ->
            PgTab(
                text = {
                    PgTitleBar(
                        text = stringResource(item.title),
                    )
                },
                selected = item.selected,
                onClick = { onTabSelected(item) },
            )
        }
    }
}
