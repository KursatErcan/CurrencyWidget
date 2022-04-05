package com.kursatercan.currencywidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent

private var id = 999

class CurrencyWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        appWidgetIds?.let { ids ->
            ids.forEach { appWidgetId ->
                CurrencyWidgetUpdateWorker.updateWidget(context!!, appWidgetId)
                id = appWidgetId
            }
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        if (MY_BUTTTON_START == intent!!.action){
            println(id)
            CurrencyWidgetUpdateWorker.manuelUpdateWidget(context!!, id)
        }
    }

}