package com.podraza.android.gaogao.gaogao;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.podraza.android.gaogao.gaogao.data.DataContract;

/**
 * Implementation of App Widget functionality.
 */
public class GaoGaoWidget extends AppWidgetProvider {
    private static String LOG_TAG = "GaoGaoWidget";

    private static Cursor dogCursor;
    private static Cursor todoCursor;
    private static String userEmail;
    private static long userId;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences("com.podraza.android.gaogao.gaogao", Context.MODE_PRIVATE);

        userEmail = prefs.getString(Utility.userEmail, null);
        userId = prefs.getLong(Utility.userId, -1);
        Log.d(LOG_TAG, "userId is " + userId);


        if(userId >= 0) {
            dogCursor = context.getContentResolver().query(
                    DataContract.DogEntry.buildDataUri(userId),
                    null,
                    null,
                    null,
                    null
            );
            String name = "No dogs yet";
            if(dogCursor.moveToFirst())
                name = dogCursor.getString(dogCursor.getColumnIndex(DataContract.DogEntry.COLUMN_NAME));


            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.gao_gao_widget);
            views.setTextViewText(R.id.widget_dog_name, name);


            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
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

