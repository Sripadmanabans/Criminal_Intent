package com.example.sripadmanaban.criminalintent;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Shows a list of crimes
 * Created by Sripadmanaban on 3/20/2015.
 */
public class CrimeListFragment extends ListFragment {

    private static final String TAG = "CrimeListFragment";

    private Button mReportCrimeButton;

    private ArrayList<Crime> mCrimes;
    private boolean mSubtitleVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        mSubtitleVisible = false;

        getActivity().setTitle(R.string.crimes_title);
        mCrimes = CrimeLab.getInstance(getActivity()).getCrimes();

        CrimeAdapter adapter = new CrimeAdapter(mCrimes);

        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mReportCrimeButton = (Button) view.findViewById(R.id.report_crime);
        mReportCrimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCrime();
            }
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if(mSubtitleVisible) {
                ((ActionBarActivity) getActivity())
                        .getSupportActionBar().setSubtitle(R.string.subtitle);
            }
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((CrimeAdapter) getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_crime_list, menu);
        MenuItem showSubTitle = menu.findItem(R.id.menu_item_show_subtitle);
        if(mSubtitleVisible && showSubTitle != null) {
            showSubTitle.setTitle(R.string.hide_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime :
                addCrime();
                return true;
            case R.id.menu_item_show_subtitle :
                if(((ActionBarActivity) getActivity())
                        .getSupportActionBar().getSubtitle() == null) {
                    ((ActionBarActivity) getActivity())
                            .getSupportActionBar().setSubtitle(R.string.subtitle);
                    mSubtitleVisible = true;
                    item.setTitle(R.string.hide_subtitle);
                } else {
                    ((ActionBarActivity) getActivity())
                            .getSupportActionBar().setSubtitle(null);
                    mSubtitleVisible = false;
                    item.setTitle(R.string.show_subtitle);
                }
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Crime crime = ((CrimeAdapter)getListAdapter()).getItem(position);
        Log.d(TAG, crime.getTitle() + " was clicked");
        Log.d(TAG, crime.getId() + " was clicked");
        Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
        intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
        startActivity(intent);
    }

    private class CrimeAdapter extends ArrayAdapter<Crime> {

        public CrimeAdapter(ArrayList<Crime> crimes) {
            super(getActivity(), 0, crimes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // If no view found create a new one
            if(convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_crime, null);
            }

            // Configure the view for this crime
            Crime crime = getItem(position);

            TextView titleTextView =
                    (TextView) convertView.findViewById(R.id.crime_list_item_titleTextView);
            titleTextView.setText(crime.getTitle());

            TextView dateTextView =
                    (TextView) convertView.findViewById(R.id.crime_list_item_dateTextView);
            dateTextView.setText(crime.getDate().toString());
            CheckBox solvedCheckBox =
                    (CheckBox) convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
            solvedCheckBox.setChecked(crime.isSolved());

            return convertView;
        }
    }

    private void addCrime() {
        Crime crime = new Crime();
        CrimeLab.getInstance(getActivity()).addCrime(crime);
        Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
        intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
        startActivityForResult(intent, 0);
    }
}
