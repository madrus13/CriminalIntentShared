package com.example.sampletest.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by ms on 14.05.2017.
 */

public class TimePickerFragment extends DialogFragment {
    public static final  String EXTRA_TIME =
            "com.example.sampletest.criminalIntent.time";
    private Date mTime;

    public static TimePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_TIME, date);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }
    private void sendResult(int resultCode)
    {

        if (getTargetFragment() == null) {
            Log.d("CRIME", "TimePickerFragment:: sendResult:: getTargetFragment() == null");
            return;
        }

        Intent i = new Intent();
        i.putExtra(EXTRA_TIME, mTime);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
        Log.d("CRIME", "TimePickerFragment:: sendResult::  mTime() = " + mTime.getTime());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mTime = (Date)getArguments().getSerializable(EXTRA_TIME);

        //create Calendar object
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mTime);

        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int hour = calendar.get(Calendar.HOUR);
        final int minute = calendar.get(Calendar.MINUTE);

        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_time, null);

        TimePicker timePicker = (TimePicker) v.findViewById(R.id.dialog_time_timePicker);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(hour);
            timePicker.setMinute(minute);
        }
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                //update args
                Calendar calendar = Calendar.getInstance();
                calendar.set(year,month,day,hourOfDay,minute);

                mTime = calendar.getTime();
                Log.d("CRIME", "TimePickerFragment:: onCreateDialog::  onDateChanged() = " + mTime.getTime());
                getArguments().putSerializable(EXTRA_TIME, mTime);
            }
        }) ;

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(
                        android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("CRIME", "TimePickerFragment:: onCreateDialog::  OnClickListener() = " + mTime.getTime());
                                sendResult(Activity.RESULT_OK);
                            }
                        }
                )
                .create();
    }
}
