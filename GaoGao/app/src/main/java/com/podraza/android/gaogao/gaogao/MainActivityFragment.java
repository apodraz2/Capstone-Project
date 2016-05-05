package com.podraza.android.gaogao.gaogao;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.adampodraza.myapplication.backend.myApi.MyApi;
import com.example.adampodraza.myapplication.backend.myApi.model.Key;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.podraza.android.gaogao.gaogao.data.DataContract;

import java.io.IOException;
import java.util.ArrayList;

//todo
//figure out best way to get ids for items
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link MainActivityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private final String LOG_TAG = getClass().getSimpleName();



    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private static User user;
    private static FloatingActionButton fabMaybe;

    private static int page = 0;
    private static long userId = 0;

    private static int LOADER_ID = 1;
    private View rootView;

    private static View.OnClickListener mOnClickListener;
    private Cursor dogCursor;



    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putParcelable(Utility.arrayListIdentifier, user);


        super.onSaveInstanceState(outState);


    }


    public MainActivityFragment() {
        // Required empty public constructor
    }


    public static MainActivityFragment newInstance(String param1, String param2) {
        MainActivityFragment fragment = new MainActivityFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main_activity, container);

        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v;

                PopupMenu menu = new PopupMenu(getContext(), fabMaybe);
                menu.inflate(R.menu.popup_menu);

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.launch_new_dog) {
                            fabMaybe.hide();
                            Intent intent = new Intent(getContext(), EditDogActivity.class);

                            intent.putExtra(Utility.page, 0);

                            startActivityForResult(intent, 0);

                        } else {
                            Snackbar snackbar = Snackbar.make(view, "Please create a new dog first", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                        return false;
                    }
                });
                menu.show();
            }
        };

        Intent intent = getActivity().getIntent();
        String email = intent.getStringExtra("email");

        String name = intent.getStringExtra("name");

        Log.d(LOG_TAG, "email is " + email);

        Cursor cursor = getActivity().getContentResolver().query(
                Uri.parse(DataContract.UserEntry.CONTENT_URI + "/" +email),
                null,
                null,
                null,
                null);

        if(cursor.moveToFirst()) {

            userId = cursor.getLong(cursor.getColumnIndex(DataContract.UserEntry._id));

            getActivity().getSupportLoaderManager().initLoader(LOADER_ID, null, this);

            SharedPreferences prefs = getActivity().getSharedPreferences("com.podraza.android.gaogao.gaogao", Context.MODE_PRIVATE);
            Log.d(LOG_TAG, "prefs is null " + (prefs == null));
            prefs.edit().putString(Utility.userEmail, user.getEmail());

            prefs.edit().putLong(Utility.userId, user.getId());

            dogCursor = getActivity().getContentResolver().query(
                    DataContract.DogEntry.buildDataUri(userId),
                    null,
                    null,
                    null,
                    null
            );


            user = new User(userId, name, email, new ArrayList<ParcelableDog>());

            mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager(), dogCursor);

            // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) rootView.findViewById(R.id.container);

            mViewPager.setAdapter(mSectionsPagerAdapter);

            Log.d(LOG_TAG, "Cursor's count is " + dogCursor.getCount());
            String dogName;
            long id;


        } else {
            CreateUserTask task = new CreateUserTask();

            user = new User();
            user.setEmail(email);
            user.setName(name);
            userId = user.getId();

            getActivity().getSupportLoaderManager().initLoader(LOADER_ID, null, this);

            SharedPreferences prefs = getActivity().getSharedPreferences("com.podraza.android.gaogao.gaogao", Context.MODE_PRIVATE);
            Log.d(LOG_TAG, "prefs is null " + (prefs == null));
            prefs.edit().putString(Utility.userEmail, user.getEmail());

            prefs.edit().putLong(Utility.userId, user.getId());

            if(Utility.isNetworkAvailable(getContext())) {

                task.execute();
            } else {
                Snackbar snack = Snackbar.make(new LinearLayout(getContext()), "Please connect to the internet", Snackbar.LENGTH_LONG);
                snack.show();
            }



            dogCursor = getActivity().getContentResolver().query(
                    DataContract.DogEntry.buildDataUri(userId),
                    null,
                    null,
                    null,
                    null
            );

            mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager(), dogCursor);

            // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) rootView.findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);

            ContentValues values = new ContentValues();

            values.put(DataContract.UserEntry.COLUMN_NAME, user.getName());
            values.put(DataContract.UserEntry.COLUMN_EMAIL, user.getEmail());
            values.put(DataContract.UserEntry._id, userId);

            getActivity().getContentResolver().insert(DataContract.UserEntry.buildDataUri(userId), values);

        }


        fabMaybe = (FloatingActionButton) rootView.findViewById(R.id.fab_maybe);

        if (dogCursor == null || dogCursor.getCount() == 0) {
            //This is a floating action button that is only visible if there are no dogs in the list

            fabMaybe.setOnClickListener(mOnClickListener);
        } else {
            fabMaybe.hide();
        }



        return rootView;
    }

    /**
     * Method to receive edited string and it's position in the list
     *
     * @param resultCode
     * @param requestCode
     * @param data
     */
    @Override
    public void onActivityResult(int resultCode, int requestCode, Intent data) {
        super.onActivityResult(resultCode, requestCode, data);
        Log.d(LOG_TAG, "onActivityResult");

        if (data != null) {
            int page = data.getIntExtra(Utility.page, 0);
            if (data.getBooleanExtra(Utility.isDogResult, false)) {
                String dogName = data.getStringExtra(Intent.EXTRA_TEXT);

                boolean isEditDog = data.getBooleanExtra(Utility.isEditDog, false);
                long dogId = data.getLongExtra(Utility.dogId, 0);



                updateDogData(dogName, isEditDog, dogId);


            } else {
                int position = data.getIntExtra(Utility.position, 100);
                String todoDesc = data.getStringExtra(Intent.EXTRA_TEXT);

                long todoId = data.getLongExtra(Utility.todoId, 0);
                long dogId = data.getLongExtra(Utility.dogId, 0);

                updateTodoData(position, todoDesc, todoId, dogId);

            }

            mViewPager.setCurrentItem(page - 1, true);


            if (dogCursor.getCount() == 0) {
                fabMaybe.show();
                fabMaybe.setOnClickListener(mOnClickListener);

            } else {
                fabMaybe.hide();
            }

        }



    }

    /**
     * To reload the pager fragment
     */
    private void refreshScreen() {
        dogCursor = getActivity().getContentResolver().query(
                DataContract.DogEntry.buildDataUri(userId),
                null,
                null,
                null,
                null
        );

        mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager(), dogCursor);
        mViewPager = (ViewPager) rootView.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    //TODO
    //Need to restrict the amount of items that can be added to the list
    /**
     * This method handles all the logic to update the underlying data structures and is controlled by
     * MainActivity
     *
     * @param dogName
     */

    public void updateDogData(String dogName, boolean isEditDog, long dogId) {

        Log.d(LOG_TAG, "dog's size is " + dogCursor.getCount());


        if (dogName.equals(Utility.emptyString)) {
            Log.d(LOG_TAG, "emptyString");
            if (page == 0 && dogCursor.getCount() != 0) {

                getActivity().getContentResolver().delete(DataContract.DogEntry.buildDataUri(dogId), null, null);
                //user.getDogs().remove(page);




            } else if (page >= dogCursor.getCount() || dogCursor.getCount() == 0) {
                Log.d(LOG_TAG, "page number is wrong");
                Log.d(LOG_TAG, "dogId is " + dogId);
                if(dogId != 0)
                    getActivity().getContentResolver().delete(DataContract.DogEntry.buildDataUri(dogId), null, null);

            } else {
                Log.d(LOG_TAG, "dogId is " + dogId);
                getActivity().getContentResolver().delete(DataContract.DogEntry.buildDataUri(dogId), null, null);
                //user.getDogs().remove(page);
                Log.d(LOG_TAG, "remove dog");

            }


        } else if (!isEditDog) {
            Log.d(LOG_TAG, "new dog");


            Log.d(LOG_TAG, dogName);

            ParcelableDog tempDog = new ParcelableDog(dogName);

            ContentValues values = new ContentValues();
            values.put(DataContract.DogEntry.COLUMN_NAME, dogName);
            values.put(DataContract.DogEntry._id, tempDog.getId());

            getActivity().getContentResolver().insert(DataContract.DogEntry.buildDataUri(userId), values);


            //user.getDogs().add(tempDog);



        } else {

            ContentValues values = new ContentValues();
            values.put(DataContract.DogEntry.COLUMN_NAME, dogName);

            getActivity().getContentResolver().update(DataContract.DogEntry.buildDataUri(dogId), values, null, null);


        }

        refreshScreen();

    }


    //TODO
    //Need to restrict the amount of items that can be added to the list
    /**
     * Like updateDogData, this method handles all the logic to update the underlying data structures
     *
     * @param position
     * @param todoDesc
     */
    public void updateTodoData(int position, String todoDesc, long todoId, long dogId) {


        //Case if user chose to delete item
        if (todoDesc.equals(" ")) {

                //user.getDogs().get(page).getTodos().remove(position);
                getActivity().getContentResolver().delete(DataContract.TodoEntry.buildDataUri(todoId), null, null);



        } else {
            //Case if user edited an item that was already in the list
            if (position != 100) {

                ContentValues values = new ContentValues();
                values.put(DataContract.TodoEntry.COLUMN_DESCRIPTION, todoDesc);
                values.put(DataContract.TodoEntry.COLUMN_DONE, false);

                getActivity().getContentResolver().update(DataContract.TodoEntry.buildDataUri(todoId), values, null, null);


            }
            //Case if user created a new item to add to list
            else {

                ParcelableTodo todo = new ParcelableTodo(todoDesc, dogId);

                Log.d(LOG_TAG, "page is " + page);
                Log.d(LOG_TAG, "dogId is " + dogId);

                ContentValues values = new ContentValues();
                values.put(DataContract.TodoEntry.COLUMN_DESCRIPTION, todoDesc);
                values.put(DataContract.TodoEntry._id, todo.getId());
                values.put(DataContract.TodoEntry.COLUMN_DOG_ID, dogId);
                //Log.d(LOG_TAG, "dog id is " + user.getDogs().get(page).getId());
                values.put(DataContract.TodoEntry.COLUMN_DONE, 0);

                getActivity().getContentResolver().insert(DataContract.TodoEntry.buildDataUri(dogId), values);


            }
        }

        refreshScreen();

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        CursorLoader loader = new CursorLoader(
                getContext(),
                DataContract.DogEntry.buildDataUri(userId),
                null,
                null,
                null,
                null
        );


        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(LOG_TAG, "onLoadFinished");
        mSectionsPagerAdapter.swapCursor(data);
        //getLoaderManager().restartLoader(LOADER_ID, null, this);
        //getLoaderManager().destroyLoader(LOADER_ID);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mSectionsPagerAdapter.swapCursor(null);
    }

    /**
     * A {@link FragmentStatePagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        private Cursor cursor;


        public SectionsPagerAdapter(FragmentManager fm, Cursor cursor) {

            super(fm);

            this.cursor = cursor;
        }

        @Override
        public DogFragment getItem(int page) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            Log.d(LOG_TAG, "cursor's size in getItem is " + cursor.getCount());

            if (cursor == null) {
                return null;
            }

            cursor.moveToPosition(page);

            DogFragment dogFragment = new DogFragment();

            Bundle args = new Bundle();

            args.putInt(DogFragment.ARG_SECTION_NUMBER, page + 1);
            args.putString(DogFragment.ARG_SECTION_TITLE, getPageTitle(page).toString());
            args.putLong(DogFragment.ARG_DOG_ID, cursor.getLong(cursor.getColumnIndex(DataContract.DogEntry._id)));

            dogFragment.setArguments(args);


            return dogFragment;
        }

        @Override
        public int getCount() {
            if(cursor != null)
                return cursor.getCount();
            else
                return 0;

        }

        @Override
        public CharSequence getPageTitle(int position) {


            if (position < cursor.getCount()) {
                cursor.moveToPosition(position);

                String dogName = cursor.getString(cursor.getColumnIndex(DataContract.DogEntry.COLUMN_NAME));

                return dogName;
            } else
                return null;
        }

        public void swapCursor(Cursor c) {
            if (cursor == c)
                return;

            this.cursor = c;
            notifyDataSetChanged();
        }

        public Cursor getCursor() {
            return cursor;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        dogCursor.close();
    }

    class CreateUserTask extends AsyncTask<Void, Void, Void> {
        private MyApi myApiService;

        @Override
        protected Void doInBackground(Void... params) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://gaogao-1257.appspot.com/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                            request.setDisableGZipContent(true);
                        }
                    });

            myApiService = builder.build();
            try {

                myApiService.createUser(user.getEmail());
            } catch (IOException e){
                e.printStackTrace();
            }

            return null;
        }
    }

}
