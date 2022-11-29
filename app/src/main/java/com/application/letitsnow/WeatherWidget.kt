package com.application.letitsnow

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews

class WeatherWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val widgetText = context.getString(R.string.appwidgetTown)
    // Construct the RemoteViews object
    val remoteViews = RemoteViews(context.packageName, R.layout.weather_widget)
    remoteViews.setTextViewText(R.id.appwidgetTemperature, widgetText)


    //2. define intent --> action which will be performed
    /*val intent = Intent(Intent.ACTION_VIEW)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    intent.data = Uri.parse("https://insideandroid.in")

    val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)*/

    //3. set pending intent on view
    //remoteViews.setOnClickPendingIntent(R.id.updateButton, pendingIntent)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, remoteViews)


    /* val remoteViews = RemoteViews(context.getPackageName(), R.layout.widgetlayout).also {
         setTextViewText(R.id.textview_widget_layout, "Updated text")
     }*/
    appWidgetManager.partiallyUpdateAppWidget(appWidgetId, remoteViews)
}