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
    public static final String EXTRA_POSITION = "com.pjlosco.eventtimer.bibposition";

    private BibEntry originalBib;
    private BibEntry newBib;

    public static BibAddEditDialogFragment newInstance() {
        BibAddEditDialogFragment dialogFragment = new BibAddEditDialogFragment();
        return dialogFragment;
    }

    public static BibAddEditDialogFragment newInstance(BibEntry bibEntry) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_ORIGNAL_BIB, bibEntry);

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
            originalBib = (BibEntry) getArguments().getSerializable(EXTRA_ORIGNAL_BIB);
            bibTextInput.setText(originalBib.getBibIdNumber() + "");
            placementTextInput.setText(originalBib.getFinishedPlacement() + "");
            title = "Edit Bib";
        } catch (NullPointerException e) {
            title = "Add Bib";
        }

        AlertDialog alert = new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            int bib = getEditedText(bibTextInput.getText().toString());
                            int place = getEditedText(placementTextInput.getText().toString());
                            newBib = new BibEntry(bib, place);
                            if (originalBib == null || originalBib != newBib) {
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
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
