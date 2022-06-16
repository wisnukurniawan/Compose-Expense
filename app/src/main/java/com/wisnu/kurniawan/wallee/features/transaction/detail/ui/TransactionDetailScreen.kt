package com.wisnu.kurniawan.wallee.features.transaction.detail.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgHeaderEditMode
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgPageLayout

@Composable
fun TransactionDetailScreen(
    navController: NavController
) {
    PgPageLayout(
        Modifier
            .fillMaxSize()
    ) {
        PgHeaderEditMode(
            isAllowToSave = true,
            onCancelClick = {
                navController.navigateUp()
            },
            onSaveClick = {},
            title = {
                Text(
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                    text = "Add transaction"
                )
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                Text("Hello")
            }
        }
    }
}

