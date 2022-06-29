package com.wisnu.kurniawan.wallee.features.transaction.detail.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.wisnu.kurniawan.wallee.R
import com.wisnu.kurniawan.wallee.foundation.extension.getLabel
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
import com.wisnu.kurniawan.wallee.foundation.uiextension.showDatePicker
import com.wisnu.kurniawan.wallee.model.AccountType
import com.wisnu.kurniawan.wallee.model.Currency
import com.wisnu.kurniawan.wallee.model.TransactionType
import com.wisnu.kurniawan.wallee.runtime.navigation.TransactionDetailFlow

@Composable
fun TransactionDetailScreen(
    navController: NavController,
    viewModel: TransactionDetailViewModel
) {
    val state by viewModel.state.collectAsState()
    val localFocusManager = LocalFocusManager.current
    val activity = LocalContext.current as AppCompatActivity

    TransactionDetailScreen(
        state = state,
        onCancelClick = {
            navController.navigateUp()
        },
        onAccountSectionClick = {
            localFocusManager.clearFocus()
            navController.navigate(TransactionDetailFlow.SelectAccount.route)
        },
        onTransferAccountSectionClick = {
            localFocusManager.clearFocus()
            navController.navigate(TransactionDetailFlow.SelectTransferAccount.route)
        },
        onDateSectionClick = {
            activity.showDatePicker(state.transactionDate.toLocalDate()) { selectedDate ->
                viewModel.dispatch(TransactionAction.SelectDate(selectedDate))
            }
        },
        onTransactionTypeSelected = {
            localFocusManager.clearFocus()
            viewModel.dispatch(TransactionAction.SelectTransactionType(it))
        },
        onTotalAmountChange = { viewModel.dispatch(TransactionAction.TotalAmountAction.Change(it)) },
        onTotalAmountFocusChange = { viewModel.dispatch(TransactionAction.TotalAmountAction.FocusChange(it)) },
        onNoteChange = { viewModel.dispatch(TransactionAction.ChangeNote(it)) },
    )
}

@Composable
private fun TransactionDetailScreen(
    state: TransactionState,
    onCancelClick: () -> Unit,
    onAccountSectionClick: () -> Unit,
    onTransferAccountSectionClick: () -> Unit,
    onDateSectionClick: () -> Unit,
    onTransactionTypeSelected: (TransactionTypeItem) -> Unit,
    onTotalAmountChange: (TextFieldValue) -> Unit,
    onTotalAmountFocusChange: (Boolean) -> Unit,
    onNoteChange: (TextFieldValue) -> Unit,
) {
    val localFocusManager = LocalFocusManager.current
    PgPageLayout(
        Modifier.fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        localFocusManager.clearFocus()
                    }
                )
            }
    ) {
        PgHeaderEditMode(
            isAllowToSave = true,
            title = stringResource(R.string.transaction_edit_add),
            onSaveClick = {},
            onCancelClick = onCancelClick,
        )

        TransactionTypeSection(
            transactionTypes = state.transactionTypeItems,
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
                    onTotalAmountChange,
                    onTotalAmountFocusChange
                )
            }

            item {
                Spacer(Modifier.height(16.dp))
            }

            item {
                GeneralSection(
                    transactionType = state.selectedTransactionType(),
                    selectedAccount = state.selectedAccountName() ?: stringResource(AccountType.CASH.getLabel()),
                    selectedTransferAccount = state.selectedAccountTransferName(),
                    transactionDate = state.transactionDateDisplayable(),
                    onAccountSectionClick = onAccountSectionClick,
                    onTransferAccountSectionClick = onTransferAccountSectionClick,
                    onDateSectionClick = onDateSectionClick
                )
            }

            item {
                Spacer(Modifier.height(16.dp))
            }

            item {
                NoteSection(
                    note = state.note,
                    hint = stringResource(state.noteHintDisplayable()),
                    onNoteChange = onNoteChange
                )
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
            .padding(horizontal = 16.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(transactionTypes.size),
            contentPadding = PaddingValues(horizontal = 8.dp),
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = MaterialTheme.shapes.extraLarge
                )
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
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                        CompositionLocalProvider(LocalContentColor provides contentColor) {
                            PgTabLabel(stringResource(it.title))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AmountSection(
    totalAmount: TextFieldValue,
    currency: Currency,
    onTotalAmountChange: (TextFieldValue) -> Unit,
    onTotalAmountFocusChange: (Boolean) -> Unit,
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
            val localFocusManager = LocalFocusManager.current
            BasicTextField(
                value = totalAmount,
                onValueChange = onTotalAmountChange,
                modifier = Modifier.fillMaxWidth()
                    .onFocusChanged {
                        onTotalAmountFocusChange(it.isFocused)
                    },
                textStyle = MaterialTheme.typography.headlineMedium.copy(color = MaterialTheme.colorScheme.onBackground),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Done),
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        localFocusManager.clearFocus()
                    }
                )
            )
        }
    }
}

