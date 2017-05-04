package com.example.sampletest.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by ms on 02.05.2017.
 */

public class CrimeLab {



    private static ArrayList<Crime> mCrimes;
    private static CrimeLab sCrimeLab;
    private Context mAppContext;


    /* Constructor */
    private CrimeLab(Context appContext) {
        mAppContext = appContext;
        mCrimes = new ArrayList<>();

        for(int i = 0; i < 100; i++) {
            Crime c = new Crime();
            c.setTitle("Crime #" + i);
            c.setSolved(i%2 == 0);
            mCrimes.add(c);
        }
    }

    public static CrimeLab get(Context c)
    {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(c.getApplicationContext());
        }
        return sCrimeLab;
    }

    public static ArrayList<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id) {
        for (Crime c : mCrimes) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }
}
