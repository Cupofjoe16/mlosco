package com.pjlosco.eventtimer.bibs;

/**
 * Created by patricklosco on 11/1/14.
 */
public class BibEntry {

    private int bibIdNumber;
    private int finishedPlacement;

    BibEntry(int bibIdNumber) {
        this.bibIdNumber = bibIdNumber;
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
}
