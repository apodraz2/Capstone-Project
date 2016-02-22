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

        final EditText editTodo = (EditText) rootView.findViewById(R.id.edit_todo_description);
        Button saveEditButton = (Button) rootView.findViewById(R.id.save_todo_edit);
        Button deleteTodoButton = (Button) rootView.findViewById(R.id.edit_todo_delete);

        Intent args = getActivity().getIntent();

        Log.d(LOG_TAG, "args is null " + (args == null));

        String tempTodo = args.getStringExtra(Intent.EXTRA_TEXT);


        position = args.getIntExtra("position", 100);

        //If user is editing an existing item, populates EditText with description
        if(tempTodo != null) {
            editTodo.setText(tempTodo);
        }

        saveEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todoDesc = editTodo.getText().toString();

                if(position == 100) {

                    finishActivity(todoDesc, position, false);

                } else {

                    finishActivity(todoDesc, position, true);

                }

            }
        });

        deleteTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity(" ", position, true);
            }
        });

        return rootView;
    }

    //Method handles all wrapup tasks and finishes the activity
    private void finishActivity(String extraText, int position, boolean positionNeeded) {
        if(positionNeeded) {
            Intent finishIntent = new Intent(getContext(), MainActivity.class);
            finishIntent.putExtra(Intent.EXTRA_TEXT, extraText);
            finishIntent.putExtra("position", position);
            getActivity().setResult(Activity.RESULT_OK, finishIntent);
            getActivity().finish();
        } else {
            Intent finishIntent = new Intent(getContext(), MainActivity.class);
            finishIntent.putExtra(Intent.EXTRA_TEXT, extraText);
            getActivity().setResult(Activity.RESULT_OK, finishIntent);
            getActivity().finish();
        }
    }

}
