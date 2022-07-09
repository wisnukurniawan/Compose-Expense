package com.wisnu.kurniawan.wallee.foundation.uicomponent

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

private val LinearIndicatorWidth = 240.dp
private val LinearIndicatorHeight = ProgressIndicatorDefaults.StrokeWidth

@Composable
internal fun RoundedLinearProgressIndicator(
    /*@FloatRange(from = 0.0, to = 1.0)*/
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary,
    trackColor: Color = color.copy(alpha = ProgressIndicatorDefaults.IndicatorBackgroundOpacity)
) {
    Canvas(
        modifier
            .progressSemantics(progress)
            .size(LinearIndicatorWidth, LinearIndicatorHeight)
    ) {
        val strokeWidth = size.height
        drawLinearIndicatorTrack(
            color = trackColor,
            strokeWidth = strokeWidth
        )
        drawRoundedLinearProgressIndicator(
            startFraction = 0f,
            endFraction = progress,
            color = color,
            strokeWidth = strokeWidth
        )
    }
}

private fun DrawScope.drawLinearIndicatorTrack(
    color: Color,
    strokeWidth: Float
) = drawRoundedLinearProgressIndicator(0f, 1f, color, strokeWidth)

private fun DrawScope.drawRoundedLinearProgressIndicator(
    startFraction: Float,
    endFraction: Float,
    color: Color,
    strokeWidth: Float,
) {
    val cap = StrokeCap.Round
    val width = size.width
    val height = size.height
    // Start drawing from the vertical center of the stroke
    val yOffset = height / 2

    val roundedCapOffset = size.height / 2

    val isLtr = layoutDirection == LayoutDirection.Ltr
    val barStart = (if (isLtr) startFraction else 1f - endFraction) * width + if (isLtr) roundedCapOffset else -roundedCapOffset
    val barEnd = (if (isLtr) endFraction else 1f - startFraction) * width - if (isLtr) roundedCapOffset else -roundedCapOffset

    // Progress line
    drawLine(
        color = color,
        start = Offset(barStart, yOffset),
        end = Offset(barEnd, yOffset),
        strokeWidth = strokeWidth,
        cap = cap,
    )
}
