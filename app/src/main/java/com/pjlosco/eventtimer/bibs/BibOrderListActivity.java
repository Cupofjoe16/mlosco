package com.pjlosco.eventtimer.bibs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.pjlosco.eventtimer.R;
import com.pjlosco.eventtimer.SettingsActivity;

public class BibOrderListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bib_order_list);
        if (savedInstanceState == null) {
            Bundle args = new Bundle();
            // set args here if needed. maybe location in list?

            BibOrderListFragment bibOrderListFragment = new BibOrderListFragment();
            bibOrderListFragment.setArguments(args);

            getFragmentManager().beginTransaction()
                    .add(R.id.bibOrderListContainer, bibOrderListFragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bib_order_list, menu);
        return true;
    }

}
