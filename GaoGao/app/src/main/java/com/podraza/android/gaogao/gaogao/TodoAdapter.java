package com.podraza.android.gaogao.gaogao;

import android.content.Context;
import android.os.Parcelable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by adampodraza on 2/16/16.
 */
public class TodoAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ParcelableTodo> todoList;

    TodoAdapter(Context context, ArrayList<ParcelableTodo> todoList) {
        this.context = context;
        this.todoList = todoList;
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

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View adapterView = convertView;

        if(adapterView == null) {
            adapterView = inflater.inflate(R.layout.dog_todo_list_item, null);

        }

        TextView textView = (TextView) adapterView.findViewById(R.id.todo_description);

        textView.setText(todoList.get(position).getTodo());

        return adapterView;
    }
}
