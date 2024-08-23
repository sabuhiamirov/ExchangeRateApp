package com.presentation.main.exchangeRate

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.presentation.R
import com.presentation.base.BaseFragment
import com.presentation.databinding.ExchangeRateFragmentBinding
import com.presentation.helper.AlertNotificationService
import com.presentation.main.warningExchangeRate.WarningExchangeRateArgs
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ExchangeRateFragment :
    BaseFragment<ExchangeViewModelState, ExchangeViewModelEffect, ExchangeRateFragmentBinding, ExchangeViewModel>() {

    private val _viewModel: ExchangeViewModel by viewModels()
    override fun getLayoutId(): Int = R.layout.exchange_rate_fragment
    override fun getViewModel(): ExchangeViewModel = _viewModel
    private val service: AlertNotificationService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewModel().getListQuotes()
    }

    override val bindViews: ExchangeRateFragmentBinding.() -> Unit = {

        etvCurrentAmount.focusInput()

        btnConvert.setOnClickListener {
            getViewModel().getExchangeCurrencyAmount(
                currentCurrency = actvCurrentCurrency.text.toString(),
                exchangeCurrency = actvExchangeCurrency.text.toString(),
                amount = etvCurrentAmount.getAmount()

            )
        }

        btnWarning.setOnClickListener {

         findNavController().navigate(
                ExchangeRateFragmentDirections.toWarningExchangeRateFragment(
                    WarningExchangeRateArgs(
                        currencyList = getViewModel().currencyList
                    )
                )
            )
        }

    }

    override fun observeState(state: ExchangeViewModelState) {
        super.observeState(state)

        when (state) {
            is ExchangeViewModelState.GetCurrencyListQuotesSuccess -> {
                context?.let {
                    val arrayAdapter =
                        ArrayAdapter(it, R.layout.item_dropdown, state.currencyListQuotes)
                    getBinding().apply {
                        actvCurrentCurrency.setAdapter(arrayAdapter)
                        actvExchangeCurrency.setAdapter(arrayAdapter)
                    }
                }
            }

            is ExchangeViewModelState.CurrencyConvertSuccess -> {
                getBinding().mtvExchangedAmount.text = state.convertedAmount.toString()
            }
        }

    }

    override fun observeEffect(effect: ExchangeViewModelEffect) {
        super.observeEffect(effect)
        when (effect) {
            is ExchangeViewModelEffect.GetCurrencyListQuotesError -> {

                val snackbar =
                    Snackbar.make(getBinding().root, effect.message, Snackbar.LENGTH_SHORT)
                snackbar.show()
            }

            is ExchangeViewModelEffect.CurrencyConvertedError -> {
                val snackbar =
                    Snackbar.make(getBinding().root, effect.message, Snackbar.LENGTH_SHORT)
                snackbar.show()
            }
        }
    }
}