package com.pjlosco.eventtimer.timer;

import android.app.DialogFragment;

/**
 * Created by patricklosco on 1/18/15.
 */
public class TimerDialogFragment extends DialogFragment {


    public static final String EXTRA_ADD_TIME = "com.pjlosco.eventtimer.addtime";
    public static final String EXTRA_START_HOUR = "com.pjlosco.eventtimer.starthour";
    public static final String EXTRA_START_MINUTES = "com.pjlosco.eventtimer.startminutes";
    public static final String EXTRA_START_SECONDS = "com.pjlosco.eventtimer.startseconds";

    public static TimerDialogFragment newInstance() {
        TimerDialogFragment dialogFragment = new TimerDialogFragment();
        return dialogFragment;
    }

}
