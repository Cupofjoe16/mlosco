package com.pjlosco.eventtimer.bibentry;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pjlosco.eventtimer.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class BibEntryDetailFragment extends Fragment {

    public static String KEY;

    public BibEntryDetailFragment() {
        KEY = "1";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bib_entry_detail, container, false);
        return rootView;
    }
}
