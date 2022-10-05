package com.wisnu.kurniawan.wallee.features.host.ui

import androidx.lifecycle.viewModelScope
import com.wisnu.foundation.coreviewmodel.StatefulViewModel
import com.wisnu.kurniawan.wallee.features.host.data.IHostEnvironment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class HostViewModel @Inject constructor(hostEnvironment: IHostEnvironment) :
    StatefulViewModel<HostState, Unit, Unit, IHostEnvironment>(HostState(), hostEnvironment) {

    init {
        initTheme()
    }

    override fun dispatch(action: Unit) {

    }

    private fun initTheme() {
        viewModelScope.launch {
            environment.getTheme()
                .collect { setState { copy(theme = it) } }
        }
    }

}


