package com.podraza.android.gaogao.gaogao;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;
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
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.podraza.android.gaogao.gaogao.data.DataContract;

import java.util.ArrayList;


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



    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putParcelable(Utility.arrayListIdentifier, user);


        super.onSaveInstanceState(outState);


    }


    public MainActivityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainActivityFragment.
     */
    // TODO: Rename and change types and number of parameters
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

            Cursor dogCursor = getActivity().getContentResolver().query(
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

            if (dogCursor.moveToFirst()) {
                do {

                    dogName = dogCursor.getString(cursor.getColumnIndex(DataContract.DogEntry.COLUMN_NAME));
                    id = dogCursor.getLong(cursor.getColumnIndex(DataContract.DogEntry._id));

                    ParcelableDog dog = new ParcelableDog(id, new ArrayList<ParcelableTodo>(), dogName);
                    user.getDogs().add(dog);


                } while (dogCursor.moveToNext());

            }

                Log.d(LOG_TAG, "cursor size is " + cursor.getCount());
        } else {
            user = new User();
            user.setEmail(email);
            user.setName(name);
            userId = user.getId();

            ContentValues values = new ContentValues();

            values.put(DataContract.UserEntry.COLUMN_NAME, user.getName());
            values.put(DataContract.UserEntry.COLUMN_EMAIL, user.getEmail());
            values.put(DataContract.UserEntry._id, userId);

            getActivity().getContentResolver().insert(DataContract.UserEntry.buildDataUri(userId), values);

        }


        fabMaybe = (FloatingActionButton) rootView.findViewById(R.id.fab_maybe);

        if (user.getDogs().size() == 0) {
            //This is a floating action button that is only visible if there are no dogs in the list

            fabMaybe.setOnClickListener(mOnClickListener);
        } else {
            fabMaybe.hide();
        }
        getActivity().getSupportLoaderManager().initLoader(LOADER_ID, null, this);


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


            if (page == 0 && user.getDogs().size() == 0) {
                fabMaybe.show();
                fabMaybe.setOnClickListener(mOnClickListener);

            } else {
                fabMaybe.hide();
            }

        }

        refreshScreen();

    }

    /**
     * To reload the pager fragment
     */
    private void refreshScreen() {
        Cursor dogCursor = getActivity().getContentResolver().query(
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
            Log.d(LOG_TAG, "emptyString");
            if (page == 0 && user.getDogs().size() != 0) {
                getActivity().getContentResolver().delete(DataContract.DogEntry.buildDataUri(user.getDogs().get(page).getId()), null, null);
                user.getDogs().remove(page);




            } else if (page >= user.getDogs().size() || user.getDogs().size() == 0) {


            } else {
                getActivity().getContentResolver().delete(DataContract.DogEntry.buildDataUri(user.getDogs().get(page).getId()), null, null);
                user.getDogs().remove(page);


            }


        } else if (page == user.getDogs().size()) {
            Log.d(LOG_TAG, "new dog");

            Log.d(LOG_TAG, dogName);

            ParcelableDog tempDog = new ParcelableDog(dogName);

            ContentValues values = new ContentValues();
            values.put(DataContract.DogEntry.COLUMN_NAME, dogName);
            values.put(DataContract.DogEntry._id, tempDog.getId());

            getActivity().getContentResolver().insert(DataContract.DogEntry.buildDataUri(userId), values);


            user.getDogs().add(tempDog);



        } else {
            if ((page - 1) == user.getDogs().size()) {

                ParcelableDog tempDog = new ParcelableDog(dogName);

                ContentValues values = new ContentValues();
                values.put(DataContract.DogEntry.COLUMN_NAME, dogName);
                values.put(DataContract.DogEntry._id, tempDog.getId());

                getActivity().getContentResolver().insert(DataContract.DogEntry.buildDataUri(userId), values);


                user.getDogs().add(page - 1, tempDog);



            } else {
                ParcelableDog tempDog = user.getDogs().get(page);

                tempDog.setName(dogName);

                user.getDogs().remove(page);

                user.getDogs().add(page, tempDog);

                ContentValues values = new ContentValues();
                values.put(DataContract.DogEntry.COLUMN_NAME, dogName);


                getActivity().getContentResolver().update(DataContract.DogEntry.buildDataUri(tempDog.getId()), values, null, null);



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
            if (page == user.getDogs().size()) {


            } else {
                //user.getDogs().get(page).getTodos().remove(position);
                getActivity().getContentResolver().delete(DataContract.TodoEntry.buildDataUri(todoId), null, null);

            }

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

                ParcelableTodo todo = new ParcelableTodo(todoDesc, user.getDogs().get(page).getId());
                Log.d(LOG_TAG, "page is " + page);

                ContentValues values = new ContentValues();
                values.put(DataContract.TodoEntry.COLUMN_DESCRIPTION, todoDesc);
                values.put(DataContract.TodoEntry._id, todo.getId());
                values.put(DataContract.TodoEntry.COLUMN_DOG_ID, user.getDogs().get(page).getId());
                Log.d(LOG_TAG, "dog id is " + user.getDogs().get(page).getId());
                values.put(DataContract.TodoEntry.COLUMN_DONE, 0);

                //user.getDogs().get(page - 1).getTodos().add(todo);

                getActivity().getContentResolver().insert(DataContract.TodoEntry.buildDataUri(user.getDogs().get(page).getId()), values);


            }
        }

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

            if (cursor == null) {
                return null;
            }

            cursor.moveToPosition(page);

            DogFragment dogFragment = new DogFragment();

            Bundle args = new Bundle();

            args.putInt(DogFragment.ARG_SECTION_NUMBER, page);
            args.putString(DogFragment.ARG_SECTION_TITLE, getPageTitle(page).toString());
            args.putLong(DogFragment.ARG_DOG_ID, cursor.getLong(cursor.getColumnIndex(DataContract.DogEntry._id)));

            dogFragment.setArguments(args);


            return dogFragment;
        }

        @Override
        public int getCount() {

            return cursor.getCount();

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


}