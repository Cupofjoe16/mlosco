package com.pjlosco.eventtimer.participants;

import com.pjlosco.eventtimer.bibs.BibCatalogue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class Participant {

    private static final String JSON_ID = "id";
    private static final String JSON_FIRSTNAME = "firstname";
    private static final String JSON_LASTNAME = "lastname";
    private static final String JSON_GENDER = "gender";
    private static final String JSON_AGE = "age";
    private static final String JSON_BIB = "bib";
    private static final String JSON_TIME = "time";

    private UUID id;
    private String firstName;
    private String lastName;
    private char gender;
    private int age;
    private int bibNumber;
    private String finishTime = "00:00:00";

    public Participant() {
        this.id = UUID.randomUUID();
    }

    public Participant(String firstName, String lastName, char gender, int age) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
    }

    public void editParticipant(String firstName, String lastName, char gender, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
    }

    public void setBibNumber(int bibNumber) {
        this.bibNumber = bibNumber;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public char getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getBibNumber() {
        // WARNING: this could be null.
        return bibNumber;
    }

    public int getFinishedPlacement() {
        return BibCatalogue.getBibCatalogue().getOrderedBibs().indexOf(bibNumber);
    }

    public String getFinishTime() {
        return finishTime;
    }

    public Participant(JSONObject jsonObject) throws JSONException {
        id = UUID.fromString(jsonObject.getString((JSON_ID)));
        if (jsonObject.has(JSON_FIRSTNAME)) {
            firstName = jsonObject.getString(JSON_FIRSTNAME);
        }
        if (jsonObject.has(JSON_LASTNAME)) {
            lastName = jsonObject.getString(JSON_LASTNAME);
        }
        if (jsonObject.has(JSON_GENDER)) {
            gender = jsonObject.getString(JSON_GENDER).charAt(0);
        }
        if (jsonObject.has(JSON_AGE)) {
            age = Integer.parseInt(jsonObject.getString(JSON_AGE));
        }
        if (jsonObject.has(JSON_BIB)) {
            bibNumber = Integer.parseInt((jsonObject.getString(JSON_BIB)));
        }
        if (jsonObject.has(JSON_TIME)) {
            finishTime = jsonObject.getString(JSON_TIME);
        }
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, id.toString());
        json.put(JSON_FIRSTNAME, firstName);
        json.put(JSON_LASTNAME, lastName);
        json.put(JSON_GENDER, gender);
        json.put(JSON_AGE, age);
        json.put(JSON_BIB, bibNumber);
        json.put(JSON_TIME, finishTime);
        return json;
    }
}
