package com.pjlosco.eventtimer.participants;

import com.pjlosco.eventtimer.bibs.BibEntry;

/**
 * Created by patricklosco on 11/1/14.
 */
public class Participant {

    private int id;
    private String firstName;
    private String lastName;
    private char sex;
    private int age;
    private BibEntry bibEntry;
    private int finishTime;

    public Participant(String firstName, String lastName, char sex, int age) {
        // TODO set ID
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.age = age;
    }

    public void editParticipant(String firstName, String lastName, char sex, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.age = age;
    }

    public void setBibEntry(BibEntry bibEntry) {
        this.bibEntry = bibEntry;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public char getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public BibEntry getBibEntry() {
        return bibEntry;
    }

    public int getFinishTime() {
        return finishTime;
    }
}
