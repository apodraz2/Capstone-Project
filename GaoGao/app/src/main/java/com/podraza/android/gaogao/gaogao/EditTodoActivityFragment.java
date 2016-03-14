package com.podraza.android.gaogao.gaogao;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
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
    private int page;

    public EditTodoActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_edit_todo, container, false);

        Log.d(LOG_TAG, "onCreateView");

        final EditText editTodo = (EditText) rootView.findViewById(R.id.edit_todo_description);
        Button saveEditButton = (Button) rootView.findViewById(R.id.save_todo_edit);
        Button deleteTodoButton = (Button) rootView.findViewById(R.id.edit_todo_delete);

        Intent args = getActivity().getIntent();

        Log.d(LOG_TAG, "args is null " + (args == null));

        String tempTodo = args.getStringExtra(Intent.EXTRA_TEXT);


        position = args.getIntExtra(Utility.position, 100);
        page = args.getIntExtra(Utility.page, 0);

        //If user is editing an existing item, populates EditText with description
        if(tempTodo != null) {
            editTodo.setText(tempTodo);
        }

        saveEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "save button pressed");
                todoDesc = editTodo.getText().toString();
                if(Utility.verifyUserInput(todoDesc)) {

                    if (position == 100) {

                        finishActivity(todoDesc, position, false);

                    } else {

                        finishActivity(todoDesc, position, true);

                    }
                } else {
                    Snackbar snack = Snackbar.make(v, "Todo was not acceptable", Snackbar.LENGTH_LONG);
                    snack.show();
                }

            }
        });

        deleteTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity(Utility.emptyString, position, true);
            }
        });

        return rootView;
    }

    //Method handles all wrapup tasks and finishes the activity
    private void finishActivity(String extraText, int position, boolean positionNeeded) {

        Log.d(LOG_TAG, "finishActivity");

        if(positionNeeded) {
            Log.d(LOG_TAG, "positionNeeded");
            Intent finishIntent = new Intent(getContext(), MainActivity.class);
            finishIntent.putExtra(Intent.EXTRA_TEXT, extraText);
            finishIntent.putExtra(Utility.position, position);
            finishIntent.putExtra(Utility.page, page);
            finishIntent.putExtra(Utility.isDogResult, false);
            getActivity().setResult(Activity.RESULT_OK, finishIntent);
            getActivity().finish();
        } else {
            Log.d(LOG_TAG, "position not needed");
            Intent finishIntent = new Intent(getContext(), MainActivity.class);
            finishIntent.putExtra(Intent.EXTRA_TEXT, extraText);
            finishIntent.putExtra(Utility.page, page);
            finishIntent.putExtra(Utility.isDogResult, false);
            getActivity().setResult(Activity.RESULT_OK, finishIntent);
            getActivity().finish();
        }
    }

}
