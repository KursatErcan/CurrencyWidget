package com.kursatercan.currencywidget

import android.appwidget.AppWidgetManager
import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

class CurrencyWidgetUpdateWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {

        val widgetId = inputData.getInt(
            INPUT_DATA_WIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        )

        require(widgetId != AppWidgetManager.INVALID_APPWIDGET_ID)

        updateWidget(
            applicationContext,
            AppWidgetManager.getInstance(applicationContext),
            arrayOf(widgetId).toIntArray()
        )

        return Result.success()
    }

    companion object {
        private const val INPUT_DATA_WIDGET_ID =
            "com.metehanbolat.currencywidgetapp.inputdata.widget_id"

        fun updateWidget(context: Context, widgetId: Int) {

            val data: Data = Data.Builder()
                .putInt(INPUT_DATA_WIDGET_ID, widgetId)
                .build()
            /*
            val request = OneTimeWorkRequestBuilder<CurrencyWidgetUpdateWorker>()
                .setInputData(data)
                .setInitialDelay(3, TimeUnit.SECONDS)
                .build()

            WorkManager.getInstance(context)
                .enqueueUniqueWork(uniqueWorkName(widgetId), ExistingWorkPolicy.REPLACE, request)
            */

            // Periodic kullanacaksak 15 dakikadan az olmuyor */
            val request = PeriodicWorkRequestBuilder<CurrencyWidgetUpdateWorker>(15L, TimeUnit.MILLISECONDS)
                .setInputData(data)
                .build()

            WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork(uniqueWorkName(widgetId), ExistingPeriodicWorkPolicy.REPLACE, request)


        }

        fun manuelUpdateWidget(context: Context, widgetId: Int) {
            val data: Data = Data.Builder()
                .putInt(INPUT_DATA_WIDGET_ID, widgetId)
                .build()

            val request = OneTimeWorkRequestBuilder<CurrencyWidgetUpdateWorker>()
                .setInputData(data)
                .setInitialDelay(100, TimeUnit.MILLISECONDS)
                .build()

            WorkManager.getInstance(context)
                .enqueueUniqueWork(uniqueWorkName(widgetId), ExistingWorkPolicy.REPLACE, request)
        }

        private fun uniqueWorkName(widgetId: Int): String {
            return "widget_update_$widgetId"
        }
    }
}