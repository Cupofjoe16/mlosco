package com.pjlosco.eventtimer.participants;

import android.content.Context;
import android.util.Log;

import com.pjlosco.eventtimer.data.EventTimerJSONSerializer;

import java.util.ArrayList;
import java.util.UUID;


public class ParticipantCatalogue {
    private static final String TAG = "ParticipantCollection";
    private static final String FILENAME = "participants.json";

    private static ParticipantCatalogue participantCatalogue;
    private Context mAppContext;

    private ArrayList<Participant> participants;
    private EventTimerJSONSerializer mSerializer;

    private ParticipantCatalogue(Context appContext) {
        mAppContext = appContext;
        mSerializer = new EventTimerJSONSerializer(mAppContext, FILENAME);
        try {
            participants = mSerializer.loadParticipants();
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

    public void addParticipant(Participant participant) {
        participants.add(participant);
    }

    public void deleteParticipant(Participant participant) {
        participants.remove(participant);
    }

    public boolean saveParticipants() {
        try {
            mSerializer.saveParticipants(participants);
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

}
