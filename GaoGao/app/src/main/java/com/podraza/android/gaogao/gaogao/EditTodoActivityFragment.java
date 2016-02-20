package com.podraza.android.gaogao.gaogao;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class EditTodoActivityFragment extends Fragment {
    private String LOG_TAG = getClass().getSimpleName();

    private String todoDesc;

    public EditTodoActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_edit_todo, container, false);

        EditText editTodo = (EditText) rootView.findViewById(R.id.edit_todo_description);

        Bundle args = getArguments();

        if(args != null) {
            todoDesc = args.getString(Intent.EXTRA_TEXT);
            editTodo.setText(todoDesc);
        }

        editTodo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                todoDesc = (String) v.getText();

                return false;
            }
        });

        return rootView;
    }
}
