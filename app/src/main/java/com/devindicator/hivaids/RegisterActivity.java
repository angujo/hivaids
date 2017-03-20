package com.devindicator.hivaids;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.devindicator.hivaids.adapter.FragmentsViewPager;
import com.devindicator.hivaids.database.DBQuery;
import com.devindicator.hivaids.models.Helper;
import com.devindicator.hivaids.models.User;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    public FragmentsViewPager fragmentsViewPager;
    public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        setTitle("About Me...");
        user = new User();

        // Set up the ViewPager with the sections adapter.
        /*mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);*/

        fragmentsViewPager = (FragmentsViewPager) findViewById(R.id.container);
        fragmentsViewPager.setSwipeable(false);
        fragmentsViewPager.setAdapter(mSectionsPagerAdapter);
    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        fragmentsViewPager.setCurrentItem(item, smoothScroll);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private int fragNumber = 0;
        RegisterActivity registerActivity;
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            this.fragNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            final View rootView;
            switch (this.fragNumber) {
                case 1:
                    rootView = inflater.inflate(R.layout.fragment_register_details, container, false);
                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.fragment_register_age, container, false);
                    break;
                case 3:
                    rootView = inflater.inflate(R.layout.fragment_register_hiv, container, false);
                    break;
                case 4:
                    rootView = inflater.inflate(R.layout.fragment_register_kenya, container, false);
                    break;
                case 5:
                    rootView = inflater.inflate(R.layout.fragment_register_welcome, container, false);
                    break;
                default:
                    rootView = inflater.inflate(R.layout.fragment_register, container, false);
                    TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                    textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
                    break;
            }
            final Button btn = (Button) rootView.findViewById(R.id.nxtButton);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean proceed = false;
                    String msg = "Entry missing!";
                    final User user = ((RegisterActivity) getActivity()).user;
                    switch (fragNumber) {
                        case 1:
                            msg = "Full names OR Email missing!";
                            EditText enames = (EditText) rootView.findViewById(R.id.reg_names);
                            EditText email = (EditText) rootView.findViewById(R.id.reg_email);
                            String n = enames.getText().toString().trim(),
                                    e = email.getText().toString().trim();
                            if (n.length() > 0 && e.length() > 0) {
                                user.setEmail(e);
                                user.setNames(n);
                                proceed = true;
                            }
                            break;
                        default:
                            msg = "You need to select YES Or NO!";
                            RadioButton rdYes = (RadioButton) rootView.findViewById(R.id.rdYes);
                            RadioButton rdNo = (RadioButton) rootView.findViewById(R.id.rdNo);
                            if (rdYes.isChecked() || rdNo.isChecked()) {
                                proceed = true;
                                switch (fragNumber) {
                                    case 2:
                                        user.setBtn15and35(rdYes.isChecked());
                                        break;
                                    case 3:
                                        user.setWithHIV(rdYes.isChecked());
                                        break;
                                    case 4:
                                        user.setKenyanResident(rdYes.isChecked());
                                        break;
                                }
                            }
                            break;
                        case 5:
                            btn.setVisibility(View.INVISIBLE);
                            final ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.regProgress);
                            progressBar.setVisibility(View.VISIBLE);
                            if (Helper.isNetworkAvailable(getActivity().getApplicationContext())) {
                                RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                                StringRequest stringRequest = new StringRequest(
                                        Request.Method.POST,
                                        Helper.URL_DETAILS,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                Log.d("HIV", response);
                                                boolean shBtn = false;
                                                try {
                                                    JSONObject jsonObject = new JSONObject(response);
                                                    if (jsonObject.getBoolean("status")) {
                                                        DBQuery dbQuery = new DBQuery(getActivity().getApplicationContext());
                                                        dbQuery.open();
                                                        if (dbQuery.setUser(user)) {
                                                            startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
                                                            getActivity().finish();
                                                        } else {
                                                            shBtn = true;
                                                            Toast.makeText(getActivity().getApplicationContext(), "Error setting your details!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                        shBtn = true;
                                                        Toast.makeText(getActivity().getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (JSONException e1) {
                                                    shBtn = true;
                                                    Toast.makeText(getActivity().getApplicationContext(), "Error encountered with server result!", Toast.LENGTH_SHORT).show();
                                                    e1.printStackTrace();
                                                }
                                                if (shBtn) {
                                                    btn.setVisibility(View.VISIBLE);
                                                    progressBar.setVisibility(View.INVISIBLE);
                                                }
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(getActivity().getApplicationContext(), "Error submitting your details!", Toast.LENGTH_SHORT).show();
                                                Log.e("HIV", error.getMessage());
                                                btn.setVisibility(View.VISIBLE);
                                                progressBar.setVisibility(View.INVISIBLE);
                                            }
                                        });
                                requestQueue.add(stringRequest);
                            } else {
                                Toast.makeText(getActivity().getApplicationContext(), "No Internet Connection to proceed!", Toast.LENGTH_SHORT).show();
                            }
                            break;
                    }
                    if (proceed)
                        ((RegisterActivity) getActivity()).setCurrentItem(fragNumber, true);
                    else if (5 != fragNumber)
                        Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
            });
            return rootView;
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
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
                case 3:
                    return "SECTION 3";
                case 4:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
