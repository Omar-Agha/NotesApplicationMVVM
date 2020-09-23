package com.example.android.test_notes_mvvm.dataProvider;

import android.net.Uri;
import android.provider.BaseColumns;

public abstract class DataContract {

    public static final String AUTHORITY = "com.example.android.test_notes_mvvm";

    public static final Uri BASE_URI = Uri.parse("content://"+AUTHORITY);

    public static final String NOTES_PATH = "notes";

    public static final Uri NOTES_CONTENT = BASE_URI.buildUpon().appendPath(NOTES_PATH).build();

    public static abstract class NotesTable implements BaseColumns {
        public static final String TITEL_COLUMN ="title";
        public static final String DESCRIPTION_COLUMN = "description";
        public static final String PRIORITY_COLUMN = "priority";
        public static final String TABLE_NAME = "notes_table";
    }
}
