package com.pjlosco.eventtimer.results;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pjlosco.eventtimer.R;
import com.pjlosco.eventtimer.SettingsActivity;
import com.pjlosco.eventtimer.bibs.BibCatalogue;
import com.pjlosco.eventtimer.participants.Participant;
import com.pjlosco.eventtimer.participants.ParticipantCatalogue;

import java.util.ArrayList;

/**
 * Created by patricklosco on 1/16/15.
 */
public class ResultsListFragment extends Fragment {


    private static final String TAG = ResultsListFragment.class.getName();

    public ListView resultsListView;
    public BibCatalogue bibCatalogue;
    public ArrayList<Integer> orderedBibs;
    public ParticipantCatalogue participantCatalogue;
    private ResultsAdapter resultsAdapter;

    public ResultsListFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.title_activity_results_list);
        participantCatalogue = ParticipantCatalogue.get(getActivity());
        bibCatalogue = BibCatalogue.get(getActivity());
        orderedBibs = bibCatalogue.getOrderedBibs();
        resultsAdapter = new ResultsAdapter(orderedBibs);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_results_list, container, false);

        resultsListView = (ListView) rootView.findViewById(R.id.list);
        resultsListView.setAdapter(resultsAdapter);

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(getActivity(),SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private class ResultsAdapter extends ArrayAdapter<Integer> {

        public ResultsAdapter(ArrayList<Integer> bibs) {
            super(getActivity(), 0, bibs);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_results, null);
            }
            int bib = getItem(position);

            TextView bibNumberTextView = (TextView) convertView.findViewById(R.id.results_list_item_bib_number_textView);
            bibNumberTextView.setText(bib+"");

            TextView placementTextView = (TextView) convertView.findViewById(R.id.results_list_item_finish_placement_textView);
            placementTextView.setText(position+1+"");

            Participant participant = participantCatalogue.getParticipant(bib);
            TextView finishTimeTextView = (TextView) convertView.findViewById(R.id.results_list_item_finish_time_textView);
            finishTimeTextView.setText(participant.getFinishTime());

            TextView firstNameTextView = (TextView) convertView.findViewById(R.id.results_list_item_first_name_textView);
            firstNameTextView.setText(participant.getFirstName());
            TextView lastNameTextView = (TextView) convertView.findViewById(R.id.results_list_item_last_name_textView);
            lastNameTextView.setText(participant.getLastName());

            TextView ageTextView = (TextView) convertView.findViewById(R.id.results_list_item_age_textView);
            ageTextView.setText("Age: "+participant.getAge());
            TextView genderTextView = (TextView) convertView.findViewById(R.id.results_list_item_gender_textView);
            genderTextView.setText("Gender: "+(participant.getGender()+"").toUpperCase());

            return convertView;
        }
    }
}
