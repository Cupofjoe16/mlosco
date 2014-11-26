package com.pjlosco.eventtimer.bibs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.pjlosco.eventtimer.R;

public class BibAddEditDialogFragment extends DialogFragment {

    public static final String EXTRA_ADD_BIB = "com.pjlosco.eventtimer.addbib";
    public static final String EXTRA_ORIGNAL_BIB = "com.pjlosco.eventtimer.originalbib";
    public static final String EXTRA_POSITION = "com.pjlosco.eventtimer.bibposition";

    private int originalBibNumber;
    private int newBibNumber;
    private int position =0;

    public static BibAddEditDialogFragment newInstance(int bibNumber) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_ADD_BIB, bibNumber);

        BibAddEditDialogFragment dialogFragment = new BibAddEditDialogFragment();
        dialogFragment.setArguments(args);

        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_bib_add_edit, null);

        originalBibNumber = (Integer) getArguments().getSerializable(EXTRA_ADD_BIB);

        final EditText bibTextInput = (EditText) v.findViewById(R.id.bibTextInput);
        bibTextInput.setText(originalBibNumber+"");

        String title;
        if (originalBibNumber != 0) {
            title = "Add Bib";
        } else {
            title = "Edit Bib";
        }

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            newBibNumber = Integer.parseInt(bibTextInput.getText().toString());
                            sendResult(Activity.RESULT_OK);
                        } catch (Exception e) {
                            e.printStackTrace();
                            sendResult(Activity.RESULT_CANCELED);
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ADD_BIB, newBibNumber);
        intent.putExtra(EXTRA_ORIGNAL_BIB, originalBibNumber);
        intent.putExtra(EXTRA_POSITION, position);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
