package com.kursatercan.currencywidget.model


import com.google.gson.annotations.SerializedName

data class ExchangeRateResultModel(
    @SerializedName("base")
    val base: String,
    @SerializedName("ms")
    val ms: Int,
    @SerializedName("results")
    val results: Results,
    @SerializedName("updated")
    val updated: String
)

data class Results(
    @SerializedName("EUR")
    val eUR: Double,
    @SerializedName("USD")
    val uSD: Double
)