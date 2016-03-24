package com.podraza.android.gaogao.gaogao;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
   
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.podraza.android.gaogao.gaogao.data.DataContract;
import com.podraza.android.gaogao.gaogao.data.DataDBHelper;
import com.podraza.android.gaogao.gaogao.data.DataProvider;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String LOG_TAG = getClass().getSimpleName();
    private FloatingActionButton fabMaybe;

    private int page = 0;
    private long userId = 0;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private static User user;

    private View.OnClickListener mOnClickListener;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putParcelable(Utility.arrayListIdentifier, user);


        super.onSaveInstanceState(outState);


    }

    //TODO: This method is too long, refactor
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v;

                PopupMenu menu = new PopupMenu(getApplicationContext(), fabMaybe);
                menu.inflate(R.menu.popup_menu);

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.launch_new_dog) {
                            fabMaybe.hide();
                            Intent intent = new Intent(getApplicationContext(), EditDogActivity.class);

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

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        String name = intent.getStringExtra("name");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Cursor cursor = getContentResolver().query(
                Uri.parse(DataContract.UserEntry.CONTENT_URI + "/" +email),
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst() && savedInstanceState == null) {

            name = cursor.getString(cursor.getColumnIndex(DataContract.UserEntry.COLUMN_NAME));

            userId = cursor.getLong(cursor.getColumnIndex(DataContract.UserEntry._id));

            int i = 0;

            user = new User(userId, name, email, new ArrayList<ParcelableDog>());

            Cursor dogCursor = this.getContentResolver().query(
                    DataContract.DogEntry.buildDataUri(userId),
                    null,
                    null,
                    null,
                    null
            );

            String dogName;
            long id;

            if (dogCursor.moveToFirst()) {
                do {
                    Log.d(LOG_TAG, "Cursor's count is " + dogCursor.getCount());
                    dogName = dogCursor.getString(cursor.getColumnIndex(DataContract.DogEntry.COLUMN_NAME));
                    id = dogCursor.getLong(cursor.getColumnIndex(DataContract.DogEntry._id));

                    ParcelableDog dog = new ParcelableDog(id, new ArrayList<ParcelableTodo>(), dogName);
                    user.getDogs().add(dog);
                    Cursor todoCursor = getContentResolver().query(
                            DataContract.TodoEntry.buildDataUri(id),
                            null,
                            null,
                            null,
                            null,
                            null
                    );


                    if (todoCursor.moveToFirst()) {
                        do {
                            long todoId = todoCursor.getLong(todoCursor.getColumnIndex(DataContract.TodoEntry._id));
                            String description = todoCursor.getString(todoCursor.getColumnIndex(DataContract.TodoEntry.COLUMN_DESCRIPTION));

                            ParcelableTodo todo = new ParcelableTodo(todoId, description, false, id);
                            user.getDogs().get(i).getTodos().add(todo);


                        } while (todoCursor.moveToNext());
                        todoCursor.close();
                    }
                    i++;


                } while (dogCursor.moveToNext());
                dogCursor.close();

            }

        } else if (savedInstanceState != null) {

            user = savedInstanceState.getParcelable(Utility.arrayListIdentifier);

        } else {
            //ArrayList for the tasks
            user = new User();
            user.setEmail(email);
            user.setName(name);

            ContentValues values = new ContentValues();

            values.put(DataContract.UserEntry.COLUMN_NAME, user.getName());
            values.put(DataContract.UserEntry.COLUMN_EMAIL, user.getEmail());
            values.put(DataContract.UserEntry._id, user.getId());

            Uri userUri = getContentResolver().insert(DataContract.UserEntry.buildDataUri(userId), values);
            user.setId(DataContract.getIdFromUri(userUri));
        }


        fabMaybe = (FloatingActionButton) findViewById(R.id.fab_maybe);

        if (user.getDogs().size() == 0) {
            //This is a floating action button that is only visible if there are no dogs in the list

            fabMaybe.setOnClickListener(mOnClickListener);
        } else {
            fabMaybe.hide();
        }

        refreshScreen();

        cursor.close();
    }

    /**
     * To reload the pager fragment
     */
    private void refreshScreen() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

        if (data != null) {
            if (data.getBooleanExtra(Utility.isDogResult, false)) {
                String dogName = data.getStringExtra(Intent.EXTRA_TEXT);
                page = data.getIntExtra(Utility.page, 0);


                updateDogData(page, dogName);


            } else {
                int position = data.getIntExtra(Utility.position, 100);
                String todoDesc = data.getStringExtra(Intent.EXTRA_TEXT);
                page = data.getIntExtra(Utility.page, 0);
                long todoId = data.getLongExtra(Utility.todoId, 0);

                updateTodoData(position, todoDesc, page, todoId);

            }

            Log.d(LOG_TAG, "dog size is " + user.getDogs().size());

            if (page == 0 && user.getDogs().size() == 0) {
                fabMaybe.show();
                fabMaybe.setOnClickListener(mOnClickListener);

            } else {
                fabMaybe.hide();
            }


            refreshScreen();
        }

    }

    /**
     * This method handles all the logic to update the underlying data structures and is controlled by
     * MainActivity
     *
     * @param page
     * @param dogName
     */

    public void updateDogData(int page, String dogName) {
        if (dogName.equals(Utility.emptyString)) {
            if (page == 0 && user.getDogs().size() != 0) {
                getContentResolver().delete(DataContract.DogEntry.buildDataUri(user.getDogs().get(page).getId()), null, null);
                user.getDogs().remove(page);

                refreshScreen();


            } else if (page >= user.getDogs().size() || user.getDogs().size() == 0) {
                refreshScreen();

            } else {
                getContentResolver().delete(DataContract.DogEntry.buildDataUri(user.getDogs().get(page).getId()), null, null);
                user.getDogs().remove(page);
                refreshScreen();
            }


        } else if (page == user.getDogs().size()) {

            Log.d(LOG_TAG, dogName);

            ParcelableDog tempDog = new ParcelableDog(dogName);

            ContentValues values = new ContentValues();
            values.put(DataContract.DogEntry.COLUMN_NAME, dogName);
            values.put(DataContract.DogEntry._id, tempDog.getId());

            getContentResolver().insert(DataContract.DogEntry.buildDataUri(userId), values);


            user.getDogs().add(tempDog);

        } else {
            if ((page - 1) == user.getDogs().size()) {

                ParcelableDog tempDog = new ParcelableDog(dogName);

                ContentValues values = new ContentValues();
                values.put(DataContract.DogEntry.COLUMN_NAME, dogName);
                values.put(DataContract.DogEntry._id, tempDog.getId());

                getContentResolver().insert(DataContract.DogEntry.buildDataUri(userId), values);


                user.getDogs().add(page - 1, tempDog);
            } else {
                ParcelableDog tempDog = user.getDogs().get(page);
                //TODO content resolver update method
                tempDog.setName(dogName);

                user.getDogs().remove(page);

                user.getDogs().add(page, tempDog);

                ContentValues values = new ContentValues();
                values.put(DataContract.DogEntry.COLUMN_NAME, dogName);


                getContentResolver().update(DataContract.DogEntry.buildDataUri(tempDog.getId()), values, null, null);
            }

        }


    }

    /**
     * Like updateDogData, this method handles all the logic to update the underlying data structures
     *
     * @param position
     * @param todoDesc
     * @param page
     */
    public void updateTodoData(int position, String todoDesc, int page, long todoId) {

        //Case if user chose to delete item
        if (todoDesc.equals(" ")) {

                user.getDogs().get(page).getTodos().remove(position);
                getContentResolver().delete(DataContract.TodoEntry.buildDataUri(todoId), null, null);

                refreshScreen();


        } else {
            //Case if user edited an item that was already in the list
            if (position != 100) {
                ParcelableTodo tempTodo = user.getDogs().get(page).getTodos().get(position);
                tempTodo.setTodo(todoDesc);
                user.getDogs().get(page).getTodos().remove(position);
                user.getDogs().get(page).addTodos(tempTodo, position);

                ContentValues values = new ContentValues();
                values.put(DataContract.TodoEntry.COLUMN_DESCRIPTION, todoDesc);
                values.put(DataContract.TodoEntry.COLUMN_DONE, tempTodo.isDone());

                getContentResolver().update(DataContract.TodoEntry.buildDataUri(tempTodo.getId()), values, null, null);


            }
            //Case if user created a new item to add to list
            else {

                ParcelableTodo todo = new ParcelableTodo(todoDesc, user.getDogs().get(page - 1).getId());

                ContentValues values = new ContentValues();
                values.put(DataContract.TodoEntry.COLUMN_DESCRIPTION, todoDesc);
                values.put(DataContract.TodoEntry._id, todo.getId());
                values.put(DataContract.TodoEntry.COLUMN_DOG_ID, user.getDogs().get(page - 1).getId());
                values.put(DataContract.TodoEntry.COLUMN_DONE, 0);

                user.getDogs().get(page - 1).getTodos().add(todo);

                getContentResolver().insert(DataContract.TodoEntry.buildDataUri(user.getDogs().get(page - 1).getId()), values);


            }


        }

        refreshScreen();


    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            return PlaceholderFragment.newInstance(position + 1, getPageTitle(position));
        }

        @Override
        public int getCount() {
            // Show no more pages than the size of todo

            return user.getDogs().size();

        }

        @Override
        public CharSequence getPageTitle(int position) {

            if (position < user.getDogs().size()) {

                return user.getDogs().get(position).getName();
            } else
                return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
        private String LOG_TAG = getClass().getSimpleName();

        private TodoCursorAdapter todoAdapter;

        private long dogId;
        private int sectionNumber;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */

        private int LOADER_ID = 2;

        private static final String ARG_SECTION_NUMBER = "section_number";

        private static final String ARG_SECTION_TITLE = "section_title";

        private String sectionTitle = "No Dog";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, CharSequence pageTitle) {


            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putCharSequence(ARG_SECTION_TITLE, pageTitle);
            fragment.setArguments(args);


            return fragment;
        }

        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER) - 1;
            dogId = user.getDogs().get(sectionNumber).getId();


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
            sectionTitle = user.getDogs().get(getArguments().getInt(ARG_SECTION_NUMBER) - 1).getName();


            todoAdapter = new TodoCursorAdapter(getActivity(),
                    null,
                    sectionNumber);
            todoView.setAdapter(todoAdapter);
            dogName.setText(sectionTitle);


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

                                intent.putExtra(Utility.page, getArguments().getInt(ARG_SECTION_NUMBER) + 1);

                                getActivity().startActivityForResult(intent, 0);
                            } else {
                                Intent intent = new Intent(getActivity().getApplicationContext(), EditTodoActivity.class);

                                intent.putExtra(Utility.page, getArguments().getInt(ARG_SECTION_NUMBER));

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

        @Override
        public void onSaveInstanceState(Bundle outstate) {

            super.onSaveInstanceState(outstate);

        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            getLoaderManager().initLoader(LOADER_ID, null, this);

            super.onActivityCreated(savedInstanceState);
        }


        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {

            Uri dogTodoUri = DataContract.TodoEntry.buildDataUri(dogId);


            return new CursorLoader(getActivity(), dogTodoUri, null, null, null, null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            todoAdapter.swapCursor(data);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            todoAdapter.swapCursor(null);
        }

        @Override
        public void onActivityResult(int resultCode, int requestCode, Intent data) {



        }


    }
}
