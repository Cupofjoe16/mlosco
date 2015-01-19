package com.pjlosco.eventtimer.timer;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.pjlosco.eventtimer.R;
import com.pjlosco.eventtimer.SettingsActivity;

import java.sql.Timestamp;
import java.util.ArrayList;

public class TimerListFragment extends Fragment {
    private static final String TAG = TimerListFragment.class.getName();

    public Button addNewTimeButton;
    public ListView timestampListView;
    public Timer timer;
    public ArrayList<Timestamp> timestamps;
    private TimeAdapter timeAdapter;

    private static final String TIME_DIALOG = "time dialog";
    private static final int ADD_TIME = 0;
    private static final int SET_START_TIME = 1;

    public TimerListFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.title_activity_timer);
        timer = Timer.get(getActivity());
        timestamps = timer.getTimes();
        timeAdapter = new TimeAdapter(timestamps);
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle args = getArguments();
        timeAdapter.notifyDataSetChanged();

        if (args != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_timestamp_list, container, false);

        timestampListView = (ListView) rootView.findViewById(R.id.list);

        timestampListView.setAdapter(timeAdapter);

        addNewTimeButton = (Button) rootView.findViewById(R.id.addBibNumber);
        addNewTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.addTimestamp();
            }

        });
        timeAdapter.notifyDataSetChanged();
        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(getActivity(),SettingsActivity.class));
            return true;
        } else if (id == R.id.add_custom_time) {
            FragmentManager fragmentManager = getActivity().getFragmentManager();
            TimerDialogFragment dialogFragment = TimerDialogFragment.newInstance();
            dialogFragment.setTargetFragment(TimerListFragment.this, ADD_TIME);
            dialogFragment.show(fragmentManager, TIME_DIALOG);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == ADD_TIME) {
            Timestamp newTime = (Timestamp)data.getSerializableExtra(TimerDialogFragment.EXTRA_ADD_TIME);
            timer.addTimestamp(newTime);
        } else if (resultCode == SET_START_TIME) {
            int hours = (Integer) data.getSerializableExtra(TimerDialogFragment.EXTRA_START_HOUR);
            int minutes = (Integer) data.getSerializableExtra(TimerDialogFragment.EXTRA_START_MINUTES);
            int seconds = (Integer) data.getSerializableExtra(TimerDialogFragment.EXTRA_START_SECONDS);
            timer.setStartTime(hours, minutes, seconds);
        }
        timeAdapter.notifyDataSetChanged();
    }

    private class TimeAdapter extends ArrayAdapter<Timestamp> {

        public TimeAdapter(ArrayList<Timestamp> timestamps) {
            super(getActivity(), 0, timestamps);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_bib, null);
            }
            Timestamp timestamp = getItem(position);

            TextView positionTextView = (TextView) convertView.findViewById(R.id.position_list_item_textView);
            positionTextView.setText((timestamps.indexOf(timestamp)+1)+"");
            TextView bibNumberTextView = (TextView) convertView.findViewById(R.id.bib_number_list_item_textView);
            bibNumberTextView.setText(timestamp+"");
            return convertView;
        }
    }
}
