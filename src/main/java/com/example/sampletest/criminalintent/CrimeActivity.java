package com.example.sampletest.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Created by ms on 07.05.2017.
 */

public class CrimeActivity extends SingleFragmentActivity {
    @Override    protected Fragment createFragment()
    {
        return new CrimeFragment();
    }
}


