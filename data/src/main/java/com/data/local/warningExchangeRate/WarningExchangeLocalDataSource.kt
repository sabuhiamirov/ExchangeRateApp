package com.data.local.warningExchangeRate

import com.domain.dto.WarningExchangeSavedLocalDto


interface WarningExchangeLocalDataSource {

    val warningExchangeSavedLocalDto: List<WarningExchangeSavedLocalDto>

    suspend fun insertWarningExchangeCurrency(warningExchangeSavedLocalDto: WarningExchangeSavedLocalDto)
}