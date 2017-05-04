package com.example.sampletest.criminalintent;


import android.support.v4.app.Fragment;

/**
 * Created by ms on 03.05.2017.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment  createFragment() {

        return new CrimeListFragment();
    }
}
