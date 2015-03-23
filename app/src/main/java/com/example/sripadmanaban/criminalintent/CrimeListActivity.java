package com.example.sripadmanaban.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Holds the CrimeListFragment
 * Created by Sripadmanaban on 3/20/2015.
 */
public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
