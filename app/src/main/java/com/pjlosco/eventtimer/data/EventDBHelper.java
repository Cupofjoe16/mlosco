package com.pjlosco.eventtimer.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.pjlosco.eventtimer.data.EventContract.*;

/**
 * Created by patricklosco on 9/13/14.
 */
public class EventDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "event.db";

    final String SQL_CREATE_PARTICIPANT_TABLE =
            "CREATE TABLE " + ParticipantEntry.TABLE_NAME + " ( " +
                    ParticipantEntry._ID + " INTEGER PRIMARY KEY, " +
                    ParticipantEntry.COLUMN_FIRST_NAME + " TEXT NOT NULL, " +
                    ParticipantEntry.COLUMN_LAST_NAME + " TEXT NOT NULL, " +
                    ParticipantEntry.COLUMN_GENDER + " TEXT NOT NULL, " +
                    ParticipantEntry.COLUMN_AGE + " INTEGER NOT NULL, " +
                    ParticipantEntry.COLUMN_BIB + " INTEGER NOT NULL, " +
                    ParticipantEntry.COLUMN_TIME + " INTEGER NOT NULL, " + // TODO check to see if this needs to be a double
                    "FOREIGN KEY (" + ParticipantEntry.COLUMN_TIME + ") REFERENCES " +
                    TimestampEntry.TABLE_NAME + " (" + TimestampEntry.COLUMN_TIME + "), " +
                    "UNIQUE (" + ParticipantEntry.COLUMN_BIB + ") ON CONFLICT IGNORE);";

    final String SQL_CREATE_BIB_ORDER_TABLE =
            "CREATE TABLE " + BibOrderEntry.TABLE_NAME + " ( " +
                    BibOrderEntry._ID + " INTEGER PRIMARY KEY, " +
                    BibOrderEntry.COLUMN_BIB + " INTEGER NOT NULL, " +
                    "FOREIGN KEY (" + BibOrderEntry.COLUMN_BIB + ") REFERENCES " +
                    ParticipantEntry.TABLE_NAME + " (" + ParticipantEntry.COLUMN_BIB + ");";

    final String SQL_CREATE_TIMESTAMP_TABLE =
            "CREATE TABLE " + TimestampEntry.TABLE_NAME + " ( " +
                    TimestampEntry._ID + " INTEGER PRIMARY KEY, " +
                    TimestampEntry.COLUMN_TIME + " REAL NOT NULL);";

    public EventDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PARTICIPANT_TABLE);
        db.execSQL(SQL_CREATE_BIB_ORDER_TABLE);
        db.execSQL(SQL_CREATE_TIMESTAMP_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
