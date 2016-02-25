package com.podraza.android.gaogao.gaogao;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A placeholder fragment containing a simple view.
 */
public class EditDogActivityFragment extends Fragment {
    private String LOG_TAG = getClass().getSimpleName();
    private int page;
    private String dogName;

    public EditDogActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_dog, container, false);

        final EditText editName = (EditText) rootView.findViewById(R.id.edit_dog_name);
        Button saveDog = (Button) rootView.findViewById(R.id.save_dog_edit);
        Button deleteDog = (Button) rootView.findViewById(R.id.edit_dog_delete);

        Intent args = getActivity().getIntent();

        String tempDog = args.getStringExtra(Intent.EXTRA_TEXT);



        page = args.getIntExtra(Utility.page, 0);

        //If user is editing an existing item, populates EditText with description
        if(tempDog != null) {
            editName.setText(tempDog);
        }

        saveDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dogName = editName.getText().toString();

                finishActivity(dogName);

            }
        });

        deleteDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity(" ");
            }
        });


        return rootView;
    }

    //Method handles all wrapup tasks and finishes the activity
    private void finishActivity(String extraText) {

            Intent finishIntent = new Intent(getContext(), MainActivity.class);
            finishIntent.putExtra(Intent.EXTRA_TEXT, extraText);
            finishIntent.putExtra(Utility.page, page);
            finishIntent.putExtra(Utility.isDogResult, true);
            getActivity().setResult(Activity.RESULT_OK, finishIntent);
            getActivity().finish();

    }
}
