package com.pjlosco.eventtimer.bibs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.pjlosco.eventtimer.R;

public class BibAddEditDialogFragment extends DialogFragment {

    public static final String EXTRA_ADD_BIB = "com.pjlosco.eventtimer.addbib";
    public static final String EXTRA_ORIGNAL_BIB = "com.pjlosco.eventtimer.originalbib";
    public static final String EXTRA_PLACEMENT = "com.pjlosco.eventtimer.bibplacement";

    private int originalBib;
    private int originalPlacement;
    private int newBib;
    private int newPlacement;

    public static BibAddEditDialogFragment newInstance() {
        BibAddEditDialogFragment dialogFragment = new BibAddEditDialogFragment();
        return dialogFragment;
    }

    public static BibAddEditDialogFragment newInstance(int bibNumber) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_ORIGNAL_BIB, bibNumber);

        BibAddEditDialogFragment dialogFragment = new BibAddEditDialogFragment();
        dialogFragment.setArguments(args);

        return dialogFragment;
    }

    public static BibAddEditDialogFragment newInstance(int bibNumber, int bibPlacement) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_ORIGNAL_BIB, bibNumber);
        args.putSerializable(EXTRA_PLACEMENT, bibPlacement);

        BibAddEditDialogFragment dialogFragment = new BibAddEditDialogFragment();
        dialogFragment.setArguments(args);

        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_bib_add_edit, null);

        String title;
        final EditText bibTextInput = (EditText) v.findViewById(R.id.bibTextInput);
        final EditText placementTextInput = (EditText) v.findViewById(R.id.placementTextInput);
        try {
            originalBib = (Integer) getArguments().getSerializable(EXTRA_ORIGNAL_BIB);
            bibTextInput.setText(originalBib + "");
            try {
                originalPlacement = (Integer) getArguments().getSerializable(EXTRA_PLACEMENT);
                placementTextInput.setText(originalPlacement + "");
            } catch (NullPointerException e) {
                placementTextInput.setVisibility(View.INVISIBLE);
            }
            title = "Edit Bib";
        } catch (NullPointerException e) {
            title = "Add Bib";
            placementTextInput.setVisibility(View.INVISIBLE);
        }

        AlertDialog alert = new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            newBib = getEditedText(bibTextInput.getText().toString());
                            try {
                                newPlacement = getEditedText(placementTextInput.getText().toString());
                            } catch (Exception e) {
                                newPlacement = -1; // easy way to handle new entries
                            }
                            if (originalBib != newBib || originalPlacement != newPlacement) {
                                sendResult(Activity.RESULT_OK);
                            } else {
                                sendResult(Activity.RESULT_CANCELED);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            sendResult(Activity.RESULT_CANCELED);
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
        alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return alert;
    }
    private int getEditedText(String text) {
        int value = 0;
        if (!text.isEmpty()) {
            try {
                value = Integer.parseInt(text);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    @Override
    public void onPause() {
        super.onPause();
        BibCatalogue.get(getActivity()).saveBibs();
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ADD_BIB, newBib);
        intent.putExtra(EXTRA_ORIGNAL_BIB, originalBib);
        intent.putExtra(EXTRA_PLACEMENT, newPlacement);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
