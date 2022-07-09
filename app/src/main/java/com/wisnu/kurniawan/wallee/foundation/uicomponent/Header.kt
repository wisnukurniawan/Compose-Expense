package com.wisnu.kurniawan.wallee.foundation.uicomponent

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.wisnu.kurniawan.wallee.R

@Composable
fun PgModalBackHeader(
    text: String,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Box(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(0.2F)
        ) {
            PgModalBackButton(
                onClick = onClickBack
            )
        }

        PgModalTitle(
            text = text,
            modifier = Modifier.weight(0.6F)
        )

        Spacer(
            Modifier
                .size(0.dp)
                .weight(0.2F)
        )
    }
}

@Composable
fun PgBackHeader(
    text: String,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .padding(start = 4.dp)
                .align(Alignment.CenterStart)
        ) {
            PgIconButton(
                onClick = onClickBack,
                color = Color.Transparent
            ) {
                PgIcon(imageVector = Icons.Rounded.ChevronLeft)
            }
        }
        PgTitleBar(
            modifier = Modifier.align(Alignment.Center),
            text = text,
        )
    }
}

@Composable
fun PgHeaderEditMode(
    isAllowToSave: Boolean,
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit,
    title: String
) {
    Box(
        modifier = Modifier
            .height(56.dp)
            .padding(horizontal = 4.dp)
            .fillMaxWidth()
    ) {
        PgTextButton(
            modifier = Modifier.align(Alignment.CenterStart)
                .padding(start = 16.dp),
            enabled = true,
            text = stringResource(R.string.transaction_edit_cancel),
            color = MaterialTheme.colorScheme.onSurface,
            onClick = onCancelClick
        )

        PgTitleBarPrimary(
            modifier = Modifier.align(Alignment.Center),
            text = title,
        )

        PgTextButton(
            modifier = Modifier.align(Alignment.CenterEnd)
                .padding(end = 16.dp),
            enabled = isAllowToSave,
            text = stringResource(R.string.transaction_edit_save),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Medium,
            onClick = onSaveClick
        )
    }
}
