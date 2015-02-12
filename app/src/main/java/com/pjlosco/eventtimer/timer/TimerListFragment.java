package com.pjlosco.eventtimer.timer;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.pjlosco.eventtimer.data.EventTimerJSONSerializer;

import java.sql.Timestamp;
import java.util.ArrayList;

public class TimerListFragment extends Fragment {

    private static final String TAG = TimerListFragment.class.getName();
    private static final String FILENAME_TIMESTAMPS = "timestamps.json";

    public Timer timer;
    public ArrayList<Timestamp> timestamps;
    private TimeAdapter timeAdapter;

    private Context mAppContext;
    private EventTimerJSONSerializer timerJSONSerializer;

    private static final String TIME_DIALOG = "time dialog";
    private static final int ADD_TIME = 0;
    private static final int SET_START_TIME = 1;

    public Button addNewTimeButton;
    public ListView timestampListView;

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
        mAppContext = getActivity();
        timerJSONSerializer = new EventTimerJSONSerializer(mAppContext, FILENAME_TIMESTAMPS);
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
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            timerJSONSerializer.saveTimes(timestamps);
        } catch (Exception e) {
            Log.e(TAG, "Error saving times: ", e);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_timestamp_list, container, false);

        timestampListView = (ListView) rootView.findViewById(R.id.timestampListView);

        timestampListView.setAdapter(timeAdapter);

        addNewTimeButton = (Button) rootView.findViewById(R.id.punchNewTime);
        addNewTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.addTimestamp();
                timeAdapter.notifyDataSetChanged();
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
            int hours = (Integer) data.getSerializableExtra(TimerDialogFragment.EXTRA_HOUR);
            int minutes = (Integer) data.getSerializableExtra(TimerDialogFragment.EXTRA_MINUTES);
            int seconds = (Integer) data.getSerializableExtra(TimerDialogFragment.EXTRA_SECONDS);
            timer.addTimestamp(hours, minutes, seconds);
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
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_timestamp, null);
            }
            Timestamp timestamp = getItem(position);

            TextView positionTextView = (TextView) convertView.findViewById(R.id.timestamp_position_list_item_textView);
            positionTextView.setText((timestamps.indexOf(timestamp)+1)+"");
            TextView timestampTextView = (TextView) convertView.findViewById(R.id.timestamp_list_item_textView);
            timestampTextView.setText(Timer.get().getFinishedPlacementTime(position+1));
            return convertView;
        }
    }
}
