package com.presentation.helper

import android.app.Activity
import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.domain.dto.WarningExchangeSavedLocalDto
import com.domain.usecase.GetExchangeCurrencyRemoteUseCase
import com.domain.usecase.RunSavedExchangeRateLocalUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


@HiltWorker
class UploadWorker @AssistedInject constructor(
    private val runSavedExchangeRateLocalUseCase: RunSavedExchangeRateLocalUseCase,
    private val getExchangeCurrencyRemoteUseCase: GetExchangeCurrencyRemoteUseCase,
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {


    var nowExchangeCurrencyAmount: Double = 0.0
    var dataBaseList: List<WarningExchangeSavedLocalDto> = listOf()
    val activity = appContext as? Activity

    override suspend fun doWork(): Result {

        runSavedExchangeRateLocalUseCase.execute(Unit) {
            onSuccess = {
                it?.let { list ->
                    dataBaseList = list
                }
            }
        }

        for (warningExchangeSavedLocalDto in dataBaseList) {

            getExchangeCurrencyRemoteUseCase.execute(
                params = GetExchangeCurrencyRemoteUseCase.Params(
                    currentCurrency = warningExchangeSavedLocalDto.currentCurrency ?: "",
                    exchangeCurrency = warningExchangeSavedLocalDto.exchangeCurrency ?: "",
                    amount = warningExchangeSavedLocalDto.currentCurrencyAmount ?: 0.0
                )
            ) {
                onSuccess = {
                    it?.let { convertedAmount ->
                        nowExchangeCurrencyAmount = convertedAmount.toDouble()
                    }
                }
            }

            if (nowExchangeCurrencyAmount > (warningExchangeSavedLocalDto.maxAmount
                    ?: 0.0) || nowExchangeCurrencyAmount > (warningExchangeSavedLocalDto.maxAmount
                    ?: 0.0)
            ) {
                activity?.let {
                    AlertNotificationService.sendNotification(
                        it, "Rate limit", "limit exceeded:" +
                                "New Rate Amount ${nowExchangeCurrencyAmount}" +
                                "Min Amount ${warningExchangeSavedLocalDto.minAmount}" +
                                "Min Amount ${warningExchangeSavedLocalDto.maxAmount}"
                    )

                }
            }

        }

        return Result.success()
    }
}