@Composable
private fun GeneralSection(
    transactionType: TransactionType,
    selectedAccount: String,
    selectedTransferAccount: String,
    transactionDate: String,
    onAccountSectionClick: () -> Unit,
    onTransferAccountSectionClick: () -> Unit,
    onDateSectionClick: () -> Unit,
) {
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
        onClick = onAccountSectionClick,
        trailing = {
            Row {
                PgContentTitle(
                    text = selectedAccount,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = AlphaDisabled)
                )
                Spacer(Modifier.width(8.dp))
                PgIcon(
                    imageVector = Icons.Rounded.ChevronRight,
                    tint = LocalContentColor.current.copy(alpha = AlphaDisabled)
                )
            }
        }
    )

    if (transactionType == TransactionType.TRANSFER) {
        ActionContentCell(
            title = stringResource(R.string.transaction_edit_account_to),
            showDivider = true,
            shape = Shapes.None,
            onClick = onTransferAccountSectionClick,
            trailing = {
                Row {
                    PgContentTitle(
                        text = selectedTransferAccount,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = AlphaDisabled)
                    )
                    Spacer(Modifier.width(8.dp))
                    PgIcon(
                        imageVector = Icons.Rounded.ChevronRight,
                        tint = LocalContentColor.current.copy(alpha = AlphaDisabled)
                    )
                }
            }
        )
    }

    if (transactionType == TransactionType.EXPENSE) {
        ActionContentCell(
            title = stringResource(R.string.transaction_edit_category),
            showDivider = true,
            shape = Shapes.None,
            onClick = {},
            trailing = {
                PgIcon(
                    imageVector = Icons.Rounded.ChevronRight,
                    tint = LocalContentColor.current.copy(alpha = AlphaDisabled)
                )
            }
        )
    }

    ActionContentCell(
        title = stringResource(R.string.transaction_edit_date_transaction),
        showDivider = false,
        shape = RoundedCornerShape(
            bottomStart = MediumRadius,
            bottomEnd = MediumRadius
        ),
        onClick = onDateSectionClick,
        trailing = {
            Row {
                PgContentTitle(
                    text = transactionDate,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = AlphaDisabled)
                )
                Spacer(Modifier.width(8.dp))
                PgIcon(
                    imageVector = Icons.Rounded.ChevronRight,
                    tint = LocalContentColor.current.copy(alpha = AlphaDisabled)
                )
            }
        }
    )
}

@Composable
private fun NoteSection(
    note: TextFieldValue,
    hint: String,
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
        val focusManager = LocalFocusManager.current
        BasicTextField(
            value = note,
            onValueChange = onNoteChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.onBackground),
            keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Sentences, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            )
        )

        if (note.text.isBlank()) {
            PgContentTitle(
                text = hint,
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
    onClick: (() -> Unit),
    trailing: @Composable () -> Unit
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
            Spacer(Modifier.size(8.dp))
            trailing()
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
