package com.wisnu.kurniawan.wallee.foundation.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wisnu.foundation.coreviewmodel.StatefulViewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun <STATE, EFFECT, ACTION, ENVIRONMENT> HandleEffect(
    viewModel: StatefulViewModel<STATE, EFFECT, ACTION, ENVIRONMENT>,
    handle: (EFFECT) -> Unit
) {
    val effect by viewModel.effect.collectAsStateWithLifecycle()
    LaunchedEffect(effect) {
        effect?.let {
            handle(it)
            viewModel.resetEffect()
        }
    }
}
