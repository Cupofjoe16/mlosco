package com.pjlosco.eventtimer.results;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.pjlosco.eventtimer.R;

public class ResultsListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_list);
        if (savedInstanceState == null) {
            ResultsListFragment resultsListFragment = new ResultsListFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.resultsListContainer, resultsListFragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.results_list, menu);
        return true;
    }
}
