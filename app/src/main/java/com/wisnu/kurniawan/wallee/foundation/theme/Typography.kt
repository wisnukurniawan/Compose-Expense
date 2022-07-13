package com.wisnu.kurniawan.wallee.foundation.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wisnu.kurniawan.wallee.R

private val Sans = FontFamily(
    Font(R.font.sans_light, FontWeight.Light),
    Font(R.font.sans_regular, FontWeight.Normal),
    Font(R.font.sans_medium, FontWeight.Medium),
    Font(R.font.sans_bold, FontWeight.SemiBold)
)

private val Lato = FontFamily(
    Font(R.font.lato_regular, FontWeight.Normal),
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = Lato,
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = Lato,
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = Lato,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = Lato,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = Lato,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = Lato,
        fontWeight = FontWeight.Normal,
        fontSize = 21.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = Sans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = Sans,
        fontWeight = FontWeight.Medium,
        fontSize = 21.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = Sans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = Sans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = Sans,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = Sans,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = Sans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = Sans,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = Sans,
        fontWeight = FontWeight.Normal,
        fontSize = 9.sp,
    )
)

@Composable
@Preview
private fun TextPreview() {
    MaterialTheme(
        colorScheme = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
    ) {
        Column(Modifier.verticalScroll(rememberScrollState())) {
            Spacer(Modifier.height(8.dp))
            Text(
                style = MaterialTheme.typography.headlineLarge,
                text = "Text headlineLarge"
            )
            Spacer(Modifier.height(8.dp))
            Text(
                style = MaterialTheme.typography.headlineMedium,
                text = "Text headlineMedium"
            )
            Spacer(Modifier.height(8.dp))
            Text(
                style = MaterialTheme.typography.headlineSmall,
                text = "Text headlineSmall"
            )
            Spacer(Modifier.height(8.dp))
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = "Text titleLarge"
            )
            Spacer(Modifier.height(8.dp))
            Text(
                style = MaterialTheme.typography.titleMedium,
                text = "Text titleMedium"
            )
            Spacer(Modifier.height(8.dp))
            Text(
                style = MaterialTheme.typography.titleSmall,
                text = "Text titleSmall"
            )
            Spacer(Modifier.height(8.dp))
            Text(
                style = MaterialTheme.typography.bodyLarge,
                text = "Text bodyLarge"
            )
            Spacer(Modifier.height(8.dp))
            Text(
                style = MaterialTheme.typography.bodyMedium,
                text = "Text bodyMedium"
            )
            Spacer(Modifier.height(8.dp))
            Text(
                style = MaterialTheme.typography.bodySmall,
                text = "Text bodySmall"
            )
            Spacer(Modifier.height(8.dp))
            Text(
                style = MaterialTheme.typography.labelLarge,
                text = "Text labelLarge"
            )
            Spacer(Modifier.height(8.dp))
            Text(
                style = MaterialTheme.typography.labelMedium,
                text = "Text labelMedium"
            )
            Spacer(Modifier.height(8.dp))
            Text(
                style = MaterialTheme.typography.labelSmall,
                text = "Text labelSmall"
            )
        }
    }
}

@Composable
@Preview
private fun TextPreview2() {
    MaterialTheme(
        colorScheme = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
    ) {
        Column(Modifier.verticalScroll(rememberScrollState())) {
            Spacer(Modifier.height(8.dp))
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = "Summary"
            )
            Spacer(Modifier.height(8.dp))
            Text(
                style = MaterialTheme.typography.titleMedium,
                text = "Activity"
            )
            Spacer(Modifier.height(8.dp))
            Text(
                style = MaterialTheme.typography.titleSmall,
                text = "Indoor run"
            )
            Spacer(Modifier.height(8.dp))
            Text(
                style = MaterialTheme.typography.titleSmall,
                text = "Show more"
            )

            Spacer(Modifier.height(8.dp))
            Text(
                style = MaterialTheme.typography.titleSmall,
                text = "Cancel"
            )
            Spacer(Modifier.height(8.dp))
            Text(
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Medium),
                text = "Title"
            )
            Spacer(Modifier.height(8.dp))
            Text(
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Medium),
                text = "Save"
            )

            Spacer(Modifier.height(8.dp))
            Text(
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
                text = "Featured"
            )
            Spacer(Modifier.height(8.dp))
            Text(
                style = MaterialTheme.typography.labelMedium,
                text = "Charts"
            )

            Spacer(Modifier.height(8.dp))
            Text(
                style = MaterialTheme.typography.headlineMedium,
                text = "2,12km"
            )
            Spacer(Modifier.height(8.dp))
            Text(
                style = MaterialTheme.typography.headlineMedium,
                text = "Rp 2.873.991"
            )
            Spacer(Modifier.height(8.dp))

            Text(
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.typography.bodyMedium.color.copy(alpha = AlphaMedium)),
                text = "THURSDAY, 26 JUN"
            )
            Spacer(Modifier.height(8.dp))
            Text(
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.typography.bodyMedium.color.copy(alpha = AlphaMedium)),
                text = "07/06/22"
            )
            Spacer(Modifier.height(8.dp))
            Text(
                style = MaterialTheme.typography.labelSmall,
                text = "Summary"
            )
        }
    }
}
