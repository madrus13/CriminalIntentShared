package com.example.sampletest.criminalintent;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

/**
 * Created by KorotayevR on 3/15/2017.
 */


public class CrimeFragment extends Fragment {
    public static String EXTRA_CRIME_ID =
            "com.example.sampletest.criminalintent.crime_id";

    private static  final  String DIALOG_DATE = "date";
    private static  final int REQUEST_DATE = 0;

    private static  final  String DIALOG_TIME = "time";
    private static  final int REQUEST_TIME = 1;

    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private Button mTimeButton;
    private CheckBox mSolvedCheckBox;

    public void returnResult() {

        getActivity().setResult(CrimeListFragment.REQUEST_CRIME, null);
    }

    public static CrimeFragment newInstance(UUID crimeId) {

        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public void updateDate()
    {
        String formatDate = DateFormat.format("EEEE, MMM dd, yyyy",
                mCrime.getDate()).toString();
        mDateButton.setText(formatDate);
    }
    public void updateTime()
    {
        String formatDate = DateFormat.format("hh:mm",
                mCrime.getDate()).toString();

        mTimeButton.setText(formatDate);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("CRIME", "CrimeFragment:: onActivityResult resultCode = " + resultCode);
       if (resultCode != Activity.RESULT_OK) {
           return;
       }

        if (requestCode == REQUEST_DATE) {

            Date date = (Date) data
                    .getSerializableExtra(DataPickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            Log.d("CRIME", "CrimeFragment:: onActivityResult requestCode == REQUEST_DATE mCrime = " + mCrime.getDate());
            updateDate();
        }

        if (requestCode == REQUEST_TIME) {

            Date date = (Date) data
                    .getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            mCrime.setDate(date);
            Log.d("CRIME", "CrimeFragment:: onActivityResult requestCode == REQUEST_TIME mCrime = " + mCrime.getDate());
            updateTime();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
            if (NavUtils.getParentActivityName(getActivity()) != null) {
                NavUtils.navigateUpFromSameTask(getActivity());
            }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);

        setHasOptionsMenu(true);
    }

    @TargetApi(11)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, Bundle savedInstanceState) {

       // Log.d("Crime", "CrimeFragment->onCreateView");

        View v = inflater.inflate(R.layout.fragment_crime, parent, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (NavUtils.getParentActivityIntent(getActivity()) != null) {
                getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence c, int i, int i1, int i2) {
                mCrime.setTitle(c.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mDateButton = (Button)v.findViewById(R.id.crime_date);
        updateDate();

        mDateButton.setText(mCrime.getDate().toString());
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getActivity().getSupportFragmentManager();
                DataPickerFragment dialog =  DataPickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(fm,DIALOG_DATE);
                Log.d("CRIME", "CrimeFragment:: onCreateView::mDateButton -> onClick  " + mCrime.getDate());
            }
        });

        mTimeButton = (Button)v.findViewById(R.id.crime_time);
        updateTime();
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                TimePickerFragment dialog =  TimePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
                dialog.show(fm,DIALOG_TIME);
                Log.d("CRIME", "CrimeFragment:: onCreateView::mTimeButton -> onClick  " + mCrime.getDate());
            }
        });

        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });
        return v;
    }
}
