package com.pjlosco.eventtimer.participants;


import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pjlosco.eventtimer.R;

import java.util.ArrayList;


public class ParticipantListFragment extends ListFragment {
    private ArrayList<Participant> participants;
    private static final String TAG = ParticipantListFragment.class.getName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        getActivity().setTitle(R.string.participants_title);
        participants = ParticipantCatalogue.get(getActivity()).getParticipants();

        ParticipantAdapter adapter = new ParticipantAdapter(participants);
        setListAdapter(adapter);
        setRetainInstance(true);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Participant participant = ((ParticipantAdapter) getListAdapter()).getItem(position);
        Intent intent = new Intent(getActivity(), ParticipantPagerActivity.class);
        intent.putExtra(ParticipantFragment.EXTRA_PARTICIPANT_ID, participant.getId());
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((ParticipantAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_participant_list, menu);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_participant:
                addParticipant();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.participant_list_item_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        ParticipantAdapter adapter = (ParticipantAdapter) getListAdapter();
        Participant participant = adapter.getItem(position);

        switch (item.getItemId()) {
            case R.id.menu_item_delete_participant:
                ParticipantCatalogue.get(getActivity()).deleteParticipant(participant);
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, parent, savedInstanceState);
        ListView listView = (ListView) view.findViewById(android.R.id.list);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            registerForContextMenu(listView);
        } else {
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                @Override
                public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                }

                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    MenuInflater menuInflater = mode.getMenuInflater();
                    menuInflater.inflate(R.menu.participant_list_item_context, menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_item_delete_participant:
                            ParticipantAdapter adapter = (ParticipantAdapter) getListAdapter();
                            ParticipantCatalogue participantCatalogue = ParticipantCatalogue.get(getActivity());
                            for (int i = adapter.getCount() - 1; i >= 0; i--) {
                                if (getListView().isItemChecked(i)) {
                                    participantCatalogue.deleteParticipant(adapter.getItem(i));
                                }
                            }
                            mode.finish();
                            adapter.notifyDataSetChanged();
                            return true;
                        default:
                            return false;
                    }
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                }
            });
        }
        return view;
    }

    private void addParticipant() {
        Participant participant = new Participant();
        ParticipantCatalogue.get(getActivity()).addParticipant(participant);
        Intent i = new Intent(getActivity(), ParticipantPagerActivity.class);
        i.putExtra(ParticipantFragment.EXTRA_PARTICIPANT_ID, participant.getId());
        startActivityForResult(i, 0);
    }

    private class ParticipantAdapter extends ArrayAdapter<Participant> {

        public ParticipantAdapter(ArrayList<Participant> participants) {
            super(getActivity(), 0, participants);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_participant, null);
            }
            Participant participant = getItem(position);

            TextView bibNumberTextView = (TextView) convertView.findViewById(R.id.participant_list_item_bib_number_textView);
            TextView placementTextView = (TextView) convertView.findViewById(R.id.participant_list_item_finish_placement_textView);
            if (participant.getBibNumber() > 0) {
                bibNumberTextView.setText(participant.getBibNumber()+"");
                int placement = participant.getFinishedPlacement();
                if (placement != 0) {
                    placementTextView.setText("Placed: " + placement);
                } else {
                    placementTextView.setText("Not Finished");
                }
            } else {
                bibNumberTextView.setText("?");
                placementTextView.setText("Not Finished");
            }
            TextView finishTimeTextView = (TextView) convertView.findViewById(R.id.participant_list_item_finish_time_textView);
            finishTimeTextView.setText(participant.getFinishTime());

            TextView firstNameTextView = (TextView) convertView.findViewById(R.id.participant_list_item_first_name_textView);
            firstNameTextView.setText(participant.getFirstName());
            TextView lastNameTextView = (TextView) convertView.findViewById(R.id.participant_list_item_last_name_textView);
            lastNameTextView.setText(participant.getLastName());

            TextView ageTextView = (TextView) convertView.findViewById(R.id.participant_list_item_age_textView);
            ageTextView.setText(participant.getAge()+"");
            TextView genderTextView = (TextView) convertView.findViewById(R.id.participant_list_item_gender_textView);
            genderTextView.setText(participant.getGender()+"");

            return convertView;
        }
    }

}
