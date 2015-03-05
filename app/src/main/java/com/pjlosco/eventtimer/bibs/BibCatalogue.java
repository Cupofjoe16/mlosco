package com.pjlosco.eventtimer.bibs;

import android.content.Context;
import android.util.Log;

import com.pjlosco.eventtimer.DuplicateBibEntryException;
import com.pjlosco.eventtimer.data.EventTimerJSONSerializer;

import java.util.ArrayList;
import java.util.Set;

public class BibCatalogue {
    private static final String TAG = "BibCatalogue";
    private static final String FILENAME_ORDERED_BIBS = "ordered_bibs.json";

    private static BibCatalogue bibCatalogue;
    private Context mAppContext;

    private static ArrayList<Integer> orderedBibsList;
    private static Set<Integer> orderedBibsSet;
    private EventTimerJSONSerializer orderedBibsSerializer;

    public BibCatalogue(Context appContext) {
        mAppContext = appContext;
        orderedBibsSerializer = new EventTimerJSONSerializer(mAppContext, FILENAME_ORDERED_BIBS);
        try {
            orderedBibsList = orderedBibsSerializer.loadBibs();
        } catch (Exception e) {
            orderedBibsList = new ArrayList<Integer>();
            Log.e(TAG, "Error loading bibs: ", e);
        }
    }

    public static BibCatalogue get(Context context) {
        if (bibCatalogue == null) {
            bibCatalogue = new BibCatalogue(context.getApplicationContext());
        }
        return bibCatalogue;
    }

    public static BibCatalogue getBibCatalogue() {
        return bibCatalogue;
    }

    public ArrayList<Integer> getOrderedBibs(){
        return orderedBibsList;
    }

    public void addOrderedBib(int bibNumber) throws DuplicateBibEntryException {
        if (!orderedBibsList.contains(bibNumber)) {
            orderedBibsList.add(bibNumber);
        } else {
            throw new DuplicateBibEntryException("Bib already entered");
        }
    }
    public void addOrderedBib(int bibNumber, int position) throws DuplicateBibEntryException {
        if (!orderedBibsList.contains(bibNumber)) {
            orderedBibsList.add(position-1, bibNumber);
        } else {
            throw new DuplicateBibEntryException("Bib already entered");
        }
    }


    public boolean removeOrderedBib(int bibNumber) {
        if (orderedBibsList.contains(bibNumber)) {
            orderedBibsList.remove(new Integer(bibNumber));
            return true;
        }
        return false;
    }

    public boolean saveOrderedBibs() {
        try {
            orderedBibsSerializer.saveBibs(orderedBibsList);
            Log.d(TAG, "Bibs saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving bibs: ", e);
            return false;
        }
    }

    public void clearBibCatalogue() {
        orderedBibsList = new ArrayList<Integer>();
        saveOrderedBibs();
    }
}
