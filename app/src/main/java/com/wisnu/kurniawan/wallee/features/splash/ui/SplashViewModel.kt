package com.wisnu.kurniawan.wallee.features.splash.ui

import androidx.lifecycle.viewModelScope
import com.wisnu.foundation.coreviewmodel.StatefulViewModel
import com.wisnu.kurniawan.wallee.features.splash.data.ISplashEnvironment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SplashViewModel @Inject constructor(
    splashEnvironment: ISplashEnvironment
) : StatefulViewModel<Unit, SplashEffect, SplashAction, ISplashEnvironment>(Unit, splashEnvironment) {

    init {
        dispatch(SplashAction.AppLaunch)
    }

    override fun dispatch(action: SplashAction) {
        when (action) {
            is SplashAction.AppLaunch -> {
                viewModelScope.launch {
//                    environment.getCredential()
//                        .collect {
                            // if (it.isLoggedIn()) {
//                            setEffect(SplashEffect.NavigateToDashboard)
//                            } else {
//                                setEffect(SplashEffect.NavigateToLogin)
//                            }
//                        }

                    environment.hasFinishOnboarding()
                        .collect {
                            if (it) {
                                setEffect(SplashEffect.NavigateToDashboard)
                            } else {
                                setEffect(SplashEffect.NavigateToOnboarding)
                            }
                        }
                }
            }
        }
    }
}

