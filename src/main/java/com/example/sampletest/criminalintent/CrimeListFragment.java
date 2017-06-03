package com.example.sampletest.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.logging.Logger;

import static android.R.attr.resource;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
/**
 * Created by ms on 02.05.2017.
 */

public class CrimeListFragment extends ListFragment  {
    public static final int REQUEST_CRIME = 1;
    private ArrayList<Crime> mCrimes;
    public static final String TAG = "CrimeListFragment";

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
         if (requestCode == REQUEST_CRIME) {
             Log.d(TAG, "::REQUEST_CRIME");
             ((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
         }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()){
           case R.id.menu_item_new_crime:
               Crime crime = new Crime();
               CrimeLab.get(getActivity()).addCrime(crime);
                Intent i = new Intent(getActivity(), CrimePagerActivity.class);
               startActivityForResult(i,0);
               return true;
           case R.id.menu_item_show_subtitle:
               if (getActivity().getActionBar().getSubtitle() == null) {
                   getActivity().getActionBar().setSubtitle(R.string.subtitle);
                   item.setTitle(R.string.hide_subtitle);
               }
               else {
                   getActivity().getActionBar().setSubtitle(null);
                   item.setTitle(R.string.show_subtitle);
               }
               return true;
           default:
               return super.onOptionsItemSelected(item);
       }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


        getActivity().setTitle(R.string.crimes_title);
        mCrimes = CrimeLab.get(getActivity()).getCrimes();
        CrimeAdapter adapter = new CrimeAdapter(mCrimes);
        setListAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Crime c = ((CrimeAdapter)getListAdapter()).getItem(position);
        Intent i = new Intent(getActivity(), CrimePagerActivity.class);
        i.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getId());
        startActivity(i);
    }


    private class CrimeAdapter extends ArrayAdapter<Crime> {

        public CrimeAdapter(ArrayList<Crime> crimes) {
            super(getActivity(), 0, crimes);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_crime, null);
            }
            Crime c = getItem(position);

            TextView titleTextView =
                    (TextView) convertView.findViewById(R.id.crime_list_item_titleTextView);
            titleTextView.setText(c.getTitle());

            TextView dateTextView =
                    (TextView) convertView.findViewById(R.id.crime_list_item_dateTextView);

            String formatDate = DateFormat.format("EEEE, MMM dd, yyyy hh:mm",
                    c.getDate()).toString();

            dateTextView.setText(formatDate);

            CheckBox solvedCheckBox = (CheckBox)convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
           solvedCheckBox.setChecked(c.isSolved());
            return convertView;
        }
    }
}

