package com.podraza.android.gaogao.gaogao;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.podraza.android.gaogao.gaogao.data.DataContract;

/**
 * Created by adampodraza on 4/12/16.
 */
public class DogFragment extends Fragment {
    private final String LOG_TAG = this.getClass().getSimpleName();



    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    static final String ARG_SECTION_NUMBER = "section_number";

    static final String ARG_SECTION_TITLE = "section_title";

    static final String ARG_DOG_ID = "dog_id";

    private String sectionTitle = "No Dog";
    private int sectionNumber;

    private Cursor todoCursor;

    private long dogId;

    private static TodoCursorAdapter mTodoAdapter;


    public DogFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER) - 1;

        ListView todoView = (ListView) rootView.findViewById(R.id.todo_listview);

        AdView mAdView = (AdView) rootView.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);


        TextView dogName = (TextView) rootView.findViewById(R.id.current_dog);
        sectionTitle = getArguments().getCharSequence(ARG_SECTION_TITLE).toString();


        Log.d(LOG_TAG, "dog name is " + sectionTitle);

        dogName.setText(sectionTitle);

        dogId = getArguments().getLong(ARG_DOG_ID);

        todoCursor = getActivity().getContentResolver().query(
                DataContract.TodoEntry.buildDataUri(dogId),
                null,
                null,
                null,
                null
        );

        mTodoAdapter = new TodoCursorAdapter(getContext(), todoCursor, 0);

        todoView.setAdapter(mTodoAdapter);


        /**
         * Floating action button launches a new edit activity
         */
        final FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu menu = new PopupMenu(getActivity(), fab);
                menu.inflate(R.menu.popup_menu);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.launch_new_dog) {
                            Intent intent = new Intent(getActivity(), EditDogActivity.class);

                            intent.putExtra(Utility.page, sectionNumber + 1);

                            getActivity().startActivityForResult(intent, 0);
                        } else {
                            Intent intent = new Intent(getActivity().getApplicationContext(), EditTodoActivity.class);

                            intent.putExtra(Utility.page, sectionNumber);

                            startActivityForResult(intent, 0);
                        }
                        return false;
                    }
                });
                menu.show();
            }
        });

        dogName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(getActivity(), EditDogActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, sectionTitle);
                intent.putExtra(Utility.page, getArguments().getInt(ARG_SECTION_NUMBER) - 1);

                getActivity().startActivityForResult(intent, 0);
                return false;
            }
        });

        return rootView;
    }
}
