package com.pjlosco.eventtimer.data;

import com.pjlosco.eventtimer.participants.Participant;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVReader {

    public CSVReader(String filename) {
    }

    public ArrayList<Participant> parseParticipants(String filename, ArrayList<Participant> currentParticipants) throws FileNotFoundException, IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filename));
            String line;

            while ((line = br.readLine()) != null) {
                String[] participantData = line.split(",");
                Participant participant = new Participant();
                participant.setBibNumber(Integer.parseInt(participantData[0]));
                participant.setFirstName(participantData[1]);
                participant.setLastName(participantData[2]);
                participant.setGender(participantData[3].charAt(0));
                participant.setAge(Integer.parseInt(participantData[4]));
                currentParticipants.add(participant);
            }
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return currentParticipants;
    }
}
