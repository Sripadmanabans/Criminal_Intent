package com.example.sripadmanaban.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A singleton holding the list of crimes
 * Created by Sripadmanaban on 3/20/2015.
 */
public class CrimeLab {

    private static CrimeLab sCrimeLab;
    private Context mAppContext;

    private ArrayList<Crime> mCrimes;

    private CrimeLab(Context context) {
        mAppContext = context;
        mCrimes = new ArrayList<>();

        for(int i = 0; i < 100; i++) {
            Crime c = new Crime();
            c.setTitle("Crime #" + i);
            c.setSolved(i % 2 == 0);
            mCrimes.add(c);
        }
    }

    public static CrimeLab getInstance(Context context) {
        if(sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context.getApplicationContext());
        }
        return sCrimeLab;
    }

    public ArrayList<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id) {
        for(Crime crime : mCrimes) {
            if(id == crime.getId()) {
                return crime;
            }
        }
        return null;
    }
}
