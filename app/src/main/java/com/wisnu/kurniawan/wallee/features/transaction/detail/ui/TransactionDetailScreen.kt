package com.wisnu.kurniawan.wallee.features.transaction.detail.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wisnu.foundation.coredatetime.toLocalDateTime
import com.wisnu.foundation.coredatetime.toMillis
import com.wisnu.kurniawan.wallee.R
import com.wisnu.kurniawan.wallee.foundation.extension.getEmojiAndText
import com.wisnu.kurniawan.wallee.foundation.theme.AlphaDisabled
import com.wisnu.kurniawan.wallee.foundation.theme.AlphaHigh
import com.wisnu.kurniawan.wallee.foundation.theme.MediumRadius
import com.wisnu.kurniawan.wallee.foundation.uicomponent.ActionContentCell
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgBasicTextField
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgContentTitle
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgHeaderEditMode
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgHeadlineLabel
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgIcon
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgPageLayout
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgSecondaryButton
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgTabLabel
import com.wisnu.kurniawan.wallee.foundation.uiextension.paddingCell
import com.wisnu.kurniawan.wallee.foundation.viewmodel.HandleEffect
import com.wisnu.kurniawan.wallee.model.CategoryType
import com.wisnu.kurniawan.wallee.model.TransactionType
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

@Composable
fun TransactionDetailScreen(
    viewModel: TransactionDetailViewModel,
    onClosePage: () -> Unit,
    onCancelClick: () -> Unit,
    onAccountSectionClick: () -> Unit,
    onAddAccountClick: () -> Unit,
    onCategorySectionClick: () -> Unit,
    onTransferAccountSectionClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val focusRequester = remember { FocusRequester() }
    val localFocusManager = LocalFocusManager.current
    val activity = LocalContext.current as AppCompatActivity

    HandleEffect(
        viewModel = viewModel,
    ) {
        when (it) {
            TransactionEffect.ClosePage -> {
                onClosePage()
            }

            TransactionEffect.ShowAmountKeyboard -> {
                focusRequester.requestFocus()
            }
        }
    }

    TransactionDetailScreen(
        state = state,
        focusRequester = focusRequester,
        onSaveClick = {
            localFocusManager.clearFocus()
            viewModel.dispatch(TransactionAction.Save)
        },
        onCancelClick = {
            localFocusManager.clearFocus()
            onCancelClick()
        },
        onAccountSectionClick = {
            localFocusManager.clearFocus()
            onAccountSectionClick()

        },
        onCategorySectionClick = {
            localFocusManager.clearFocus()
            onCategorySectionClick()

        },
        onTransferAccountSectionClick = {
            localFocusManager.clearFocus()
            onTransferAccountSectionClick()
        },
        onAddAccountClick = {
            localFocusManager.clearFocus()
            onAddAccountClick()
        },
        onDateSectionClick = {
            viewModel.dispatch(TransactionAction.ShowDatePicker)
        },
        onClickDateCancel = {
            viewModel.dispatch(TransactionAction.DismissDatePicker)
        },
        onClickDateSelect = {
            viewModel.dispatch(TransactionAction.SelectDate(it))
        },
        onDeleteClick = {
            localFocusManager.clearFocus()
            viewModel.dispatch(TransactionAction.Delete)
        },
        onTransactionTypeSelected = {
            localFocusManager.clearFocus()
            viewModel.dispatch(TransactionAction.SelectTransactionType(it))
        },
        onTotalAmountChange = { viewModel.dispatch(TransactionAction.TotalAmountAction.Change(it)) },
        onNoteChange = { viewModel.dispatch(TransactionAction.ChangeNote(it)) },
    )
}

