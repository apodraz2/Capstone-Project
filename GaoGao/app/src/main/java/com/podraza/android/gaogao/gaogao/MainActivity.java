package com.podraza.android.gaogao.gaogao;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.podraza.android.gaogao.gaogao.data.DataProvider;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String LOG_TAG = getClass().getSimpleName();
    private FloatingActionButton fabMaybe;

    private DataProvider dp;

    private int page = 0;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    //ArrayList for the tasks
    private static User user = new User(1, "Adam Podraza", "apodra86@gmail.com");

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putParcelable(Utility.arrayListIdentifier, user);


        super.onSaveInstanceState(outState);


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Cursor cursor = getContentResolver().query(
                DataContract.UserEntry.buildDataUri(1),
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst() && savedInstanceState == null) {
            String name = cursor.getString(cursor.getColumnIndex(DataContract.UserEntry.COLUMN_NAME));
            String email = cursor.getString(cursor.getColumnIndex(DataContract.UserEntry.COLUMN_EMAIL));
            user = new User(1, name, email);

            cursor = this.getContentResolver().query(
                    DataContract.DogEntry.buildDataUri(1),
                    null,
                    null,
                    null,
                    null
            );

            if(cursor.moveToFirst()) {
                do {
                    name = cursor.getString(cursor.getColumnIndex(DataContract.DogEntry.COLUMN_NAME));
                    long id = cursor.getLong(cursor.getColumnIndex(DataContract.DogEntry.COLUMN_ID));

                    ParcelableDog dog = new ParcelableDog(id, name);
                    user.getDogs().add(dog);

                } while(cursor.moveToNext());
            }

        }

        if(savedInstanceState != null) {

            user = savedInstanceState.getParcelable(Utility.arrayListIdentifier);

        }

        fabMaybe = (FloatingActionButton) findViewById(R.id.fab_maybe);

        if(user.getDogs().size() == 0) {
            //This is a floating action button that is only visible if there are no dogs in the list

            fabMaybe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final View view = v;

                    PopupMenu menu = new PopupMenu(getApplicationContext(), fabMaybe);
                    menu.inflate(R.menu.popup_menu);

                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if(item.getItemId() == R.id.launch_new_dog) {
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

            });
        } else {
            fabMaybe.hide();
        }

        refreshScreen();


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

        if(data != null) {
            if(data.getBooleanExtra(Utility.isDogResult, false)) {
                String dogName = data.getStringExtra(Intent.EXTRA_TEXT);
                page = data.getIntExtra(Utility.page, 0);

                if(page == 0 && user.getDogs().size() != 0) {
                    fabMaybe.show();
                } else {
                    fabMaybe.hide();
                }

                user.updateDogData(page, dogName);
            } else {
                int position = data.getIntExtra(Utility.position, 100);
                String todoDesc = data.getStringExtra(Intent.EXTRA_TEXT);
                page = data.getIntExtra(Utility.page, 0);

                user.updateTodoData(position, todoDesc, page);

            }


                refreshScreen();
            }

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

            if(position < user.getDogs().size()) {

                return user.getDogs().get(position).getName();
            } else
                return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private String LOG_TAG = getClass().getSimpleName();

        private TodoAdapter todoAdapter;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
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
            sectionTitle = user.getDogs().get(getArguments().getInt(ARG_SECTION_NUMBER)-1).getName();

            if(getArguments() != null) {


                todoAdapter = new TodoAdapter(getActivity(), user.getDogs().get(getArguments().getInt(ARG_SECTION_NUMBER) - 1).getTodos(), getArguments().getInt("section_number")-1);


                dogName.setText(sectionTitle);

                todoView.setAdapter(todoAdapter);
            }


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
                            if(item.getItemId() == R.id.launch_new_dog) {
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


    }



}
