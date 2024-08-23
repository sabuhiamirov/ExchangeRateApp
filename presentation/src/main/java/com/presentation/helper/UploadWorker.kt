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

        println("doWorker 1 ${dataBaseList.size}")

        for (warningExchangeSavedLocalDto in dataBaseList) {

            println("Test do work for 2")

            getExchangeCurrencyRemoteUseCase.execute(
                params = GetExchangeCurrencyRemoteUseCase.Params(
                    currentCurrency = warningExchangeSavedLocalDto.currentCurrency ?: "",
                    exchangeCurrency = warningExchangeSavedLocalDto.exchangeCurrency ?: "",
                    amount = warningExchangeSavedLocalDto.currentCurrencyAmount ?: 0.0
                )
            ) {
                onSuccess = {
                    println("Test do work 3")
                    it?.let { convertedAmount ->
                        nowExchangeCurrencyAmount = convertedAmount.toDouble()
                    }
                }
            }

            println("now amount ${nowExchangeCurrencyAmount}")


            return if (nowExchangeCurrencyAmount > (warningExchangeSavedLocalDto.maxAmount
                    ?: 0.0) || nowExchangeCurrencyAmount > (warningExchangeSavedLocalDto.maxAmount
                    ?: 0.0)
            ) {
                println("Test do work 4")
                activity?.let {
                    println("Test do work 5")
                    AlertNotificationService.sendNotification(it, "Başlık", "Mesaj içeriği")
                }
                Result.success()
            } else {
                println("else")
                Result.failure()
            }

        }

        return Result.failure()
    }
}