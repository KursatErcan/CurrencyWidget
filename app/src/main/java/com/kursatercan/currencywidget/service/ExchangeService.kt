package com.kursatercan.currencywidget.service

import com.kursatercan.currencywidget.api.ExchangeApi
import com.kursatercan.currencywidget.model.ExchangeRateResultModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeService {

    //https://api.fastforex.io/fetch-multi?from=TRY&to=USD%2CEUR&api_key=0348a3e825-8ef63f1f23-r9uzsg

    @GET("fetch-multi?")
    fun fetchMulti(@Query("from") from:String,
                  @Query("to") to:String,
                  @Query("api_key") api_key:String = ExchangeApi.api_key
                  ): Call<ExchangeRateResultModel>
}