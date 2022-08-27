package com.wisnu.kurniawan.wallee.features.onboarding.ui

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wisnu.kurniawan.wallee.R
import com.wisnu.kurniawan.wallee.foundation.extension.cellShape
import com.wisnu.kurniawan.wallee.foundation.extension.shouldShowDivider
import com.wisnu.kurniawan.wallee.foundation.theme.AlphaDisabled
import com.wisnu.kurniawan.wallee.foundation.theme.DividerAlpha
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgButton
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgHeadline1
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgHeadline2
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgIcon
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgIconButton
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgPageLayout
import com.wisnu.kurniawan.wallee.foundation.viewmodel.HandleEffect
import com.wisnu.kurniawan.wallee.model.Currency

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel,
    onSettingClick: () -> Unit,
    onClosePage: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HandleEffect(
        viewModel = viewModel
    ) {
        when (it) {
            OnboardingEffect.ClosePage -> {
                onClosePage()
            }
        }
    }

    OnboardingScreen(
        state = state,
        onItemClick = {
            viewModel.dispatch(OnboardingAction.SelectCurrency(it.currency))
        },
        onSaveClick = {
            viewModel.dispatch(OnboardingAction.ClickSave)
        },
        onSettingClick = onSettingClick
    )
}

@Composable
private fun OnboardingScreen(
    state: OnboardingState,
    onSettingClick: () -> Unit,
    onItemClick: (CurrencyItem) -> Unit,
    onSaveClick: () -> Unit,
) {
    PgPageLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        Header(
            onSettingClick = onSettingClick,
        )
        Box(modifier = Modifier.fillMaxSize().weight(1F)) {
            Content(
                state = state,
                onItemClick = onItemClick,
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.surface),
            ) {
                PgButton(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    onClick = onSaveClick,
                    enabled = state.canSave(),
                ) {
                    Text(
                        style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.onBackground, fontSize = 16.sp, fontWeight = FontWeight.Medium),
                        text = stringResource(R.string.transaction_edit_save), color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

@Composable
private fun Header(
    onSettingClick: () -> Unit,
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
    }
}

@Composable
private fun Content(
    state: OnboardingState,
    onItemClick: (CurrencyItem) -> Unit,
) {
    val items by state.rememberGroupedCurrencyItems()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        item {
            PgHeadline1(
                text = stringResource(R.string.welcome),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }

        item {
            SpacerSection()
        }

        CurrencyCell(
            data = items,
            selectedCurrency = state.selectedCurrency,
            onItemClick = onItemClick,
        )

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
private inline fun LazyListScope.CurrencyCell(
    data: Map<String, List<CurrencyItem>>,
    selectedCurrency: Currency?,
    noinline onItemClick: (CurrencyItem) -> Unit,
) {
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PgHeadline2(text = stringResource(R.string.onboarding_title))
        }

        SpacerHeadline2()
    }

    data.forEach { (title, currencies) ->
        if (title.isNotBlank()) {
            stickyHeader {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.surface)
                ) {
                    Text(
                        style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.onBackground, fontSize = 16.sp),
                        text = title,
                        modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
                    )
                }
            }
        }

        val size = currencies.size
        itemsIndexed(
            items = currencies,
            key = { index, item -> index.toString() + item.currency.countryCode + item.currency.currencyCode }
        ) { index, item ->
            CurrencyItemCell(
                currencySymbol = item.currencySymbol,
                flag = item.flag,
                countryName = item.countryName,
                selected = item.currency == selectedCurrency,
                shape = cellShape(index, size),
                showDivider = shouldShowDivider(index, size),
                onClick = { onItemClick(item) }
            )
        }
    }
}

@Composable
private fun CurrencyItemCell(
    currencySymbol: String,
    flag: String,
    countryName: String,
    selected: Boolean,
    shape: Shape,
    showDivider: Boolean,
    onClick: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(shape)
            .clickable(onClick = onClick),
        shape = shape,
        color = MaterialTheme.colorScheme.secondary,
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(modifier = Modifier.weight(.75F)) {
                    Text(
                        style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.onBackground, fontSize = 16.sp),
                        text = flag,
                        modifier = Modifier.padding(end = 8.dp)
                    )

                    Text(
                        style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.onBackground, fontSize = 16.sp),
                        text = countryName,
                    )
                }

                Row(
                    modifier = Modifier.weight(.25f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        style = MaterialTheme.typography.headlineMedium
                            .copy(color = MaterialTheme.colorScheme.onBackground.copy(AlphaDisabled), fontSize = 16.sp),
                        text = currencySymbol,
                        modifier = Modifier.padding(end = 8.dp).align(alignment = Alignment.CenterVertically)
                    )

                    if (selected) {
                        PgIcon(
                            imageVector = Icons.Rounded.Check,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.align(alignment = Alignment.CenterVertically).size(20.dp)
                        )
                    }
                }
            }

            if (showDivider) {
                Divider(
                    modifier = Modifier.padding(start = 16.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = DividerAlpha)
                )
            }
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
