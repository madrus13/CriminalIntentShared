package com.example.sampletest.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by ms on 12.05.2017.
 */

public class DataPickerFragment extends DialogFragment {
    public static final  String EXTRA_DATE =
            "com.example.sampletest.criminalIntent.date";
    private Date mDate;

    public static DataPickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE, date);

        DataPickerFragment fragment = new DataPickerFragment();
        fragment.setArguments(args);
        return fragment;
    }
    private void sendResult(int resultCode)
    {

        if (getTargetFragment() == null) {
            Log.d("CRIME", "DataPickerFragment:: sendResult:: getTargetFragment() == null");
            return;
        }

        Intent i = new Intent();
        i.putExtra(EXTRA_DATE, mDate);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
        Log.d("CRIME", "DataPickerFragment:: sendResult::  mDate() = " + mDate.getTime());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mDate = (Date)getArguments().getSerializable(EXTRA_DATE);

        //create Calendar object
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);

        DatePicker datePicker = (DatePicker) v.findViewById(R.id.dialog_date_dataPicker);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mDate = new GregorianCalendar(year,month,day).getTime();

                //update args
                Log.d("CRIME", "DataPickerFragment:: onCreateDialog::  onDateChanged() = " + mDate.getTime());
                getArguments().putSerializable(EXTRA_DATE, mDate);
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(
                        android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("CRIME", "DataPickerFragment:: onCreateDialog::  OnClickListener() = " + mDate.getTime());
                                sendResult(Activity.RESULT_OK);
                            }
                        }
                )
                .create();
    }
}
