package com.podraza.android.gaogao.gaogao;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.podraza.android.gaogao.gaogao.data.DataContract;

/**
 * Created by adampodraza on 3/11/16.
 */
public class TodoCursorAdapter extends CursorAdapter {
    private final String LOG_TAG = this.getClass().getSimpleName();

    private int flags;

    public TodoCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        this.flags = flags;
    }

    public static class ViewHolder {
        public final TextView tv;
        public final CheckBox cb;

        public ViewHolder(View view) {
            this.tv = (TextView) view.findViewById(R.id.todo_description);

            this.cb = (CheckBox) view.findViewById(R.id.checkBox);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        Log.d(LOG_TAG, "newView");

        View view = LayoutInflater.from(context).inflate(R.layout.dog_todo_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        view.setTag(viewHolder);

        //viewHolder.tv.setText(cursor.getString(cursor.getColumnIndex(DataContract.TodoEntry.COLUMN_DESCRIPTION)));
        //viewHolder.cb.setChecked(cursor.getInt(cursor.getColumnIndex(DataContract.TodoEntry.COLUMN_DONE)) > 0);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Log.d(LOG_TAG, "bindView");

        final ViewHolder viewHolder = (ViewHolder) view.getTag();
        final Uri todoUri = DataContract.TodoEntry.buildDataUri(cursor.getLong(cursor.getColumnIndex(DataContract.TodoEntry.COLUMN_DOG_ID)));
        Log.d(LOG_TAG, "Cursor is closed " + cursor.isClosed());


        final long id = cursor.getLong(cursor.getColumnIndex(DataContract.TodoEntry._id));

        final String description = cursor.getString(cursor.getColumnIndex(DataContract.TodoEntry.COLUMN_DESCRIPTION));

        Log.d(LOG_TAG, "description is " + description);

        mCursor = cursor;

        //Log.d(LOG_TAG, "column done is " + cursor.getColumnIndex(DataContract.TodoEntry.COLUMN_DONE));

        final ContentValues values = new ContentValues();
        viewHolder.tv.setText(description);
        viewHolder.cb.setChecked(cursor.getInt(cursor.getColumnIndex(DataContract.TodoEntry.COLUMN_DONE)) > 0);

        viewHolder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!viewHolder.cb.isChecked()) {
                    values.put(DataContract.TodoEntry.COLUMN_DONE, false);
                    mContext.getContentResolver().update(DataContract.TodoEntry.buildDataUri(id), values, null, null);

                    viewHolder.cb.setChecked(false);



                } else {
                    values.put(DataContract.TodoEntry.COLUMN_DONE, true);
                    mContext.getContentResolver().update(DataContract.TodoEntry.buildDataUri(id), values, null, null);

                    viewHolder.cb.setChecked(true);

                }
            }
        });



        //launches edit activity
        viewHolder.tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                long dogId = mCursor.getLong(mCursor.getColumnIndex(DataContract.TodoEntry.COLUMN_DOG_ID));
                boolean done = mCursor.getInt(mCursor.getColumnIndex(DataContract.TodoEntry.COLUMN_DONE)) > 0;



                ParcelableTodo todo = new ParcelableTodo(id, description, done, dogId);

                Intent intent = new Intent(mContext, EditTodoActivity.class);

                intent.putExtra(Intent.EXTRA_TEXT, todo.getTodo());
                intent.putExtra(Utility.position, mCursor.getPosition());
                intent.putExtra(Utility.page, flags);
                intent.putExtra(Utility.todoId, id);

                ((Activity) mContext).startActivityForResult(intent, 0);
                return false;
            }
        });



    }
}
