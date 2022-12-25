package com.application.letitsnow

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import com.application.letitsnow.network.NetworkState.Success
import com.application.letitsnow.ui.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class WeatherWidget : AppWidgetProvider() {

    private var sharedPreferences: WeatherSharedPreferences? = null
    private val ACTION_WIDGET_RECEIVER = "ActionReceiverWidget"

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent?) {

        val remoteViews = RemoteViews(context.packageName, R.layout.weather_widget)
        sharedPreferences = WeatherSharedPreferences(context)

        val town = if (sharedPreferences?.getTown().isNullOrEmpty()) "Saint Petersburg"
        else sharedPreferences?.getTown() ?: "Saint Petersburg"

        remoteViews.setTextViewText(
            R.id.appwidgetTown,
            town
        )
        remoteViews.setTextViewText(
            R.id.appwidgetTemperature,
            sharedPreferences?.getTemperature() ?: "-25"
        )

        GlobalScope.launch(Dispatchers.Main) {
            val remoteTemp = networkRequest(town)

            remoteTemp?.let {
                remoteViews.setTextViewText(
                    R.id.appwidgetTemperature,
                    it
                )
            }

            val widget = ComponentName(context, WeatherWidget::class.java)
            AppWidgetManager.getInstance(context).updateAppWidget(widget, remoteViews)
        }

        val widget = ComponentName(context, WeatherWidget::class.java)
        AppWidgetManager.getInstance(context).updateAppWidget(widget, remoteViews)

        super.onReceive(context, intent)
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val remoteViews = RemoteViews(context.packageName, R.layout.weather_widget)
        sharedPreferences = WeatherSharedPreferences(context)

        remoteViews.setTextViewText(
            R.id.appwidgetTown,
            sharedPreferences?.getTown() ?: "Saint Petersburg"
        )
        remoteViews.setTextViewText(
            R.id.appwidgetTemperature,
            sharedPreferences?.getTemperature() ?: "-25"
        )

        val ids = AppWidgetManager.getInstance(context)
            .getAppWidgetIds(ComponentName(context, WeatherWidget::class.java))


        val pendingUpdate = getPendingSelfIntent(context, appWidgetId, ids)

        remoteViews.setOnClickPendingIntent(
            R.id.updateButton,
            pendingUpdate
        )

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
        appWidgetManager.notifyAppWidgetViewDataChanged(ids, R.id.appwidgetTemperature)
    }

    private fun getPendingSelfIntent(context: Context?, appWidgetId: Int, ids: IntArray): PendingIntent? {
        val intent = Intent(context, WeatherWidget::class.java)
        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE

        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)

        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            PendingIntent.FLAG_MUTABLE
        else PendingIntent.FLAG_UPDATE_CURRENT


        Log.i("sasha", "getPendingSelfIntent: ")

        return PendingIntent.getBroadcast(context, appWidgetId, intent, flag)
    }

    private suspend fun networkRequest(town: String): String? {
        return when (val townWeather = MainActivity().getRepository()?.getTownWeather(town)) {
            is Success -> townWeather.data.current.temp_c.toString()
            else -> null
        }
    }
}