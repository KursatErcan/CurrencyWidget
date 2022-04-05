package com.kursatercan.currencywidget.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ExchangeApi {
    val api_key:String = "0348a3e825-8ef63f1f23-r9uzsg"
    private val baseUrl:String = "https://api.fastforex.io/"
    private var retrofit:Retrofit? = null

    fun getClient(): Retrofit {
        if(retrofit == null){
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}