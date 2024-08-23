package com.presentation.main.warningExchangeRate

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WarningExchangeRateArgs (
    val currencyList : ArrayList<String>
) : Parcelable