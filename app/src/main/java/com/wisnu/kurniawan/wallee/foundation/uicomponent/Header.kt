package com.wisnu.kurniawan.wallee.foundation.uicomponent

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

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

// State
// Add
// cancel tab save/can not save

// Edit
// cancel title save/can not save
// delete transaction
@Composable
fun PgHeaderEditMode(
    titleText: String,
    isAllowToSave: Boolean,
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
    ) {
        TextButton(
            onClick = onCancelClick,
            modifier = Modifier
                .align(Alignment.CenterStart),
        ) {
            Text(text = "Cancel")
        }

        Text(
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
            modifier = Modifier
                .align(Alignment.Center),
            text = titleText
        )

        TextButton(
            onClick = onSaveClick,
            modifier = Modifier
                .align(Alignment.CenterEnd),
            enabled = isAllowToSave,
        ) {
            Text(
                text = "Save",
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
