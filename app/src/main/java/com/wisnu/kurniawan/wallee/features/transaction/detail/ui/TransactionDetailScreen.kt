package com.wisnu.kurniawan.wallee.features.transaction.detail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.Divider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.wisnu.kurniawan.wallee.R
import com.wisnu.kurniawan.wallee.foundation.extension.getSymbol
import com.wisnu.kurniawan.wallee.foundation.theme.AlphaDisabled
import com.wisnu.kurniawan.wallee.foundation.theme.DividerAlpha
import com.wisnu.kurniawan.wallee.foundation.theme.MediumRadius
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgAmountLabel
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgContentTitle
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgHeaderEditMode
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgHeadlineLabel
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgIcon
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgPageLayout
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgTabLabel
import com.wisnu.kurniawan.wallee.model.Currency
import com.wisnu.kurniawan.wallee.model.TransactionType

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
        },
        onTotalAmountChange = { viewModel.dispatch(TransactionAction.ChangeTotal(it)) },
        onNoteChange = { viewModel.dispatch(TransactionAction.ChangeNote(it)) },
    )
}

@Composable
private fun TransactionDetailScreen(
    state: TransactionState,
    onCancelClick: () -> Unit,
    onTransactionTypeSelected: (TransactionTypeItem) -> Unit,
    onTotalAmountChange: (TextFieldValue) -> Unit,
    onNoteChange: (TextFieldValue) -> Unit,
) {
    PgPageLayout(
        Modifier.fillMaxSize()
    ) {
        PgHeaderEditMode(
            isAllowToSave = true,
            title = stringResource(R.string.transaction_edit_add),
            onSaveClick = {},
            onCancelClick = onCancelClick,
        )

        TransactionTypeSection(
            transactionTypes = state.transactionTypes,
            onSelected = onTransactionTypeSelected
        )

        Spacer(Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .navigationBarsPadding()
                .imePadding()
        ) {
            item {
                AmountSection(
                    state.totalAmount,
                    state.currency,
                    onTotalAmountChange
                )
            }

            item {
                Spacer(Modifier.height(16.dp))
            }

            item {
                GeneralSection(state.selectedTransactionType())
            }

            item {
                Spacer(Modifier.height(16.dp))
            }

            item {
                NoteSection(state.note, onNoteChange)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun TransactionTypeSection(
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

                if (it.transactionType != TransactionType.TRANSFER) {
                    Spacer(Modifier.width(8.dp))
                }
            }
        }
    }
}

@Composable
private fun AmountSection(
    totalAmount: TextFieldValue,
    currency: Currency,
    onTotalAmountChange: (TextFieldValue) -> Unit
) {
    PgHeadlineLabel(
        text = stringResource(R.string.transaction_edit_total),
        modifier = Modifier.padding(start = 16.dp, bottom = 6.dp)
    )

    Row(
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.secondary,
            shape = MaterialTheme.shapes.medium
        )
            .fillMaxWidth()
            .padding(all = 16.dp)
    ) {
        PgAmountLabel(
            text = currency.getSymbol() + " ",
            color = MaterialTheme.colorScheme.onBackground
        )
        Box {
            BasicTextField(
                value = totalAmount,
                onValueChange = onTotalAmountChange,
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.headlineMedium,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                singleLine = true
            )
        }
    }
}

@Composable
private fun GeneralSection(transactionType: TransactionType) {
    PgHeadlineLabel(
        text = stringResource(R.string.transaction_edit_general),
        modifier = Modifier.padding(start = 16.dp, bottom = 6.dp)
    )

    ActionContentCell(
        title = if (transactionType == TransactionType.TRANSFER) {
            stringResource(R.string.transaction_edit_account_from)
        } else {
            stringResource(R.string.transaction_edit_account)
        },
        showDivider = true,
        shape = RoundedCornerShape(
            topStart = MediumRadius,
            topEnd = MediumRadius
        ),
        onClick = {}
    )

    if (transactionType == TransactionType.TRANSFER) {
        ActionContentCell(
            title = stringResource(R.string.transaction_edit_account_to),
            showDivider = true,
            shape = Shapes.None,
            onClick = {}
        )
    }

    if (transactionType == TransactionType.EXPENSE) {
        ActionContentCell(
            title = stringResource(R.string.transaction_edit_category),
            showDivider = true,
            shape = Shapes.None,
            onClick = {}
        )
    }

    ActionContentCell(
        title = stringResource(R.string.transaction_edit_date_transaction),
        showDivider = false,
        shape = RoundedCornerShape(
            bottomStart = MediumRadius,
            bottomEnd = MediumRadius
        ),
        onClick = {}
    )
}

@Composable
private fun NoteSection(
    note: TextFieldValue,
    onNoteChange: (TextFieldValue) -> Unit
) {
    PgHeadlineLabel(
        text = stringResource(R.string.transaction_edit_note),
        modifier = Modifier.padding(start = 16.dp, bottom = 6.dp)
    )
    Box(
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.secondary,
            shape = MaterialTheme.shapes.medium
        )
            .fillMaxWidth()
            .padding(all = 16.dp)
    ) {
        BasicTextField(
            value = note,
            onValueChange = onNoteChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.titleSmall,
            keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Sentences),
        )

        if (note.text.isBlank()) {
            PgContentTitle(
                text = stringResource(R.string.transaction_edit_note_hint),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = AlphaDisabled)
            )
        }
    }
}

@Composable
private fun ActionContentCell(
    title: String,
    showDivider: Boolean,
    shape: Shape,
    onClick: (() -> Unit)
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .clickable(onClick = onClick),
        color = MaterialTheme.colorScheme.secondary,
        shape = shape,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(all = 16.dp)
        ) {
            PgContentTitle(
                text = title
            )
            PgIcon(
                imageVector = Icons.Rounded.ChevronRight,
                tint = LocalContentColor.current.copy(alpha = AlphaDisabled)
            )
        }
    }

    if (showDivider) {
        Row {
            Spacer(
                Modifier
                    .width(16.dp)
                    .height(1.dp)
                    .background(color = MaterialTheme.colorScheme.secondary)
            )
            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = DividerAlpha))
        }
    }
}
