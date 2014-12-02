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

import com.pjlosco.eventtimer.R;
import com.pjlosco.eventtimer.SettingsActivity;

import java.util.ArrayList;

public class BibOrderListFragment extends Fragment {

    private static final String TAG = BibOrderListFragment.class.getName();

    public Button addNewBibButton;
    public ListView bibListView;
    public ArrayList<BibEntry> enteredBibs;
    private BibAdapter bibAdapter;

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
                TextView currentBibNumberTextView = (TextView) view.findViewById(R.id.bib_number_list_item_textView);
                int currentBibNumber = Integer.parseInt(currentBibNumberTextView.getText().toString());
                FragmentManager fragmentManager = getActivity().getFragmentManager();
                BibAddEditDialogFragment dialogFragment = BibAddEditDialogFragment.newInstance(currentBibNumber);
                dialogFragment.setTargetFragment(BibOrderListFragment.this, EDIT_BIB);
                dialogFragment.show(fragmentManager, BIB_DIALOG);
                bibAdapter.notifyDataSetChanged();
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
                bibAdapter.notifyDataSetChanged();
            }
        });

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(getActivity(),SettingsActivity.class));
            return true;
        }
        else if (id == R.id.action_jump_to_bib) {
            FragmentManager fragmentManager = getActivity().getFragmentManager();
            BibAddEditDialogFragment dialogFragment = BibAddEditDialogFragment.newInstance(0);
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
            enteredBibs.add(new BibEntry((Integer)data.getSerializableExtra(BibAddEditDialogFragment.EXTRA_ADD_BIB), enteredBibs.size()+1));
        }
        else if (requestCode == EDIT_BIB) {
            int originalBib = (Integer)data.getSerializableExtra(BibAddEditDialogFragment.EXTRA_ORIGNAL_BIB);
            int newBib = (Integer)data.getSerializableExtra(BibAddEditDialogFragment.EXTRA_ADD_BIB);
            int position = enteredBibs.indexOf(originalBib);
            enteredBibs.remove(position);
            enteredBibs.add(position, new BibEntry(newBib, position+1));
        }
        else if (requestCode == JUMP_TO_BIB) {
            // update adapter view with new position
        }
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
