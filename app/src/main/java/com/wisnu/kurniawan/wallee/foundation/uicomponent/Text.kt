package com.wisnu.kurniawan.wallee.foundation.uicomponent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.wisnu.kurniawan.wallee.foundation.theme.AlphaDisabled
import com.wisnu.kurniawan.wallee.foundation.theme.AlphaHigh

@Composable
fun PgModalTitle(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = Color.Unspecified
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Center),
            color = textColor,
        )
    }
}

@Composable
fun PgTitleBar(
    modifier: Modifier = Modifier,
    text: String,
) {
    PgTitleBarBase(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.titleSmall.copy(
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Medium
        ),
    )
}

@Composable
fun PgTitleBarPrimary(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    onClick: () -> Unit = {},
) {
    PgTitleBarBase(
        modifier = modifier,
        enabled = enabled,
        text = text,
        style = MaterialTheme.typography.titleSmall.copy(
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Medium
        ),
        onClick = onClick
    )
}

@Composable
fun PgTitleBarSecondary(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    onClick: () -> Unit,
) {
    PgTitleBarBase(
        modifier = modifier,
        enabled = enabled,
        text = text,
        style = MaterialTheme.typography.titleSmall.copy(
            color = MaterialTheme.colorScheme.onBackground
        ),
        onClick = onClick
    )
}

@Composable
private fun PgTitleBarBase(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    style: TextStyle,
    onClick: (() -> Unit)? = null,
) {
    val textAlpha = if (enabled) AlphaHigh else AlphaDisabled
    if (enabled && onClick != null) {
        modifier.clickable {
            onClick.invoke()
        }
    }

    Text(
        modifier = modifier,
        style = style.copy(
            color = style.color.copy(alpha = textAlpha)
        ),
        text = text
    )
}
