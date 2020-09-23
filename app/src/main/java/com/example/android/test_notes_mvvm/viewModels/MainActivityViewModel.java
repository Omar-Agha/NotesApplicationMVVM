package com.example.android.test_notes_mvvm.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.test_notes_mvvm.entities.Note;
import com.example.android.test_notes_mvvm.repositories.NotesRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {


    private LiveData<List<Note>> mNotes;
    private NotesRepository mNoteRepo;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        mNoteRepo = new NotesRepository(application);
        mNotes  = mNoteRepo.getAllNotes();
    }

    public void insert(Note note)
    {
        mNoteRepo.insertNote(note);
    }

    public void delete(Note note){
        mNoteRepo.deleteNote(note);
    }

    public void deleteAll(){
        mNoteRepo.deleteAllNotes();
    }

    public void update(Note note)
    {
        mNoteRepo.updateNote(note);
    }

    public LiveData<List<Note>> getNotes(){
        return mNotes;
    }
}
