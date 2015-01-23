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
    private Button startButton;
    private TextView textTimer;
    private TextView textDeciseconds;
    private static long startTime = 0L;
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
//            startTime = System.currentTimeMillis() - timer.getStartTime().getTime();
            setTimerText();
            myHandler.postDelayed(updateTimerMethod, 0);
            startButton.setText("Re-Start");
        }
        setTimerText();
    }

    @Override
    public void onPause() {
        super.onPause();
        activityStopped = true;
        myHandler.removeCallbacks(updateTimerMethod);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_clock, container, false);
        textTimer = (TextView) rootView.findViewById(R.id.textTimer);
        textDeciseconds = (TextView) rootView.findViewById(R.id.textDeciseconds);

        startButton = (Button) rootView.findViewById(R.id.btnStart);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // TODO - read in start time
                // long newStartTime = textTimer.getText();
                startButton.setText("Re-Start");
                timer.setStartTime(0);
                textTimer.setText("00:00:00");
                textDeciseconds.setText("00");
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
        textTimer.setText(Timer.formatTime(timeInMillies));
        textDeciseconds.setText(Timer.formatDeciseconds(timeInMillies));
    }

}
