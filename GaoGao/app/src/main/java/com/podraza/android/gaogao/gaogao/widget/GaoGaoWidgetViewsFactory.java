package com.podraza.android.gaogao.gaogao.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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

    private Context context;
    private long dogId;
    private Cursor todoCursor;

    public GaoGaoWidgetViewsFactory(Context context, Intent intent) {
        this.context = context;
        this.dogId = intent.getLongExtra(Utility.dogId, 0);
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

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return todoCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        if(todoCursor.move(position)) {

            RemoteViews row = new RemoteViews(context.getPackageName(), R.layout.dog_todo_list_item);

            row.setTextViewText(R.id.todo_description, todoCursor.getString(todoCursor.getColumnIndex(DataContract.TodoEntry.COLUMN_DESCRIPTION)));


        }

        return null;
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
