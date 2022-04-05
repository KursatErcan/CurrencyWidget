package com.kursatercan.currencywidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.kursatercan.currencywidget.api.ExchangeApi
import com.kursatercan.currencywidget.model.ExchangeRateResultModel
import com.kursatercan.currencywidget.service.ExchangeService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

val MY_BUTTTON_START = "myButtonStart"


fun updateWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetIds: IntArray?
) {
    val service = ExchangeApi.getClient().create(ExchangeService::class.java)

    val call = service.fetchMulti("TRY","USD,EUR")
    call.enqueue(object : Callback<ExchangeRateResultModel> {
        override fun onResponse(
            call: Call<ExchangeRateResultModel>,
            response: Response<ExchangeRateResultModel>
        ) {
            if(response.isSuccessful){
                val resultData : ExchangeRateResultModel? = response.body()
                val remoteViews = getRemoteViews(context,appWidgetIds,resultData)
                appWidgetManager.partiallyUpdateAppWidget(appWidgetIds,remoteViews)

                Log.d("response",resultData.toString())
            }else{
                //TODO : NETWORK ERROR CHECK - BASE REPOSITORY'DE ORNEK VAR
            }
        }

        override fun onFailure(call: Call<ExchangeRateResultModel>, t: Throwable) {
            Log.d("onFailureApiCall","t")
        }

    })
    //text.changeString()
    //appWidgetManager.updateAppWidget(appWidgetIds, remoteViews)
}
    fun getRemoteViews(context: Context, appWidgetIds: IntArray? ,result: ExchangeRateResultModel?)=RemoteViews(context.packageName, R.layout.currency_widget).apply {
        setTextViewText(R.id.usdAlis, result?.results?.uSD?.toFloat().toString())
        setTextViewText(R.id.eurAlis, result?.results?.eUR.toString())
        //setTextViewText(R.id.altinAlis, result?.results?.uSD.toString())
        setTextViewText(R.id.lastUpdate,result?.updated)
        //setTextViewText(R.id.ms,result?.ms.toString())

        val intent = Intent(context, CurrencyWidget::class.java)
        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds)
        PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        setOnClickPendingIntent(R.id.btn_refresh, getPendingSelfIntent(context, MY_BUTTTON_START, CurrencyWidget::class.java))
    }

fun getPendingSelfIntent(context: Context?, action: String?, widgetClass: Class<CurrencyWidget>): PendingIntent? {
    val intent = Intent(context, widgetClass)
    intent.action = action
    return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
}