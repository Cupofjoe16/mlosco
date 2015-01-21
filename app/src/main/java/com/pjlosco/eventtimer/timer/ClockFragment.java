package com.pjlosco.eventtimer.timer;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pjlosco.eventtimer.R;

public class ClockFragment extends Fragment {

    public Timer timer;
    private boolean activityStopped = false;

    private TextView textTimer;
    private TextView textDeciseconds;
    private long startTime = 0L;
    private Handler myHandler = new Handler();
    long timeInMillies = 0L;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timer = Timer.get(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        activityStopped = false;
        if (timer.getStartTime() != null) {
            startTime = timer.getStartTime().getTime();
            setTimerText();
            myHandler.postDelayed(updateTimerMethod, 0);
        }
        setTimerText();
    }

    @Override
    public void onPause() {
        super.onPause();
        activityStopped = true;
        myHandler.removeCallbacks(updateTimerMethod);
        if (startTime != 0) {
            timer.setStartTime(startTime);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_clock, container, false);
        textTimer = (TextView) rootView.findViewById(R.id.textTimer);
        textDeciseconds = (TextView) rootView.findViewById(R.id.textDeciseconds);

        Button startButton = (Button) rootView.findViewById(R.id.btnStart);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startTime = SystemClock.uptimeMillis();
                myHandler.postDelayed(updateTimerMethod, 0);

            }
        });

        return rootView;
    }

    private Runnable updateTimerMethod = new Runnable() {

        public void run() {
            if (!activityStopped) {
                setTimerText();
                myHandler.postDelayed(this, 0);
            }
        }

    };

    private void setTimerText() {
        if (startTime != 0) {
            timeInMillies = SystemClock.uptimeMillis() - startTime;
        }
        int seconds = (int) (timeInMillies / 1000);
        int minutes = seconds / 60;
        int hours = seconds / (60 * 60);
        seconds = seconds % 60;
        int milliseconds = (int) (timeInMillies % 1000);
        int deciseconds = milliseconds / 10;
        textTimer.setText(
                String.format("%02d", hours) + ":"
                        + String.format("%02d", minutes) + ":"
                        + String.format("%02d", seconds));
        textDeciseconds.setText(
                String.format("%02d", deciseconds));
    }

}
