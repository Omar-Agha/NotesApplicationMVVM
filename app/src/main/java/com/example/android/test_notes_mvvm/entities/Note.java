package com.example.android.test_notes_mvvm.entities;

import android.content.ContentValues;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes_table")
public class Note {

    public static final String NOTE_ID_KEY = "note.id";
    public static final String NOTE_TITLE_KEY = "note.title";
    public static final String NOTE_DESCRIPTION_KEY ="note.description";
    public static final String NOTE_PRIORITY_KEY = "note.priority";

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String description;

    private int priority;

    public Note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public static Note FromContentValues(ContentValues contentValues) {

        int id = contentValues.getAsInteger(NOTE_ID_KEY);
        String title = contentValues.getAsString(NOTE_TITLE_KEY);
        String description = contentValues.getAsString(NOTE_DESCRIPTION_KEY);
        int priority = contentValues.getAsInteger(NOTE_PRIORITY_KEY);
        Note note = new Note(title,description,priority);
        note.setId(id);
        return note;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }
}
