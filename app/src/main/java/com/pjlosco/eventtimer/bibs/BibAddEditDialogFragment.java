package com.pjlosco.eventtimer.bibs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;

import com.pjlosco.eventtimer.R;

public class BibAddEditDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_bib_add_edit, null);

        if (savedInstanceState != null) {
            return new AlertDialog.Builder(getActivity())
                    .setView(v)
                    .setTitle(R.string.add_new_bib)
                    .setPositiveButton(android.R.string.ok, null)
                    .setNegativeButton(android.R.string.cancel, null)
                    .create();
        } else {
            return new AlertDialog.Builder(getActivity())
                    .setView(v)
                    .setTitle(R.string.edit_bib)
                    .setPositiveButton(android.R.string.ok, null)
                    .setNegativeButton(android.R.string.cancel, null)
                    .create();
        }
    }
}
