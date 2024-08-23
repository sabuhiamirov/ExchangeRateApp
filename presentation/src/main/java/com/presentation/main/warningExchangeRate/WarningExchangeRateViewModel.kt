package com.presentation.main.warningExchangeRate

import com.domain.dto.WarningExchangeSavedLocalDto
import com.domain.usecase.SavedExchangeRateLocalUseCase
import com.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class WarningExchangeRateViewModel @Inject constructor(
    private val savedExchangeRateLocalUseCase: SavedExchangeRateLocalUseCase,
) : BaseViewModel<WarningExchangeViewModelState, WarningExchangeViewModelEffect>() {

    fun saveWarningExchangeRate(

        warningExchangeSavedLocalDto: WarningExchangeSavedLocalDto
    ) {

        savedExchangeRateLocalUseCase.launch(
            param = SavedExchangeRateLocalUseCase.Params(
                warningExchangeList = warningExchangeSavedLocalDto
            )
        ) {
            onSuccess = {
                println("On Success")
                postEffect(WarningExchangeViewModelEffect.GetCurrencyListQuotesError)
            }
        }
    }

}

sealed class WarningExchangeViewModelState {}
sealed class WarningExchangeViewModelEffect {
    object GetCurrencyListQuotesError : WarningExchangeViewModelEffect()
}