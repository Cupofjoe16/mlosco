package com.pjlosco.eventtimer.bibs;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class BibEntry implements Serializable{

    private static final String JSON_ID = "id";
    private static final String JSON_PLACEMENT = "placement";

    private int bibIdNumber;
    private int finishedPlacement;

    public BibEntry(int bibIdNumber, int finishedPlacement) {
        this.bibIdNumber = bibIdNumber;
        this.finishedPlacement = finishedPlacement;
    }

    public BibEntry(JSONObject jsonObject) throws JSONException {
        bibIdNumber = Integer.parseInt(jsonObject.getString(JSON_ID));
        finishedPlacement = Integer.parseInt(jsonObject.getString(JSON_PLACEMENT));
    }

    public void setFinishedPlacement(int place) {
        this.finishedPlacement = place;
    }

    public int getBibIdNumber() {
        return bibIdNumber;
    }

    public int getFinishedPlacement() {
        return finishedPlacement;
    }

    @Override
    public String toString() {
        return bibIdNumber+"";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, bibIdNumber);
        json.put(JSON_PLACEMENT, finishedPlacement);
        return json;
    }

    @Override
    public boolean equals(Object object) {
        boolean result = false;
        if (object == null || object.getClass() != BibEntry.class) {
            result = false;
        } else {
            BibEntry newBib = (BibEntry) object;
            if (newBib.getBibIdNumber() == this.bibIdNumber && newBib.getFinishedPlacement() == this.finishedPlacement) {
                result = true;
            }
        }
        return result;
    }

}
