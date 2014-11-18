package com.pjlosco.eventtimer.bibs;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import com.pjlosco.eventtimer.R;
import com.pjlosco.eventtimer.SettingsActivity;

import java.util.ArrayList;

public class BibOrderListFragment extends Fragment {

    public Button addNewBibButton;
    public ListView bibListView;
    public int currentBibIndex = 0;
    public ArrayList<Integer> enteredBibs = new ArrayList<Integer>();

    private static final String ADD_BIB = "add bib";
    private static final String JUMP_TO_BIB = "Jump to bib";

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bib_order_list, container, false);

        addNewBibButton = (Button) rootView.findViewById(R.id.addBibNumber);
        addNewBibButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getFragmentManager();
                BibAddEditDialogFragment dialogFragment = new BibAddEditDialogFragment();
                dialogFragment.show(fragmentManager, ADD_BIB);
            }
        });

        bibListView = (ListView) rootView.findViewById(R.id.bibListView);

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
            // TODO - open dialog for jump_to_bib, change cursor position to entered number
            FragmentManager fragmentManager = getActivity().getFragmentManager();
            DialogFragment dialogFragment = new DialogFragment();
            dialogFragment.show(fragmentManager, JUMP_TO_BIB);
        }
        return super.onOptionsItemSelected(item);
    }

}
