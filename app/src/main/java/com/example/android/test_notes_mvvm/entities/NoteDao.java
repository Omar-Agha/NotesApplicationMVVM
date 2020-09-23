package com.example.android.test_notes_mvvm.entities;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    long insertNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Query("SELECT * FROM notes_table ORDER BY priority DESC")
    LiveData<List<Note>> getNotes(); // LiveData

    @Query("DELETE FROM notes_table")
    void deleteAllNotes();

    @Update
    void updateNote(Note note);
}
