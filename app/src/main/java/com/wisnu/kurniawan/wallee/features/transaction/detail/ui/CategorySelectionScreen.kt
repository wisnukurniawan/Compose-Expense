package com.wisnu.kurniawan.wallee.features.transaction.detail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.wisnu.kurniawan.wallee.R
import com.wisnu.kurniawan.wallee.foundation.extension.getEmojiAndText
import com.wisnu.kurniawan.wallee.foundation.theme.AlphaDisabled
import com.wisnu.kurniawan.wallee.foundation.theme.AlphaHigh
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgModalTitle
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgModalVerticalGridLayout

@Composable
fun CategorySelectionScreen(
    navController: NavController,
    viewModel: TransactionDetailViewModel
) {
    val state by viewModel.state.collectAsState()

    CategorySelectionScreen(
        categoryItems = state.categoryItems,
        onClick = {
            viewModel.dispatch(TransactionAction.SelectCategory(it.type))
            navController.navigateUp()
        }
    )
}

@Composable
private fun CategorySelectionScreen(
    categoryItems: List<CategoryItem>,
    onClick: (CategoryItem) -> Unit,
) {
    PgModalVerticalGridLayout(
        title = {
            PgModalTitle(
                text = stringResource(R.string.category)
            )
        },
        content = {
            items(categoryItems) { item ->
                CategoryItem(
                    onClick = { onClick(item) },
                    item = item
                )
            }
        },
        count = 4
    )
}

@Composable
private fun CategoryItem(
    onClick: () -> Unit,
    item: CategoryItem,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(
                vertical = 8.dp
            )
    ) {
        val (emoji, name) = item.type.getEmojiAndText()
        val textAlpha = if (item.selected) {
            AlphaHigh
        } else {
            AlphaDisabled
        }

        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    shape = CircleShape,
                    color = if (item.selected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    },
                ).align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = emoji,
                fontSize = 22.sp,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }

        Spacer(Modifier.height(8.dp))

        Text(
            style = MaterialTheme.typography.titleSmall.copy(
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = textAlpha),
                fontSize = 12.sp
            ),
            text = stringResource(name),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center
        )
    }
}
