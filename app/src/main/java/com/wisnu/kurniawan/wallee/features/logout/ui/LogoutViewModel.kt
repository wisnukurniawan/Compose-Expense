package com.wisnu.kurniawan.wallee.features.logout.ui

import androidx.lifecycle.viewModelScope
import com.wisnu.foundation.coreviewmodel.StatefulViewModel
import com.wisnu.kurniawan.wallee.features.logout.data.ILogoutEnvironment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class LogoutViewModel @Inject constructor(logoutEnvironment: ILogoutEnvironment) :
    StatefulViewModel<LogoutState, LogoutEffect, LogoutAction, ILogoutEnvironment>(LogoutState(), logoutEnvironment) {

    init {
        initUser()
    }

    override fun dispatch(action: LogoutAction) {
        when (action) {
            is LogoutAction.ClickLogout -> logout()
        }
    }

    private fun logout() {
        viewModelScope.launch {
            environment.logout()
            setEffect(LogoutEffect.NavigateToSplash)
        }
    }

    private fun initUser() {
        viewModelScope.launch {
            environment.getUser()
                .collect { setState { copy(user = it) } }
        }
    }

}
