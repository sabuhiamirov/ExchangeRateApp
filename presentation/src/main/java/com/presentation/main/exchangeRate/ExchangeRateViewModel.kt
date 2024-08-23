package com.presentation.main.exchangeRate

import com.domain.usecase.GetExchangeCurrencyRemoteUseCase
import com.domain.usecase.GetListQuotesRemoteUseCase
import com.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val getListQuotesRemoteUseCase: GetListQuotesRemoteUseCase,
    private val getExchangeCurrencyRemoteUseCase: GetExchangeCurrencyRemoteUseCase
) : BaseViewModel<ExchangeViewModelState, ExchangeViewModelEffect>() {

        var currencyList : ArrayList<String> = arrayListOf()


    fun getListQuotes() {

        getListQuotesRemoteUseCase.launch(param = Unit) {

            onSuccess = {
                it?.let{ listQuotes ->
                    currencyList = listQuotes
                    postState(ExchangeViewModelState.GetCurrencyListQuotesSuccess(listQuotes))
                }
            }

            onError = {
                it.let{ messages ->
                    postEffect(ExchangeViewModelEffect.GetCurrencyListQuotesError(messages.message.toString()))
                }
            }

        }
    }

    fun getExchangeCurrencyAmount(
        currentCurrency : String,
        exchangeCurrency : String,
        amount : Double
    ){
        getExchangeCurrencyRemoteUseCase.launch(
            param = GetExchangeCurrencyRemoteUseCase.Params(
                currentCurrency = currentCurrency,
                exchangeCurrency = exchangeCurrency,
                amount = amount
            )
        ){
            onSuccess = {
                it?.let { convertedAmount ->
                    postState(ExchangeViewModelState.CurrencyConvertSuccess(convertedAmount.toDouble()))
                }
            }
            onError = {
                it.let{ messages ->
                    postEffect(ExchangeViewModelEffect.CurrencyConvertedError(messages.message.toString()))
                }
            }

        }
    }
}

sealed class ExchangeViewModelState{
    class GetCurrencyListQuotesSuccess(val currencyListQuotes : ArrayList<String>) : ExchangeViewModelState()
    class CurrencyConvertSuccess(val convertedAmount : Double) : ExchangeViewModelState()
}
sealed class ExchangeViewModelEffect{
    class GetCurrencyListQuotesError(val message : String) : ExchangeViewModelEffect()
    class CurrencyConvertedError(val message : String) : ExchangeViewModelEffect()
}