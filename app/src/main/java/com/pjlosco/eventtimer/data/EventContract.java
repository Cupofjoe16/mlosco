package com.pjlosco.eventtimer.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by patricklosco on 9/13/14.
 */
public class EventContract {

    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.pjlosco.android.eventtimer.app";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_PARTICIPANT = "participant";
    public static final String PATH_BIB_ORDER = "bib_order";
    public static final String PATH_TIMESTAMP = "timestamp";

    public static final class ParticipantEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PARTICIPANT).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_PARTICIPANT;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_PARTICIPANT;

        // Table name
        public static final String TABLE_NAME = "participant";

        public static final String COLUMN_FIRST_NAME = "first_name";
        public static final String COLUMN_LAST_NAME = "last_name";
        public static final String COLUMN_SEX = "sex";
        public static final String COLUMN_AGE = "age";
        public static final String COLUMN_BIB = "bib";
        public static final String COLUMN_TIME = "time";

        public static Uri buildParticipantUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class BibOrderEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_BIB_ORDER).build();
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_BIB_ORDER;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_BIB_ORDER;

        // Table name
        public static final String TABLE_NAME = "bib_order";

        public static final String COLUMN_BIB = "bib";

        public static Uri buildBibOrderUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class TimestampEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TIMESTAMP).build();
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_TIMESTAMP;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_TIMESTAMP;

        // Table name
        public static final String TABLE_NAME = "timestamp";

        public static final String COLUMN_TIME = "time";

        public static Uri buildTimestampUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
