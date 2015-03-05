package com.pjlosco.eventtimer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.pjlosco.eventtimer.bibs.BibOrderListActivity;
import com.pjlosco.eventtimer.participants.ParticipantListActivity;
import com.pjlosco.eventtimer.results.ResultsListActivity;
import com.pjlosco.eventtimer.settings.SettingsActivity;
import com.pjlosco.eventtimer.timer.TimerActivity;

public class MainMenuActivity extends Activity {

    private static final String TAG = "MainMenuActivity";

    private Button mParticipantEntry;
    private Button mTimeRecord;
    private Button mBibEntry;
    private Button mResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        mParticipantEntry = (Button) findViewById(R.id.participant_entry);
        mParticipantEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, ParticipantListActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        mTimeRecord = (Button) findViewById(R.id.time_record);
        mTimeRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, TimerActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        mBibEntry = (Button) findViewById(R.id.bib_entry);
        mBibEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, BibOrderListActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        mResults = (Button) findViewById(R.id.results);
        mResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, ResultsListActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
