package com.data.local.warningExchangeRate

import androidx.room.Database
import androidx.room.RoomDatabase
import com.domain.dto.WarningExchangeSavedLocalDto


@Database(
    entities = [WarningExchangeSavedLocalDto::class],
    version = 1,
    exportSchema = false
)
abstract class WarningExchangeDatabase : RoomDatabase() {
    abstract fun warningExchangeDao(): WarningExchangeDao
}