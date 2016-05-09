package com.podraza.android.gaogao.gaogao.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.podraza.android.gaogao.gaogao.LoginActivity;
import com.podraza.android.gaogao.gaogao.R;
import com.podraza.android.gaogao.gaogao.Utility;
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
    private static long dogId;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences("com.podraza.android.gaogao.gaogao", Context.MODE_PRIVATE);

        userEmail = prefs.getString(Utility.userEmail, null);
        userId = prefs.getLong(Utility.userId, -1);
        Log.d(LOG_TAG, "userId is " + userId);

        String name = "No dogs yet";
        if(userId >= 0) {
            dogCursor = context.getContentResolver().query(
                    DataContract.DogEntry.buildDataUri(userId),
                    null,
                    null,
                    null,
                    null
            );


            if (dogCursor.moveToFirst()) {
                name = dogCursor.getString(dogCursor.getColumnIndex(DataContract.DogEntry.COLUMN_NAME));
                dogId = dogCursor.getLong(dogCursor.getColumnIndex(DataContract.DogEntry._id));
                Log.d(LOG_TAG, "dogId is " + dogId);
            }
        }


            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.gao_gao_widget);
            views.setTextViewText(R.id.widget_dog_name, name);

            Intent serviceIntent = new Intent(context, GaoGaoWidgetService.class);
            serviceIntent.putExtra(Utility.dogId, dogId);


            views.setRemoteAdapter(R.id.widget_listview, serviceIntent);

            Intent intent = new Intent(context, LoginActivity.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            views.setOnClickPendingIntent(R.id.widget_dog_name, pendingIntent);


            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);

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

