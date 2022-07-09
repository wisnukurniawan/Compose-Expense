package com.wisnu.kurniawan.wallee.foundation.extension

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.RectangleShape
import com.wisnu.kurniawan.wallee.foundation.theme.MediumRadius

fun cellShape(index: Int, size: Int) = when {
    // Single item
    size == 1 -> {
        RoundedCornerShape(size = MediumRadius)
    }
    // First index
    index == 0 -> {
        RoundedCornerShape(
            topStart = MediumRadius,
            topEnd = MediumRadius
        )
    }
    // Last index
    index == size - 1 -> {
        RoundedCornerShape(
            bottomStart = MediumRadius,
            bottomEnd = MediumRadius
        )
    }
    // Middle items
    else -> {
        RectangleShape
    }
}

fun shouldShowDivider(index: Int, size: Int): Boolean {
    // Don't show divider if the item only 1 and last item
    return if (size != 1) {
        // Show divider if first or middle item
        index == 0 || index < size - 1
    } else {
        false
    }
}
