package com.data.utils

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.math.BigDecimal
import java.math.RoundingMode

class BigDecimalDefaultScaleDeserializer : JsonDeserializer<BigDecimal> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): BigDecimal {
        val value = json?.asBigDecimal
        return value?.setScale(2, RoundingMode.UP) ?: BigDecimal.ZERO
    }
}