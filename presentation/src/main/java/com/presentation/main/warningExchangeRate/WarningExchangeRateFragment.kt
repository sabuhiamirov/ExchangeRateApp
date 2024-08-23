package com.presentation.main.warningExchangeRate

import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.domain.dto.WarningExchangeSavedLocalDto
import com.presentation.R
import com.presentation.base.BaseFragment
import com.presentation.databinding.WarningExchangeRateFragmentBinding
import com.presentation.helper.UploadWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class WarningExchangeRateFragment :
    BaseFragment<WarningExchangeViewModelState, WarningExchangeViewModelEffect, WarningExchangeRateFragmentBinding, WarningExchangeRateViewModel>() {

    private val _viewModel: WarningExchangeRateViewModel by viewModels()
    override fun getLayoutId(): Int = R.layout.warning_exchange_rate_fragment
    override fun getViewModel(): WarningExchangeRateViewModel = _viewModel

    private val args by navArgs<WarningExchangeRateFragmentArgs>()

    override val bindViews: WarningExchangeRateFragmentBinding.() -> Unit = {


        val arrayAdapter =
            ArrayAdapter(requireContext(), R.layout.item_dropdown, args.argument.currencyList)

        actvCurrentCurrency.setAdapter(arrayAdapter)
        actvWarningCurrency.setAdapter(arrayAdapter)

        btnSave.setOnClickListener {
            getViewModel().saveWarningExchangeRate(
                WarningExchangeSavedLocalDto(
                    currentCurrency = actvCurrentCurrency.text.toString(),
                    exchangeCurrency = actvWarningCurrency.text.toString(),
                    minAmount = etvMinAmount.getAmount(),
                    maxAmount = etvMaxAmount.getAmount(),
                    currentCurrencyAmount = etvCurrentAmount.getAmount()
                )
            )
        }
    }


    override fun observeEffect(effect: WarningExchangeViewModelEffect) {
        super.observeEffect(effect)
        when (effect) {
            WarningExchangeViewModelEffect.GetCurrencyListQuotesError -> {

                val constraints = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresCharging(false)
                    .build()


                val myWorkRequest: PeriodicWorkRequest = PeriodicWorkRequestBuilder<UploadWorker>(
                    15,
                    TimeUnit.MINUTES
                )
                    .setConstraints(constraints)
                    .build()

                WorkManager.getInstance(requireActivity()).enqueue(myWorkRequest)

            }
        }
    }

}