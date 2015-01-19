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
    private TextView textTimer;
    private TextView textDeciseconds;
    private Button startButton;
    private Button pauseButton;
    private long startTime = 0L;
    private Handler myHandler = new Handler();
    long timeInMillies = 0L;
    long timeSwap = 0L;
    long finalTime = 0L;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_clock, container, false);
        textTimer = (TextView) rootView.findViewById(R.id.textTimer);
        textDeciseconds = (TextView) rootView.findViewById(R.id.textDeciseconds);

        startButton = (Button) rootView.findViewById(R.id.btnStart);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startTime = SystemClock.uptimeMillis();
                myHandler.postDelayed(updateTimerMethod, 0);

            }
        });

        pauseButton = (Button) rootView.findViewById(R.id.btnPause);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                timeSwap += timeInMillies;
                myHandler.removeCallbacks(updateTimerMethod);

            }
        });

        return rootView;
    }

    private Runnable updateTimerMethod = new Runnable() {

        public void run() {
            timeInMillies = SystemClock.uptimeMillis() - startTime;
            finalTime = timeSwap + timeInMillies;

            int seconds = (int) (finalTime / 1000);
            int minutes = seconds / 60;
            int hours = seconds / (60*60);
            seconds = seconds % 60;
            int milliseconds = (int) (finalTime % 1000);
            int deciseconds = milliseconds/10;
            textTimer.setText(
                    String.format("%02d", hours) + ":"
                            + String.format("%02d", minutes) + ":"
                            + String.format("%02d", seconds));
//            myHandler.postAtTime(this, )
            textDeciseconds.setText(
                    String.format("%02d", deciseconds));
            myHandler.postDelayed(this, 0);
        }

    };

}
