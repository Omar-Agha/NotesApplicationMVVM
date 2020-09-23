package com.example.android.test_notes_mvvm.dataProvider;

import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;


import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.example.android.test_notes_mvvm.NotesDatabase;
import com.example.android.test_notes_mvvm.entities.Note;
import com.example.android.test_notes_mvvm.entities.NoteDao;
import com.example.android.test_notes_mvvm.repositories.NotesRepository;

public class NotesProvider extends ContentProvider {



    public static final int NOTES_PATH_CODE = 100;

    public static final int NOTES_ID_PATH_CODE = 101;

    //for UriMather
    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    static{
        matcher.addURI(DataContract.AUTHORITY,DataContract.NOTES_PATH,NOTES_PATH_CODE);
        matcher.addURI(DataContract.AUTHORITY,DataContract.NOTES_PATH + "/#",NOTES_ID_PATH_CODE);

    }


    private NotesDatabase db;

    @Override
    public boolean onCreate() {
        Context context =getContext().getApplicationContext();
        db = NotesDatabase.getInstance(context);


        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection,String selection,String[] selectionArgs, String sortOrder) {

        String sql = "SELECT "+DataContract.NotesTable.TABLE_NAME;
        if(projection == null)
            sql+=" * ";
        else {

        }


        return null;
    }



    @Override
    public String getType(Uri uri) {

        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        Uri returnedUri;
        switch (matcher.match(uri))
        {
            case NOTES_PATH_CODE :
                Note note = Note.FromContentValues(contentValues);
//                long id = repository.insertNote(note);
                returnedUri = ContentUris.withAppendedId(DataContract.BASE_URI,5);
                break;

            default:
                //throw an error not valid uri
                throw new UnsupportedOperationException("invalid uri");

        }
        getContext().getContentResolver().notifyChange(uri,null);
        return returnedUri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
