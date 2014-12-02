package com.pjlosco.eventtimer.bibs;

import android.content.Context;
import android.util.Log;

import com.pjlosco.eventtimer.data.EventTimerJSONSerializer;

import java.util.ArrayList;

public class BibCatalogue {
    private static final String TAG = "BibCatalogue";
    private static final String FILENAME = "bibs.json";

    private static BibCatalogue bibCatalogue;
    private Context mAppContext;

    private ArrayList<BibEntry> enteredBibs;
    private EventTimerJSONSerializer mSerializer;

    private BibCatalogue(Context appContext) {
        mAppContext = appContext;
        mSerializer = new EventTimerJSONSerializer(mAppContext, FILENAME);
        try {
            enteredBibs = mSerializer.loadBibs();
        } catch (Exception e) {
            enteredBibs = new ArrayList<BibEntry>();
            Log.e(TAG, "Error loading bibs: ", e);
        }
    }

    public static BibCatalogue get(Context context) {
        if (bibCatalogue == null) {
            bibCatalogue = new BibCatalogue(context.getApplicationContext());
        }
        return bibCatalogue;
    }

    public void addBib(BibEntry c) {
        enteredBibs.add(c);
    }

    public boolean saveBibs() {
        try {
            mSerializer.saveBibs(enteredBibs);
            Log.d(TAG, "Bibs saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving bibs: ", e);
            return false;
        }
    }

    public ArrayList<BibEntry> getBibs(){
        return enteredBibs;
    }

    public BibEntry getBib(int id) {
        for (BibEntry bibEntry : enteredBibs) {
            if (bibEntry.getBibIdNumber() == id) {
                return bibEntry;
            }
        }
        return null;
    }
}
