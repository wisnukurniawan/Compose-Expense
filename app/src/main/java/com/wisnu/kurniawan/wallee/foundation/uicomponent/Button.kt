package com.wisnu.kurniawan.wallee.foundation.uicomponent

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.wisnu.kurniawan.wallee.foundation.theme.AlphaDisabled
import com.wisnu.kurniawan.wallee.foundation.theme.AlphaHigh

@Composable
fun PgModalBackButton(
    onClick: () -> Unit,
    imageVector: ImageVector = Icons.Rounded.ChevronLeft
) {
    PgIconButton(
        onClick = onClick,
        modifier = Modifier.size(28.dp)
    ) {
        PgIcon(
            imageVector = imageVector,
        )
    }
}

@Composable
fun PgIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    color: Color = MaterialTheme.colorScheme.secondary,
    content: @Composable () -> Unit
) {
    val shape = CircleShape
    IconButton(
        onClick = onClick,
        modifier = modifier.background(
            color = color,
            shape = shape
        ).clip(shape),
        enabled = enabled
    ) {
        content()
    }
}

@Composable
fun PgButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        content = content,
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = AlphaDisabled)
        ),
    )
}

@Composable
fun PgSecondaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    border: BorderStroke? = ButtonDefaults.outlinedButtonBorder,
    content: @Composable RowScope.() -> Unit
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        border = border,
        shape = MaterialTheme.shapes.medium,
        content = content
    )
}

private enum class PressState { Pressed, Released }

@Composable
fun PgTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    color: Color = MaterialTheme.colorScheme.primary,
    fontWeight: FontWeight = FontWeight.Normal
) {
    var currentState: PressState by remember { mutableStateOf(PressState.Released) }
    val transition = updateTransition(targetState = currentState, label = "animation")
    val alpha: Float by transition.animateFloat(label = "") { state ->
        if (state == PressState.Pressed || !enabled) {
            AlphaDisabled
        } else {
            AlphaHigh
        }
    }

    PgContentTitle(
        text = text,
        color = color.copy(alpha),
        fontWeight = fontWeight,
        modifier = modifier.pointerInput(enabled) {
            detectTapGestures(
                onPress = {
                    if (enabled) {
                        currentState = PressState.Pressed
                        tryAwaitRelease()
                        currentState = PressState.Released
                    }
                },
                onTap = {
                    if (enabled) {
                        onClick()
                    }
                }
            )
        }
    )
}
