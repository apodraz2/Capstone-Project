package com.podraza.android.gaogao.gaogao;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.support.design.widget.Snackbar;
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
    private boolean isEditDog;
    private long dogId;

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
        Log.d(LOG_TAG, "page is " + page);
        isEditDog = args.getBooleanExtra(Utility.isEditDog, false);
        dogId = args.getLongExtra(Utility.dogId, 0);

        Log.d(LOG_TAG, "dogId in EditDogActivityFragment is " + dogId);


        //If user is editing an existing item, populates EditText with description
        if(tempDog != null) {
            editName.setText(tempDog);
        }

        saveDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dogName = editName.getText().toString();

                if(!Utility.isNetworkAvailable(getContext())){
                    Snackbar snack = Snackbar.make(v, "Please connect to the internet.", Snackbar.LENGTH_LONG);
                    snack.show();
                    return;
                }

                if(Utility.verifyUserInput(dogName)) {
                    finishActivity(dogName);
                } else {
                    Snackbar snack = Snackbar.make(v, "Dog's name is not acceptable", Snackbar.LENGTH_LONG);
                    snack.show();
                }

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
        Log.d(LOG_TAG, "finishActivity");

        //network call to create
        CallCreateEndpointTask task = new CallCreateEndpointTask(getContext(), true);

        task.execute(dogName);

        Intent finishIntent = new Intent(getContext(), MainActivityFragment.class);
        finishIntent.putExtra(Intent.EXTRA_TEXT, extraText);
        finishIntent.putExtra(Utility.page, page);
        finishIntent.putExtra(Utility.isDogResult, true);
        finishIntent.putExtra(Utility.isEditDog, isEditDog);
        finishIntent.putExtra(Utility.dogId, dogId);
        getActivity().setResult(Activity.RESULT_OK, finishIntent);
        getActivity().finish();

    }
}
