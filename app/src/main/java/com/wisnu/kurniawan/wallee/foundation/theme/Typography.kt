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

private val Roboto = FontFamily(
    Font(R.font.roboto_light, FontWeight.Light),
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_bold, FontWeight.SemiBold)
)

// H1
//  displaySmall 36 Medium
// H2
//  headlineSmall 24 Medium
// Inside tab:
//  title small 14, normal to medium
// Date:
//  title small 14 medium
// Body1:
//  title medium 16 normal
// Body2:
//  headline medium 28 normal
//  headline small 24 normal
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = 0.sp
    ),
    displayMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    ),
    headlineLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontFamily = Roboto,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
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
            Text(
                style = MaterialTheme.typography.displayLarge,
                text = "Text displayLarge"
            )
            Spacer(Modifier.height(8.dp))
            Text(
                style = MaterialTheme.typography.displayMedium,
                text = "Text displayMedium"
            )
            Spacer(Modifier.height(8.dp))
            Text(
                style = MaterialTheme.typography.displaySmall,
                text = "Text displaySmall"
            )
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
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Normal),
                text = "Text displaySmall"
            )
            Spacer(Modifier.height(8.dp))
            Text(
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Medium),
                text = "Text displaySmall"
            )
            Spacer(Modifier.height(8.dp))
            Text(
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.SemiBold),
                text = "Text displaySmall"
            )
        }
    }
}
