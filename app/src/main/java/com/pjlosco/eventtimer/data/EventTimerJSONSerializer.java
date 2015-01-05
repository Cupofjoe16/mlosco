package com.pjlosco.eventtimer.data;

import android.content.Context;

import com.pjlosco.eventtimer.participants.Participant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by Patrick on 12/1/2014.
 */
public class EventTimerJSONSerializer {

    private static final String JSON_ID = "id";

    private Context mContext;
    private String mFilename;

    public EventTimerJSONSerializer(Context context, String filename) {
        mContext = context;
        mFilename = filename;
    }

    public ArrayList<Integer> loadBibs() throws IOException, JSONException {
        ArrayList<Integer> bibEntries = new ArrayList<Integer>();
        BufferedReader reader = null;
        try {
            InputStream inputStream = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            for (int index = 0; index < array.length(); index++) {
                bibEntries.add(Integer.parseInt(array.getJSONObject(index).getString(JSON_ID)));
            }
        } catch (FileNotFoundException e) {
            // do nothing and return the empty list
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return bibEntries;
    }

    public void saveBibs(ArrayList<Integer> bibs) throws JSONException, IOException {
        JSONArray array = new JSONArray();
        for (int bibIdNumber : bibs) {
            JSONObject json = new JSONObject();
            json.put(JSON_ID, bibIdNumber);
            array.put(json);
        }
        Writer writer = null;
        try {
            OutputStream outputStream = mContext.openFileOutput(mFilename,Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(outputStream);
            writer.write(array.toString());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public ArrayList<Participant> loadParticipants() throws IOException, JSONException {
        return null; // TODO
    }

    public void saveParticipants(ArrayList<Participant> participants) throws IOException, JSONException {
        // TODO
    }
}
