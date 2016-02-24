package com.podraza.android.gaogao.gaogao;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String LOG_TAG = getClass().getSimpleName();

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
    private static ArrayList<ParcelableDog> todos;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent args = getIntent();
        String todoDesc = args.getStringExtra(Intent.EXTRA_TEXT);

        //create dummy data
        if(savedInstanceState == null) {
            Log.d(LOG_TAG, "created new array list");
            ParcelableDog denver = new ParcelableDog("denver");
            ParcelableDog bailey = new ParcelableDog("bailey");

            ParcelableTodo firstDenverTodo = new ParcelableTodo ("Walk Denver", false);
            ParcelableTodo secondDenverTodo = new ParcelableTodo ("Feed", false);
            ParcelableTodo thirdDenverTodo = new ParcelableTodo("Give Meds", false);

            ParcelableTodo firstBaileyTodo = new ParcelableTodo("Walk Bailey", false);
            ParcelableTodo secondBaileyTodo = new ParcelableTodo("Feed Bailey", false);
            ParcelableTodo thirdBaileyTodo = new ParcelableTodo("Give Bailey Meds", false);

            todos = new ArrayList<>();

            denver.addTodos(firstDenverTodo, 0);
            denver.addTodos(secondDenverTodo, 1);
            denver.addTodos(thirdDenverTodo, 2);

            bailey.addTodos(firstBaileyTodo, 0);
            bailey.addTodos(secondBaileyTodo, 1);
            bailey.addTodos(thirdBaileyTodo, 2);

            todos.add(denver);
            todos.add(bailey);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
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
            if(data.getBooleanExtra("is_dog_result", false)) {
                String dogName = data.getStringExtra(Intent.EXTRA_TEXT);
                page = data.getIntExtra("page", 0);

                if(dogName.equals(" ")) {
                    todos.remove(page);


                    refreshScreen();
                } else {
                    ParcelableDog tempDog = todos.get(page);
                    tempDog.setName(dogName);

                    todos.remove(page);
                    todos.add(page, tempDog);
                    refreshScreen();
                }

            } else {
                int position = data.getIntExtra("position", 100);
                String todoDesc = data.getStringExtra(Intent.EXTRA_TEXT);
                page = data.getIntExtra("page", 0);

                //Case if user chose to delete item
                if (todoDesc.equals(" ")) {

                    todos.get(page).getTodos().remove(position);
                    refreshScreen();

                } else {
                    //Case if user edited an item that was already in the list
                    if (position != 100) {
                        ParcelableTodo tempTodo = todos.get(page).getTodos().get(position);
                        tempTodo.setTodo(todoDesc);
                        todos.get(page).getTodos().remove(position);
                        todos.get(page).addTodos(tempTodo, position);
                        refreshScreen();

                    }
                    //Case if user created a new item to add to list
                    else {
                        todos.get(page - 1).getTodos().add(new ParcelableTodo(todoDesc, false));
                        refreshScreen();

                    }
                }
            }
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
            return todos.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            if(position < todos.size()) {

                return todos.get(position).getName();
            } else
                return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private String LOG_TAG = getClass().getSimpleName();

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


            TextView dogName = (TextView) rootView.findViewById(R.id.current_dog);

            if(getArguments() != null) {
                sectionTitle = getArguments().getCharSequence(ARG_SECTION_TITLE).toString();
                Log.d(LOG_TAG, "section title is " + sectionTitle);
                dogName.setText(sectionTitle);
                TodoAdapter todoAdapter = new TodoAdapter(getActivity(), todos.get(getArguments().getInt("section_number") - 1).getTodos(), getArguments().getInt("section_number")-1);
                todoView.setAdapter(todoAdapter);
            }


            /**
             * Floating action button launches a new edit activity
             */
            FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getActivity().getApplicationContext(), EditTodoActivity.class);

                    intent.putExtra("page", getArguments().getInt("section_number"));

                    startActivityForResult(intent, 0);

                }
            });

            dogName.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Intent intent = new Intent(getActivity(), EditDogActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT, sectionTitle);
                    intent.putExtra("page", getArguments().getInt("section_number") - 1);
                    Log.d(LOG_TAG, "page is " + (getArguments().getInt("section_number") - 1));
                    getActivity().startActivityForResult(intent, 0);
                    return false;
                }
            });

            return rootView;
        }



    }

}
