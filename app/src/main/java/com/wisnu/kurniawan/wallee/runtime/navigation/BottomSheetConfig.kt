package com.wisnu.kurniawan.wallee.runtime.navigation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Shape
import com.wisnu.kurniawan.wallee.foundation.theme.LargeRadius
import com.wisnu.kurniawan.wallee.foundation.theme.SmallRadius

@Immutable
data class BottomSheetConfig(
    val sheetShape: Shape,
    val showScrim: Boolean
)

val DefaultBottomSheetConfig = BottomSheetConfig(
    RoundedCornerShape(
        topStart = LargeRadius,
        topEnd = LargeRadius
    ),
    true
)
val NoScrimMainBottomSheetConfig = BottomSheetConfig(
    RoundedCornerShape(
        topStart = LargeRadius,
        topEnd = LargeRadius
    ),
    false
)
val NoScrimSmallShapeMainBottomSheetConfig = BottomSheetConfig(
    RoundedCornerShape(
        topStart = SmallRadius,
        topEnd = SmallRadius
    ),
    false
)

