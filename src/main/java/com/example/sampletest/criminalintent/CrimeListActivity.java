package com.example.sampletest.criminalintent;



/**
 * Created by ms on 03.05.2017.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected CrimeFragment createFragment() {
        return new CrimeFragment();
    }
}
