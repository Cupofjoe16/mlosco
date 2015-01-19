package com.pjlosco.eventtimer.timer;

import android.content.Context;
import android.util.Log;

import com.pjlosco.eventtimer.data.EventTimerJSONSerializer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Timer {

    private static final String TAG = "Timer";
    private static final String FILENAME_TIMER = "timer.json";

    private static ArrayList<Timestamp> finishTimesList = new ArrayList<Timestamp>();
    private static Timestamp startTime;

    private static Timer timer;
    private Context mAppContext;
    private EventTimerJSONSerializer timerSerializer;

    public Timer(Context appContext) {
        mAppContext = appContext;
        timerSerializer = new EventTimerJSONSerializer(mAppContext, FILENAME_TIMER);
        try {
            finishTimesList = timerSerializer.loadTimes();
        } catch (Exception e) {
            finishTimesList = new ArrayList<Timestamp>();
            Log.e(TAG, "Error loading bibs: ", e);
        }
    }

    public static Timer get(Context context) {
        if (timer == null) {
            timer = new Timer(context.getApplicationContext());
        }
        return timer;
    }

    public void setStartTime(int hours, int minutes, int seconds) {
        if (hours == 0 && minutes == 0 && seconds == 0) {
            // TODO: current time - time passed, parse into start time.
        }
        else {
            this.startTime = new Timestamp(new Date().getTime());
        }
    }

    public void addTimestamp() {
        finishTimesList.add(new Timestamp(new Date().getTime()));
    }
    public void addTimestamp(Timestamp timestamp) {
        // TODO - Add timestamp time to clock time to insert a specific
        finishTimesList.add(new Timestamp(new Date().getTime()));
    }

    public ArrayList<Timestamp> getTimes() {
        return finishTimesList;
    }
}
