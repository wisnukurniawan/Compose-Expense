package com.wisnu.kurniawan.wallee.features.setting.ui

import com.wisnu.kurniawan.wallee.foundation.viewmodel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor() :
    StatefulViewModel<SettingState, Unit, Unit, Unit>(SettingState(), Unit, Unit) {

    override fun dispatch(action: Unit) {
    }

}
