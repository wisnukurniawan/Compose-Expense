package com.wisnu.kurniawan.wallee.features.account.detail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
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
import com.wisnu.kurniawan.wallee.foundation.uicomponent.ActionContentCell
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgAmountLabel
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgBasicTextField
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgContentTitle
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgHeaderEditMode
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgHeadlineLabel
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgIcon
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgPageLayout
import com.wisnu.kurniawan.wallee.foundation.uiextension.collectAsEffect
import com.wisnu.kurniawan.wallee.runtime.navigation.AccountDetailFlow

@Composable
fun AccountDetailScreen(
    navController: NavController,
    viewModel: AccountDetailViewModel
) {
    val state by viewModel.state.collectAsState()
    val effect by viewModel.effect.collectAsEffect()

    val localFocusManager = LocalFocusManager.current

    when (effect) {
        AccountDetailEffect.ClosePage -> {
            LaunchedEffect(effect) {
                navController.navigateUp()
            }
        }
        null -> {}
    }

    AccountDetailScreen(
        state = state,
        onSaveClick = {
            localFocusManager.clearFocus()
            viewModel.dispatch(AccountDetailAction.Save)
        },
        onCancelClick = {
            localFocusManager.clearFocus()
            navController.navigateUp()
        },
        onCategorySectionClick = {
            localFocusManager.clearFocus()
            navController.navigate(AccountDetailFlow.SelectCategory.route)
        },
        onTotalAmountChange = { viewModel.dispatch(AccountDetailAction.TotalAmountAction.Change(it)) },
        onTotalAmountFocusChange = { viewModel.dispatch(AccountDetailAction.TotalAmountAction.FocusChange(it)) },
        onNameChange = { viewModel.dispatch(AccountDetailAction.NameChange(it)) },
    )
}

@Composable
private fun AccountDetailScreen(
    state: AccountDetailState,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    onNameChange: (TextFieldValue) -> Unit,
    onCategorySectionClick: () -> Unit,
    onTotalAmountChange: (TextFieldValue) -> Unit,
    onTotalAmountFocusChange: (Boolean) -> Unit,
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
            isAllowToSave = false, // TODO wisnu
            title = stringResource(R.string.account_edit_add),
            onSaveClick = onSaveClick,
            onCancelClick = onCancelClick,
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .navigationBarsPadding()
                .imePadding()
        ) {
            item {
                NameSection(
                    name = state.name,
                    onNameChange = onNameChange
                )
            }

            item {
                Spacer(Modifier.height(16.dp))
            }

            item {
                CategorySection(
                    categoryName = stringResource(state.selectedAccountType().getLabel()),
                    onCategorySectionClick = onCategorySectionClick
                )
            }

            item {
                Spacer(Modifier.height(16.dp))
            }

            item {
                AmountSection(
                    totalAmount = state.totalAmount,
                    totalAmountDisplay = state.currency.getSymbol() + " ",
                    onTotalAmountChange = onTotalAmountChange,
                    onTotalAmountFocusChange = onTotalAmountFocusChange
                )
            }
        }
    }
}


@Composable
private fun NameSection(
    name: TextFieldValue,
    onNameChange: (TextFieldValue) -> Unit
) {
    PgHeadlineLabel(
        text = stringResource(R.string.account_edit_name),
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
        PgBasicTextField(
            value = name,
            onValueChange = onNameChange,
            keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Sentences, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            placeholderValue = stringResource(R.string.account_edit_name_hint)
        )
    }
}

@Composable
private fun CategorySection(
    categoryName: String,
    onCategorySectionClick: () -> Unit,
) {
    ActionContentCell(
        title = stringResource(R.string.category),
        showDivider = false,
        shape = MaterialTheme.shapes.medium,
        onClick = onCategorySectionClick,
        trailing = {
            Row {
                PgContentTitle(
                    text = categoryName,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = AlphaDisabled),
                )
                Spacer(Modifier.width(8.dp))
                PgIcon(
                    imageVector = Icons.Rounded.ChevronRight,
                    tint = LocalContentColor.current.copy(alpha = AlphaDisabled)
                )
            }
        },
    )
}

@Composable
private fun AmountSection(
    totalAmount: TextFieldValue,
    totalAmountDisplay: String,
    onTotalAmountChange: (TextFieldValue) -> Unit,
    onTotalAmountFocusChange: (Boolean) -> Unit,
) {
    PgHeadlineLabel(
        text = stringResource(R.string.account_edit_balance),
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
            text = totalAmountDisplay,
            color = MaterialTheme.colorScheme.onSurface
        )
        val localFocusManager = LocalFocusManager.current
        PgBasicTextField(
            value = totalAmount,
            onValueChange = onTotalAmountChange,
            modifier = Modifier.onFocusChanged {
                onTotalAmountFocusChange(it.isFocused)
            },
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
