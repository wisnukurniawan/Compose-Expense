package com.wisnu.kurniawan.wallee.features.dashboard.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgIcon
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgIconButton
import com.wisnu.kurniawan.wallee.foundation.uicomponent.PgPageLayout
import com.wisnu.kurniawan.wallee.runtime.navigation.SettingFlow

@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: DashboardViewModel,
) {
    val state by viewModel.state.collectAsState()

    DashboardScreen(
        onSettingClick = { navController.navigate(SettingFlow.Root.route) },
        onClickAddTransaction = {}
    )
}

@Composable
private fun DashboardScreen(
    onSettingClick: () -> Unit,
    onClickAddTransaction: () -> Unit,
) {
    PgPageLayout {
        // Header
        Row(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PgIconButton(onClick = onSettingClick, color = Color.Transparent) {
                PgIcon(imageVector = Icons.Rounded.Menu)
            }

            PgIconButton(onClick = onClickAddTransaction, color = Color.Transparent) {
                PgIcon(imageVector = Icons.Rounded.Add)
            }
        }
    }
}

