package com.pjlosco.eventtimer.settings;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

import com.pjlosco.eventtimer.R;
import com.pjlosco.eventtimer.bibs.BibCatalogue;
import com.pjlosco.eventtimer.participants.ParticipantCatalogue;

/**
 * Created by patricklosco on 3/3/15.
 */
public class ResetAppPreference extends DialogPreference {

    public ResetAppPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        setDialogLayoutResource(R.layout.dialog_reset_app);
        setPositiveButtonText(android.R.string.ok);
        setNegativeButtonText(android.R.string.cancel);

        setDialogIcon(null);
    }

    protected void onDialogClosed(boolean positiveResult) {
        // When the user selects "OK", persist the new value
        if (positiveResult) {
            try {
                BibCatalogue.getBibCatalogue().clearBibCatalogue();
                ParticipantCatalogue.getParticipantCatalogue().clearParticipantCatalogue();
            } catch (NullPointerException e) {
                // TODO - No catalogues to clear. Need to open those activities before clear works.
            }
        }
    }

}
