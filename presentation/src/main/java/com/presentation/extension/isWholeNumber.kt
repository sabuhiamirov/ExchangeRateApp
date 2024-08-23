package com.presentation.extension

fun Double.isWholeNumber(): Boolean {
    return this.rem(1) == 0.0
}

fun Double.formatWithoutRedundantDecimal(): String {
    return if (this.isWholeNumber()) this.toLong().toString()
    else this.toString()
}