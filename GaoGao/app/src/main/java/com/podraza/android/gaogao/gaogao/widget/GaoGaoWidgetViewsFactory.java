package com.podraza.android.gaogao.gaogao.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.podraza.android.gaogao.gaogao.R;
import com.podraza.android.gaogao.gaogao.Utility;
import com.podraza.android.gaogao.gaogao.data.DataContract;

/**
 * Created by adampodraza on 5/8/16.
 */
public class GaoGaoWidgetViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private String LOG_TAG = getClass().getSimpleName();

    private Context context;
    private long dogId;
    private Cursor todoCursor;

    public GaoGaoWidgetViewsFactory(Context context, Intent intent) {
        this.context = context;
        this.dogId = intent.getLongExtra(Utility.dogId, 0);
        Log.d(LOG_TAG, "dogId is " + this.dogId);
        todoCursor = context.getContentResolver().query(
                DataContract.TodoEntry.buildDataUri(dogId),
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        todoCursor = context.getContentResolver().query(
                DataContract.TodoEntry.buildDataUri(dogId),
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onDestroy() {
        if(todoCursor != null) {
            todoCursor.close();
            todoCursor = null;
        }

    }

    @Override
    public int getCount() {
        return todoCursor == null ? 0 : todoCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.d(LOG_TAG, "getViewAt");

        if(todoCursor.move(position)) {

            RemoteViews row = new RemoteViews(context.getPackageName(), R.layout.widget_listitem);

            String text = todoCursor.getString(todoCursor.getColumnIndex(DataContract.TodoEntry.COLUMN_DESCRIPTION));
            Log.d(LOG_TAG, "position is " + position);

            Log.d(LOG_TAG, text);

            row.setTextViewText(R.id.widget_todo_description, text);


            return row;
        } else {

            return null;
        }
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
