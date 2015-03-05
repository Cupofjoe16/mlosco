package com.pjlosco.eventtimer.participants;

import android.content.Context;
import android.util.Log;

import com.pjlosco.eventtimer.DuplicateBibEntryException;
import com.pjlosco.eventtimer.data.EventTimerJSONSerializer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


public class ParticipantCatalogue {
    private static final String TAG = "ParticipantCollection";
    private static final String FILENAME_PARTICIPANTS = "participants.json";

    private static ParticipantCatalogue participantCatalogue;
    private Context mAppContext;

    private static ArrayList<Participant> participants;
    private static Set<Integer> participantBibs = new HashSet<Integer>();
    private EventTimerJSONSerializer participantSerializer;

    private ParticipantCatalogue(Context appContext) {
        mAppContext = appContext;
        participantSerializer = new EventTimerJSONSerializer(mAppContext, FILENAME_PARTICIPANTS);

        try {
            participants = participantSerializer.loadParticipants();
            for(Participant participant : participants) {
                participantBibs.add(participant.getBibNumber());
            }
        } catch (Exception e) {
            participants = new ArrayList<Participant>();
            Log.e(TAG, "Error loading participants: ", e);
        }
    }

    public static ParticipantCatalogue get(Context context) {
        if (participantCatalogue == null) {
            participantCatalogue = new ParticipantCatalogue(context.getApplicationContext());
        }
        return participantCatalogue;
    }

    public static ParticipantCatalogue getParticipantCatalogue() {
        return participantCatalogue;
    }

    public void addParticipant(Participant participant) throws DuplicateBibEntryException {
        addParticipantBib(participant.getBibNumber());
        participants.add(participant);
    }

    public void deleteParticipant(Participant participant) {
        removeParticipantBib(participant.getBibNumber());
        participants.remove(participant);
    }

    public boolean saveParticipants() {
        try {
            participantSerializer.saveParticipants(participants);
            Log.d(TAG, "Participants saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving participants: ", e);
            return false;
        }
    }

    public ArrayList<Participant> getParticipants(){
        if (participants == null) {
            participants = new ArrayList<Participant>();
        }
        return participants;
    }

    public Participant getParticipant(UUID uuid) {
        for (Participant participant : participants) {
            if (participant.getId().equals(uuid)) {
                return participant;
            }
        }
        return null;
    }
    public Participant getParticipant(int bibNumber) {
        for (Participant participant : participants) {
            if (participant.getBibNumber() == bibNumber) {
                return participant;
            }
        }
        Participant newParticipant = new Participant("Unknown", "Participant");
        return newParticipant;
    }

    public Set<Integer> getParticipantBibs(){
        return participantBibs;
    }

    public void addParticipantBib(int bibNumber) throws DuplicateBibEntryException {
        if (!participantBibs.contains(bibNumber) || bibNumber == 0) {
            participantBibs.add(bibNumber);
        } else {
            throw new DuplicateBibEntryException("Bib already entered");
        }
    }

    public boolean removeParticipantBib(int bibNumber) {
        if (participantBibs.contains(bibNumber)) {
            participantBibs.remove(new Integer(bibNumber));
            return true;
        }
        return false;
    }

    public static void updateParticipantBibNumber(Participant participant, int newBibNumber) throws DuplicateBibEntryException {
        if (!participantBibs.contains(newBibNumber)) {
            participantBibs.remove(participant.getBibNumber());
            participantBibs.add(newBibNumber);
            participant.setBibNumber(newBibNumber);
        } else {
            throw new DuplicateBibEntryException("Bib already entered");
        }
    }

    public void clearParticipantCatalogue() {
        participantBibs = new HashSet<Integer>();
        participants = new ArrayList<Participant>();
        saveParticipants();
    }
}