@Composable
private fun TransactionDetailScreen(
    state: TransactionState,
    focusRequester: FocusRequester,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    onAccountSectionClick: () -> Unit,
    onCategorySectionClick: () -> Unit,
    onTransferAccountSectionClick: () -> Unit,
    onAddAccountClick: () -> Unit,
    onDateSectionClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onTransactionTypeSelected: (TransactionType) -> Unit,
    onTotalAmountChange: (TextFieldValue) -> Unit,
    onNoteChange: (TextFieldValue) -> Unit,
    onClickDateCancel: () -> Unit,
    onClickDateSelect: (LocalDateTime?) -> Unit,
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
            isAllowToSave = state.isValid(),
            title = state.getTitle(),
            onSaveClick = onSaveClick,
            onCancelClick = onCancelClick,
        )

        if (!state.isEditMode) {
            TransactionTypeSection(
                transactionTypes = getTransactionTypes(),
                onSelected = onTransactionTypeSelected,
                selectedType = state.transactionType
            )

            Spacer(Modifier.height(16.dp))
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .navigationBarsPadding()
                .imePadding()
        ) {
            item {
                AmountSection(
                    totalAmount = state.totalAmount,
                    totalAmountDisplay = state.getAmountDisplay(),
                    amountColor = state.getAmountColor(MaterialTheme.colorScheme.onSurface),
                    focusRequester = focusRequester,
                    onTotalAmountChange = onTotalAmountChange,
                )
            }

            item {
                Spacer(Modifier.height(16.dp))
            }

            item {
                GeneralSection(
                    showDatePicker = state.showDatePicker,
                    transactionType = state.transactionType,
                    selectedAccount = state.selectedAccountName(),
                    selectedCategoryType = state.categoryType,
                    selectedTransferAccount = state.selectedAccountTransferName(),
                    transactionDateDisplay = state.transactionDateDisplayable(),
                    transactionDateInitial = state.transactionDate,
                    hasTransferAccount = state.hasTransferAccount(),
                    isEditMode = state.isEditMode,
                    onAccountSectionClick = onAccountSectionClick,
                    onCategorySectionClick = onCategorySectionClick,
                    onTransferAccountSectionClick = onTransferAccountSectionClick,
                    onAddAccountClick = onAddAccountClick,
                    onDateSectionClick = onDateSectionClick,
                    onClickDateCancel = onClickDateCancel,
                    onClickDateSelect = onClickDateSelect,
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

            if (state.isEditMode) {
                item {
                    Spacer(Modifier.height(32.dp))
                    PgSecondaryButton(
                        modifier = Modifier.fillMaxWidth(),
                        border = BorderStroke(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.error
                        ),
                        onClick = onDeleteClick
                    ) {
                        PgContentTitle(text = stringResource(R.string.transaction_edit_delete), color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun TransactionTypeSection(
    selectedType: TransactionType,
    transactionTypes: List<TransactionType>,
    onSelected: (TransactionType) -> Unit
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
                val selected = it == selectedType
                val backgroundColor = if (selected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.secondary
                }
                val contentColor = if (selected) {
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
                            PgTabLabel(stringResource(it.getTitle()))
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
    totalAmountDisplay: String,
    amountColor: Color,
    focusRequester: FocusRequester,
    onTotalAmountChange: (TextFieldValue) -> Unit,
) {
    PgHeadlineLabel(
        text = stringResource(R.string.transaction_edit_total),
        modifier = Modifier.padding(start = 16.dp, bottom = 6.dp)
    )

    Box(
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.secondary,
            shape = MaterialTheme.shapes.medium
        )
            .fillMaxWidth()
            .paddingCell()
    ) {
        PgContentTitle(
            text = totalAmountDisplay,
            color = amountColor,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {
                        focusRequester.requestFocus()
                    }
                )
        )
        val localFocusManager = LocalFocusManager.current
        PgBasicTextField(
            value = totalAmount,
            onValueChange = onTotalAmountChange,
            modifier = Modifier.focusRequester(focusRequester).alpha(0f).size(1.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            singleLine = true,
            keyboardActions = KeyboardActions(
                onDone = {
                    localFocusManager.clearFocus()
                }
            ),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GeneralSection(
    showDatePicker: Boolean,
    transactionType: TransactionType,
    selectedAccount: String,
    selectedCategoryType: CategoryType,
    selectedTransferAccount: String,
    hasTransferAccount: Boolean,
    transactionDateDisplay: String,
    transactionDateInitial: LocalDateTime,
    isEditMode: Boolean,
    onAccountSectionClick: () -> Unit,
    onCategorySectionClick: () -> Unit,
    onTransferAccountSectionClick: () -> Unit,
    onAddAccountClick: () -> Unit,
    onDateSectionClick: () -> Unit,
    onClickDateCancel: () -> Unit,
    onClickDateSelect: (LocalDateTime?) -> Unit,
) {
    val alpha = if (isEditMode) AlphaDisabled else AlphaHigh
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
        insetSize = 120.dp,
        enabled = !isEditMode,
        onClick = onAccountSectionClick,
        trailing = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                PgContentTitle(
                    text = selectedAccount,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = alpha)
                )
                if (!isEditMode) {
                    Spacer(Modifier.width(8.dp))
                    PgIcon(
                        imageVector = Icons.Rounded.ChevronRight,
                        tint = LocalContentColor.current.copy(alpha = alpha)
                    )
                }
            }
        }
    )

    if (transactionType == TransactionType.TRANSFER) {
        ActionContentCell(
            title = stringResource(R.string.transaction_edit_account_to),
            showDivider = true,
            shape = MaterialTheme.shapes.extraSmall,
            enabled = !isEditMode,
            insetSize = 120.dp,
            onClick = {
                if (hasTransferAccount) {
                    onTransferAccountSectionClick()
                } else {
                    onAddAccountClick()
                }
            },
            trailing = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (hasTransferAccount) {
                        PgContentTitle(
                            text = selectedTransferAccount,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = alpha)
                        )

                        if (!isEditMode) {
                            Spacer(Modifier.width(8.dp))
                            PgIcon(
                                imageVector = Icons.Rounded.ChevronRight,
                                tint = LocalContentColor.current.copy(alpha = alpha)
                            )
                        }
                    } else {
                        PgContentTitle(
                            text = stringResource(R.string.account_edit_add),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        )
    }

    if (transactionType == TransactionType.EXPENSE) {
        ActionContentCell(
            title = stringResource(R.string.category),
            showDivider = true,
            shape = MaterialTheme.shapes.extraSmall,
            insetSize = 120.dp,
            onClick = onCategorySectionClick,
            trailing = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val (emoji, name) = selectedCategoryType.getEmojiAndText()
                    PgContentTitle(
                        text = stringResource(name) + " " + emoji,
                    )
                    Spacer(Modifier.width(8.dp))
                    PgIcon(
                        imageVector = Icons.Rounded.ChevronRight,
                    )
                }
            }
        )
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = transactionDateInitial.toMillis(ZoneId.ofOffset("UTC", ZoneOffset.UTC)),
            initialDisplayedMonthMillis = transactionDateInitial.toMillis(ZoneId.ofOffset("UTC", ZoneOffset.UTC)),
            yearRange = DatePickerDefaults.YearRange,
            initialDisplayMode = DisplayMode.Picker,
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    val zone = ZoneId.ofOffset("UTC", ZoneOffset.UTC)
                    val start = LocalDate.of(2000, 1, 1).toMillis(zone)
                    val end = LocalDate.now().toMillis(zone)
                    return utcTimeMillis in start..end
                }
            }
        )
        val confirmEnabled by remember { derivedStateOf { datePickerState.selectedDateMillis != null } }
        DatePickerDialog(
            onDismissRequest = onClickDateCancel,
            confirmButton = {
                TextButton(
                    onClick = {
                        onClickDateSelect(datePickerState.selectedDateMillis?.toLocalDateTime())
                    },
                    enabled = confirmEnabled
                ) { Text("Oke") }
            },
            dismissButton = {
                TextButton(
                    onClick = onClickDateCancel
                ) { Text(stringResource(R.string.transaction_edit_cancel)) }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    ActionContentCell(
        title = stringResource(R.string.transaction_edit_date_transaction),
        showDivider = false,
        shape = RoundedCornerShape(
            bottomStart = MediumRadius,
            bottomEnd = MediumRadius
        ),
        insetSize = 120.dp,
        onClick = onDateSectionClick,
        trailing = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                PgContentTitle(
                    text = transactionDateDisplay,
                )
                Spacer(Modifier.width(8.dp))
                PgIcon(
                    imageVector = Icons.Rounded.ChevronRight,
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
            .paddingCell()
    ) {
        val focusManager = LocalFocusManager.current
        PgBasicTextField(
            value = note,
            onValueChange = onNoteChange,
            keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Sentences, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            placeholderValue = hint
        )
    }
}
