package com.pjlosco.eventtimer.participants;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import com.pjlosco.eventtimer.R;

public class ParticipantListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_list);
        if (savedInstanceState == null) {
            Bundle args = new Bundle();
            // set args here if needed. maybe location in list?

            ParticipantListFragment participantListFragment = new ParticipantListFragment();
            participantListFragment.setArguments(args);

            getFragmentManager().beginTransaction()
                    .add(R.id.participantListContainer, participantListFragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.participant_list, menu);
        return true;
    }
}
