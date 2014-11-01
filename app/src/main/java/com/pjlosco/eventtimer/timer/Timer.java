package com.pjlosco.eventtimer.timer;

import com.google.common.base.Optional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by patricklosco on 11/1/14.
 */
public class Timer {

    private ArrayList<Timestamp> finishTimesList;
    private Timestamp startTime;

    public Timer(Optional<Double> currentTimePassed) {
        if (currentTimePassed.isPresent()) {
            // TODO: current time - time passed
        }
        else {
            this.startTime = new Timestamp(new Date().getTime());
        }
    }

    public void punchTime() {
        finishTimesList.add(new Timestamp(new Date().getTime()));
    }
}
