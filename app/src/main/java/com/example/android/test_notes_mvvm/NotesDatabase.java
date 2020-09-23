package com.example.android.test_notes_mvvm;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.android.test_notes_mvvm.entities.Note;
import com.example.android.test_notes_mvvm.entities.NoteDao;

@Database(entities = {Note.class} , version = 1)
public abstract class NotesDatabase extends RoomDatabase {

    private static NotesDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NotesDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context,NotesDatabase.class,"databse")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
