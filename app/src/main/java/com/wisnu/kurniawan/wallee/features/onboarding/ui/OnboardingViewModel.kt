package com.wisnu.kurniawan.wallee.features.onboarding.ui

import androidx.lifecycle.viewModelScope
import com.wisnu.foundation.coreviewmodel.StatefulViewModel
import com.wisnu.kurniawan.wallee.features.onboarding.data.IOnboardingEnvironment
import com.wisnu.kurniawan.wallee.foundation.currency.COUNTRY_DATA
import com.wisnu.kurniawan.wallee.foundation.currency.CURRENCY_DATA
import com.wisnu.kurniawan.wallee.foundation.emoji.EmojiData
import com.wisnu.kurniawan.wallee.model.Currency
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    onboardingEnvironment: IOnboardingEnvironment
) : StatefulViewModel<OnboardingState, OnboardingEffect, OnboardingAction, IOnboardingEnvironment>(OnboardingState(), onboardingEnvironment) {

    private val isSaveInProgress: MutableStateFlow<Boolean> = MutableStateFlow(false)

    init {
        initLoad()
        initSave()
    }

    private fun initLoad() {
        viewModelScope.launch {
            environment.getCurrentCountryCode()
                .collect {
                    setState { copy(currentCountryCode = it) }
                }
        }
        viewModelScope.launch {
            setState { copy(currencyItems = initialCurrencyItems()) }
        }
    }

    private fun initSave() {
        viewModelScope.launch {
            isSaveInProgress
                .filter { it }
                .distinctUntilChanged { old, new -> old != new }
                .collect {
                    environment.saveAccount(state.value.selectedCurrency!!)
                    setEffect(OnboardingEffect.ClosePage)
                }
        }
    }

    override fun dispatch(action: OnboardingAction) {
        when (action) {
            OnboardingAction.ClickSave -> {
                isSaveInProgress.value = true
            }
            is OnboardingAction.SelectCurrency -> {
                viewModelScope.launch {
                    setState { copy(selectedCurrency = action.item) }
                }
            }
        }
    }

    private fun initialCurrencyItems(): List<CurrencyItem> {
        return CURRENCY_DATA.flatMap { (key, value) ->
            value.countryCodes.map {
                val countryName = COUNTRY_DATA[it]?.name.orEmpty()
                val flagKey = countryName.lowercase().replace(" ", "_")
                val flag = EmojiData.DATA[flagKey] ?: "🏳️"

                CurrencyItem(
                    currencySymbol = value.symbol,
                    flag = flag,
                    countryName = countryName,
                    countryCode = it,
                    currency = Currency(
                        key,
                        it
                    )
                )
            }
        }.sortedBy { it.countryName }
    }

}
