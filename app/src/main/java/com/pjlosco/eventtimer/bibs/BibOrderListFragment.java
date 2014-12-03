package com.pjlosco.eventtimer.bibs;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pjlosco.eventtimer.R;
import com.pjlosco.eventtimer.SettingsActivity;

import java.util.ArrayList;

public class BibOrderListFragment extends Fragment {

    private static final String TAG = BibOrderListFragment.class.getName();

    public Button addNewBibButton;
    public ListView bibListView;
    public ArrayList<BibEntry> enteredBibs;
    private BibAdapter bibAdapter;

    private BibEntry editedBibEntry;

    private static final String BIB_DIALOG = "bib dialog";
    private static final int ADD_BIB = 0;
    private static final int EDIT_BIB = 1;
    private static final int JUMP_TO_BIB = 2;

    public BibOrderListFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle args = getArguments();
        if (args != null) {
            // probably should keep location in list, in case rotation occurs, to not be in a different spot
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.title_activity_bib_order_list);
        enteredBibs = BibCatalogue.get(getActivity()).getBibs();
        bibAdapter = new BibAdapter(enteredBibs);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bib_order_list, container, false);

        bibListView = (ListView) rootView.findViewById(R.id.list);

        bibListView.setAdapter(bibAdapter);
        bibListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editedBibEntry = enteredBibs.get(position);
                FragmentManager fragmentManager = getActivity().getFragmentManager();
                BibAddEditDialogFragment dialogFragment = BibAddEditDialogFragment.newInstance(editedBibEntry);
                dialogFragment.setTargetFragment(BibOrderListFragment.this, EDIT_BIB);
                dialogFragment.show(fragmentManager, BIB_DIALOG);
            }
        });


        addNewBibButton = (Button) rootView.findViewById(R.id.addBibNumber);
        addNewBibButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getFragmentManager();
                BibAddEditDialogFragment dialogFragment = BibAddEditDialogFragment.newInstance();
                dialogFragment.setTargetFragment(BibOrderListFragment.this, ADD_BIB);
                dialogFragment.show(fragmentManager, BIB_DIALOG);
            }
        });

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(getActivity(),SettingsActivity.class));
            return true;
        }
        else if (id == R.id.action_jump_to_bib) {
            // TODO fix this up later
            FragmentManager fragmentManager = getActivity().getFragmentManager();
            BibAddEditDialogFragment dialogFragment = BibAddEditDialogFragment.newInstance();
            dialogFragment.setTargetFragment(BibOrderListFragment.this, JUMP_TO_BIB);
            dialogFragment.show(fragmentManager, BIB_DIALOG);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == ADD_BIB) {
            BibEntry newBib = (BibEntry)data.getSerializableExtra(BibAddEditDialogFragment.EXTRA_ADD_BIB);
            newBib.setFinishedPlacement(enteredBibs.size()+1);
            enteredBibs.add(newBib);
        }
        else if (requestCode == EDIT_BIB) {
            BibEntry originalBib = (BibEntry)data.getSerializableExtra(BibAddEditDialogFragment.EXTRA_ORIGNAL_BIB);
            BibEntry newBib = (BibEntry)data.getSerializableExtra(BibAddEditDialogFragment.EXTRA_ADD_BIB);

            if (enteredBibs.contains(newBib)) {
                // TODO - not right. See if arraylist already has the bib in any placement
                Toast.makeText(this.getActivity(), R.string.duplicate_bib_found, Toast.LENGTH_SHORT).show();
            }

            enteredBibs.remove(originalBib);
            if (newBib.getFinishedPlacement() > enteredBibs.size()) {
                newBib.setFinishedPlacement(enteredBibs.size()+1);
                enteredBibs.add(newBib);
            } else {
                if (originalBib.getFinishedPlacement() != newBib.getFinishedPlacement()) {
                    // TODO - Update all placements of records from in between old and new bib
                }

                enteredBibs.add(newBib.getFinishedPlacement() - 1, newBib);
            }
        }
        else if (requestCode == JUMP_TO_BIB) {
            // update adapter view with new position
        }
        bibAdapter.notifyDataSetChanged();
    }

    private class BibAdapter extends ArrayAdapter<BibEntry> {

        public BibAdapter(ArrayList<BibEntry> bibs) {
            super(getActivity(), 0, bibs);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_bib, null);
            }
            BibEntry bib = getItem(position);

            TextView positionTextView = (TextView) convertView.findViewById(R.id.position_list_item_textView);
            positionTextView.setText(bib.getFinishedPlacement()+"");
            TextView bibNumberTextView = (TextView) convertView.findViewById(R.id.bib_number_list_item_textView);
            bibNumberTextView.setText(bib.getBibIdNumber()+"");
            return convertView;
        }
    }
}
