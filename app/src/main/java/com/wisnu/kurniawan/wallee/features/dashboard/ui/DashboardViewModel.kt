package com.wisnu.kurniawan.wallee.features.dashboard.ui

import androidx.lifecycle.viewModelScope
import com.wisnu.kurniawan.wallee.features.dashboard.data.IDashboardEnvironment
import com.wisnu.kurniawan.wallee.foundation.viewmodel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class DashboardViewModel @Inject constructor(
    dashboardEnvironment: IDashboardEnvironment,
) :
    StatefulViewModel<DashboardState, Unit, Unit, IDashboardEnvironment>(DashboardState(), Unit, dashboardEnvironment) {

    init {
        initUser()
    }

    private fun initUser() {
        viewModelScope.launch {
            environment.getUser()
                .collect { setState { copy(user = it) } }
        }
    }

    override fun dispatch(action: Unit) {

    }
}
