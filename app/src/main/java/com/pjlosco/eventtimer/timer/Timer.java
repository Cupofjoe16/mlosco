package com.pjlosco.eventtimer.timer;

import android.content.Context;
import android.util.Log;

import com.pjlosco.eventtimer.data.EventTimerJSONSerializer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
            Log.e(TAG, "Error loading times: ", e);
        }
    }

    public static Timer get(Context context) {
        if (timer == null) {
            timer = new Timer(context.getApplicationContext());
        }
        return timer;
    }

    public static Timer get() throws NullPointerException {
        return timer;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(long timeInMillies) {
        // TODO - debug this. should be current system time
        this.startTime = new Timestamp(timeInMillies);
    }

    public void addTimestamp() {
        finishTimesList.add(new Timestamp(System.currentTimeMillis()));
    }
    public void addTimestamp(int hours, int minutes, int seconds) {
        finishTimesList.add(new Timestamp(startTime.getTime()
                + TimeUnit.HOURS.toMillis(hours)
                + TimeUnit.MINUTES.toMillis(minutes)
                + TimeUnit.MINUTES.toMillis(seconds)));
    }

    public ArrayList<Timestamp> getTimes() {
        return finishTimesList;
    }
}
