package com.wisnu.kurniawan.wallee.foundation.uicomponent

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wisnu.kurniawan.wallee.foundation.theme.AlphaDisabled
import com.wisnu.kurniawan.wallee.foundation.theme.AlphaHigh
import com.wisnu.kurniawan.wallee.foundation.theme.DividerAlpha
import com.wisnu.kurniawan.wallee.foundation.uiextension.paddingCell

@Composable
fun PgModalCell(
    onClick: () -> Unit,
    text: String,
    color: Color = MaterialTheme.colorScheme.surfaceVariant,
    textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    enabled: Boolean = true,
    leftIcon: @Composable (() -> Unit)? = null,
    rightIcon: @Composable (() -> Unit)? = null
) {
    val colorAlpha = if (enabled) {
        AlphaHigh
    } else {
        AlphaDisabled
    }
    val onClickState = if (enabled) {
        onClick
    } else {
        {}
    }
    val indication = if (enabled) {
        LocalIndication.current
    } else {
        null
    }

    val shape = MaterialTheme.shapes.medium
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(56.dp)
            .clip(shape)
            .clickable(
                onClick = onClickState,
                indication = indication,
                interactionSource = remember { MutableInteractionSource() }
            ),
        shape = shape,
        color = color.copy(alpha = colorAlpha),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (leftIcon != null) {
                Spacer(Modifier.width(8.dp))
                leftIcon()
                Spacer(Modifier.width(16.dp))
            } else {
                Spacer(Modifier.width(20.dp))
            }

            PgContentTitle(
                text = text,
                color = textColor.copy(alpha = colorAlpha)
            )

            if (rightIcon != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    rightIcon()
                    Spacer(Modifier.size(20.dp))
                }
            }
        }
    }
}

@Composable
fun ActionContentCell(
    title: String,
    desc: String = "",
    showDivider: Boolean,
    shape: Shape,
    enabled: Boolean = true,
    onClick: (() -> Unit),
    trailing: @Composable () -> Unit
) {
    val onClickState = if (enabled) {
        onClick
    } else {
        {}
    }
    val indication = if (enabled) {
        LocalIndication.current
    } else {
        null
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .clickable(
                onClick = onClickState,
                indication = indication,
                interactionSource = remember { MutableInteractionSource() }
            ),
        color = MaterialTheme.colorScheme.secondary,
        shape = shape,
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .paddingCell()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    PgContentTitle(
                        text = title
                    )
                    if (desc.isNotBlank()) {
                        PgContentTitle(
                            text = desc,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = AlphaDisabled),
                        )
                    }
                }
                Spacer(Modifier.size(8.dp))
                trailing()
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
    }
}

@Composable
fun ActionContentCell(
    title: String,
    titleColor: Color = MaterialTheme.colorScheme.onBackground,
    showDivider: Boolean,
    shape: Shape,
    enabled: Boolean = true,
    insetSize: Dp,
    onClick: (() -> Unit) = {},
    trailing: @Composable () -> Unit
) {
    val onClickState = if (enabled) {
        onClick
    } else {
        {}
    }
    val indication = if (enabled) {
        LocalIndication.current
    } else {
        null
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .clickable(
                onClick = onClickState,
                indication = indication,
                interactionSource = remember { MutableInteractionSource() }
            ),
        color = MaterialTheme.colorScheme.secondary,
        shape = shape,
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .paddingCell()
            ) {
                PgContentTitle(
                    text = title,
                    modifier = Modifier.width(insetSize),
                    color = titleColor
                )
                Spacer(Modifier.size(8.dp))
                trailing()
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
    }
}

