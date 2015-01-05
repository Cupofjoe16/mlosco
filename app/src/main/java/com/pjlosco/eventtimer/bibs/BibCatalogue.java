package com.pjlosco.eventtimer.bibs;

import android.content.Context;
import android.util.Log;

import com.pjlosco.eventtimer.data.EventTimerJSONSerializer;

import java.util.ArrayList;

public class BibCatalogue {
    private static final String TAG = "BibCatalogue";
    private static final String FILENAME_ENTERED_BIBS = "entered_bibs.json";
    private static final String FILENAME_ORDERED_BIBS = "ordered_bibs.json";

    private static BibCatalogue bibCatalogue;
    private Context mAppContext;

    private static ArrayList<Integer> participantBibs;
    private static ArrayList<Integer> orderedBibs;
    private EventTimerJSONSerializer participantBibsSerializer;
    private EventTimerJSONSerializer orderedBibsSerializer;

    public BibCatalogue(Context appContext) {
        mAppContext = appContext;
        participantBibsSerializer = new EventTimerJSONSerializer(mAppContext, FILENAME_ENTERED_BIBS);
        orderedBibsSerializer = new EventTimerJSONSerializer(mAppContext, FILENAME_ORDERED_BIBS);
        try {
            participantBibs = participantBibsSerializer.loadBibs();
            orderedBibs = orderedBibsSerializer.loadBibs();
        } catch (Exception e) {
            participantBibs = new ArrayList<Integer>();
            orderedBibs = new ArrayList<Integer>();
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

    public ArrayList<Integer> getEnteredBibs(){
        return participantBibs;
    }
    public ArrayList<Integer> getOrderedBibs(){
        return participantBibs;
    }

    public void addParticipantBib(int bibNumber) throws Exception {
        if (!participantBibs.contains(bibNumber)) {
            participantBibs.add(bibNumber);
        } else {
            throw new Exception("Bib already entered");
        }
    }
    public void addOrderedBib(int bibNumber) throws Exception {
        if (!orderedBibs.contains(bibNumber)) {
            orderedBibs.add(bibNumber);
        } else {
            throw new Exception("Bib already entered");
        }
    }
    public void addOrderedBib(int bibNumber, int position) throws Exception {
        if (!orderedBibs.contains(bibNumber)) {
            orderedBibs.add(position-1, bibNumber);
        } else {
            throw new Exception("Bib already entered");
        }
    }

    public boolean removeEnteredBib(int bibNumber) {
        if (participantBibs.contains(bibNumber)) {
            participantBibs.remove(new Integer(bibNumber));
            return true;
        }
        return false;
    }

    public boolean removeOrderedBib(int bibNumber) {
        if (orderedBibs.contains(bibNumber)) {
            orderedBibs.remove(new Integer(bibNumber));
            return true;
        }
        return false;
    }

    public boolean saveBibs() {
        try {
            participantBibsSerializer.saveBibs(participantBibs);
            Log.d(TAG, "Bibs saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving bibs: ", e);
            return false;
        }
    }
}
