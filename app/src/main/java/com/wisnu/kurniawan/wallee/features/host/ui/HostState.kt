package com.wisnu.kurniawan.wallee.features.host.ui

import com.wisnu.kurniawan.wallee.model.Theme
import javax.annotation.concurrent.Immutable

@Immutable
data class HostState(val theme: Theme = Theme.SYSTEM)
