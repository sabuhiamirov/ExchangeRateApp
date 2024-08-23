package com.data.local.warningExchangeRate

import com.domain.dto.WarningExchangeSavedLocalDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class WarningExchangeLocalDataSourceImpl(
    private val warningExchangeDao: WarningExchangeDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : WarningExchangeLocalDataSource{

    override val warningExchangeSavedLocalDto: List<WarningExchangeSavedLocalDto>
        get() = warningExchangeDao.getWarningExchangeSaved()

    override suspend fun insertWarningExchangeCurrency(warningExchangeSavedLocalDto: WarningExchangeSavedLocalDto) {
        withContext(ioDispatcher){
            try {
                warningExchangeDao.insert(warningExchangeSavedLocalDto)
            }catch (e : Exception){
                Timber.e(e)
                null
            }
        }
    }
}