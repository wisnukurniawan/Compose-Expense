package com.wisnu.kurniawan.wallee.foundation.uicomponent

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
import com.wisnu.kurniawan.wallee.foundation.theme.AlphaMedium

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
    text: String
) {
    PgTitleBarBase(
        modifier = modifier,
        enabled = enabled,
        text = text,
        style = MaterialTheme.typography.titleSmall.copy(
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Medium
        )
    )
}

@Composable
fun PgTitleBarSecondary(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
) {
    PgTitleBarBase(
        modifier = modifier,
        enabled = enabled,
        text = text,
        style = MaterialTheme.typography.titleSmall.copy(
            color = MaterialTheme.colorScheme.onBackground
        )
    )
}

@Composable
private fun PgTitleBarBase(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    style: TextStyle,
) {
    val textAlpha = if (enabled) AlphaHigh else AlphaDisabled

    Text(
        modifier = modifier,
        style = style.copy(
            color = style.color.copy(alpha = textAlpha)
        ),
        text = text
    )
}

@Composable
fun PgHeadline1(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        style = MaterialTheme.typography.titleLarge,
        text = text,
        modifier = modifier
    )
}

@Composable
fun PgHeadline2(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        style = MaterialTheme.typography.titleMedium,
        text = text,
        modifier = modifier
    )
}

@Composable
fun PgHeadlineLabel(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground.copy(alpha = AlphaMedium)),
        text = text.uppercase(),
        modifier = modifier
    )
}

@Composable
fun PgContentTitle(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        style = MaterialTheme.typography.titleSmall,
        text = text,
        modifier = modifier
    )
}

@Composable
fun PgTabLabel(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        style = MaterialTheme.typography.labelMedium,
        text = text,
        modifier = modifier
    )
}
