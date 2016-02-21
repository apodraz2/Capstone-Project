package com.podraza.android.gaogao.gaogao;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class EditTodoActivityFragment extends Fragment {
    private String LOG_TAG = getClass().getSimpleName();


    private String todoDesc;
    private int position;

    public EditTodoActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_edit_todo, container, false);

        EditText editTodo = (EditText) rootView.findViewById(R.id.edit_todo_description);
        Button saveEditButton = (Button) rootView.findViewById(R.id.save_todo_edit);

        Intent args = getActivity().getIntent();

        Log.d(LOG_TAG, "args is null " + (args == null));

        todoDesc = args.getStringExtra(Intent.EXTRA_TEXT);


        position = args.getIntExtra("position", 100);

        if(todoDesc != null) {
            editTodo.setText(todoDesc);
        }


        editTodo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                todoDesc = (String) v.getText();

                return false;
            }
        });

        saveEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position == 100) {
                    Intent finishIntent = new Intent(getContext(), MainActivity.class);
                    finishIntent.putExtra(Intent.EXTRA_TEXT, todoDesc);
                    startActivity(finishIntent);

                } else {
                    Intent finishIntent = new Intent(getContext(), MainActivity.class);
                    finishIntent.putExtra(Intent.EXTRA_TEXT, todoDesc);
                    finishIntent.putExtra("position", position);
                    startActivity(finishIntent);
                }



            }
        });

        return rootView;
    }
}
