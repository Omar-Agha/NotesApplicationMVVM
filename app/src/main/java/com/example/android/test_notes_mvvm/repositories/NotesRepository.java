package com.example.android.test_notes_mvvm.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.android.test_notes_mvvm.NotesDatabase;
import com.example.android.test_notes_mvvm.entities.Note;
import com.example.android.test_notes_mvvm.entities.NoteDao;

import java.util.List;

public class NotesRepository {

    private NoteDao noteDao;


    public NotesRepository(Application application) {
        noteDao = NotesDatabase.getInstance(application).noteDao();

    }

    public void insertNote(Note note) {
        new InsertNoteAsync(noteDao).execute(note);
//        return noteDao.insertNote(note);
    }

    public void deleteNote(Note note) {
        new DeleteNoteAsync(noteDao).execute(note);
    }

    public void updateNote(Note note) {
        new UpdateNoteAsync(noteDao).execute(note);
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsync(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes() {
        return noteDao.getNotes();
    }


    private static class InsertNoteAsync extends AsyncTask<Note, Void, Long> {
        private NoteDao noteDao;

        private InsertNoteAsync(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Long doInBackground(Note... notes) {
            long id = noteDao.insertNote(notes[0]);
            return id;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
        }
    }

    private static class DeleteNoteAsync extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private DeleteNoteAsync(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.deleteNote(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsync extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private UpdateNoteAsync(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.updateNote(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsync extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;

        private DeleteAllNotesAsync(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }


}
