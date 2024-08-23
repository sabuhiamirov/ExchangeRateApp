package com.data.local.warningExchangeRate

import androidx.room.Dao
import androidx.room.Query
import com.data.local.base.BaseDao
import com.domain.dto.WarningExchangeSavedLocalDto


@Dao
interface WarningExchangeDao : BaseDao<WarningExchangeSavedLocalDto> {

    @Query("SELECT * from warning_exchange_table")
    fun getWarningExchangeSaved(): List<WarningExchangeSavedLocalDto>

}