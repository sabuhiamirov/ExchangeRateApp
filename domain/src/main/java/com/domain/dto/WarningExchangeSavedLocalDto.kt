package com.domain.dto

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "warning_exchange_table")
class WarningExchangeSavedLocalDto(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null,

    @Nullable
    @ColumnInfo(name = "currentCurrency")
    var currentCurrency: String? = null,

    @Nullable
    @ColumnInfo(name = "exchangeCurrency")
    var exchangeCurrency: String? = null,

    @Nullable
    @ColumnInfo(name = "minAmount")
    var minAmount: Double? = null,

    @Nullable
    @ColumnInfo(name = "maxAmount")
    var maxAmount: Double? = null,

    @Nullable
    @ColumnInfo(name = "currentCurrencyAmount")
    var currentCurrencyAmount: Double? = null

)