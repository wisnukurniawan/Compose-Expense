package com.wisnu.kurniawan.wallee.features.localized.ui

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wisnu.kurniawan.wallee.R
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgModalBackHeader
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgModalCell
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgModalLayout
import com.wisnu.kurniawan.wallee.foundation.viewmodel.HandleEffect

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun LanguageScreen(
    viewModel: LocalizedViewModel,
    onClickBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HandleEffect(
        viewModel = viewModel,
    ) {
        when (it) {
            is LocalizedEffect.ApplyLanguage -> {
                val lang = it.language.code
                val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(lang)
                AppCompatDelegate.setApplicationLocales(appLocale)
            }
        }
    }

    LanguageScreen(
        items = state.items,
        clickItem = {
            viewModel.dispatch(LocalizedAction.SelectLanguage(it))
        },
        onClickBack = onClickBack
    )
}

@Composable
private fun LanguageScreen(
    items: List<LanguageItem>,
    clickItem: (LanguageItem) -> Unit,
    onClickBack: () -> Unit
) {
    PgModalLayout(
        title = {
            PgModalBackHeader(
                text = stringResource(R.string.setting_language),
                onClickBack = onClickBack
            )
        },
        content = {
            items(items) { item ->
                LanguageItem(
                    onClick = { clickItem(item) },
                    item = item
                )
                Spacer(Modifier.height(8.dp))
            }
        }
    )
}

@Composable
private fun LanguageItem(
    onClick: () -> Unit,
    item: LanguageItem,
) {
    PgModalCell(
        onClick = onClick,
        text = stringResource(item.title),
        color = if (item.applied) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.surfaceVariant
        },
        textColor = if (item.applied) {
            MaterialTheme.colorScheme.onPrimary
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        },
        rightIcon = if (item.applied) {
            @Composable {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
            }
        } else {
            null
        }
    )
}
