package com.podraza.android.gaogao.gaogao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by adampodraza on 2/19/16.
 * Adapter to show todos in a list
 */
public class TodoAdapter extends BaseAdapter {

    private String LOG_TAG = getClass().getSimpleName();

    private Context context;
    private ArrayList<ParcelableTodo> todoList;
    private int page;

    TodoAdapter(Context context, ArrayList<ParcelableTodo> todoList, int page) {

        this.context = context;
        this.todoList = todoList;
        this.page = page;
    }

    @Override
    public int getCount() {
        return todoList.size();
    }

    @Override
    public Object getItem(int position) {
        return todoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int clickPosition = position;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View adapterView = convertView;

        adapterView = inflater.inflate(R.layout.dog_todo_list_item, null);

        TextView textView = (TextView) adapterView.findViewById(R.id.todo_description);

        textView.setText(todoList.get(position).getTodo());

        //launches edit activity
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Intent intent = new Intent(context, EditTodoActivity.class);

                intent.putExtra(Intent.EXTRA_TEXT, todoList.get(clickPosition).getTodo());
                intent.putExtra("position", clickPosition);
                intent.putExtra("page", page);

                ((Activity) context).startActivityForResult(intent, 0);
                return false;
            }
        });

        return adapterView;
    }
}
