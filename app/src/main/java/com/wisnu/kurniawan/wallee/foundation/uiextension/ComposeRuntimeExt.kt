package com.wisnu.kurniawan.wallee.foundation.uiextension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
fun <T : R, R> Flow<T>.collectAsEffect(): State<R?> = collectAsState(initial = null)

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun <T> Flow<T>.collectAsEffectWithLifecycle(): State<T?> = collectAsStateWithLifecycle(initialValue = null)
