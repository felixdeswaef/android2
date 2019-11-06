package be.felixdeswaef.reminder;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        //LayoutInflater myInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View myRoot = myInflater.inflate(R.layout.new_app_widget, null);
        //View box = myInflater.inflate(R.layout.widgettask,(LinearLayout) myRoot.findViewById(R.id.lines));

        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.new_app_widget);
        views.setRemoteAdapter(R.id.lines,
                new Intent(context, widgetService.class));


        Intent launchMain = new Intent(context, MainActivity.class);
        PendingIntent pendingMainIntent = PendingIntent.getActivity(context, 0, launchMain, 0);
        views.setOnClickPendingIntent(R.id.widgtitbar, pendingMainIntent);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.lines);
        appWidgetManager.updateAppWidget(appWidgetId, views);


        //views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget



    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

