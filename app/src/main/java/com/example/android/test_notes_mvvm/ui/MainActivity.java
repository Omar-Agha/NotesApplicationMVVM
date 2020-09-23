package com.example.android.test_notes_mvvm.ui;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.test_notes_mvvm.R;
import com.example.android.test_notes_mvvm.adapters.NotesAdapter;
import com.example.android.test_notes_mvvm.entities.Note;
import com.example.android.test_notes_mvvm.viewModels.MainActivityViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;
    private static final int EDIT_NOTE_REQUEST = 2;
    private MainActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.add_note_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddEditNoteActivity.class);
                startActivityForResult(i, ADD_NOTE_REQUEST);

            }
        });
        RecyclerView mNotesRc = findViewById(R.id.notes_rc);
        mNotesRc.setLayoutManager(new LinearLayoutManager(this));
        mNotesRc.setHasFixedSize(true);
        final NotesAdapter adapter = new NotesAdapter();
        mNotesRc.setAdapter(adapter);

        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        mViewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                //update adapter
                Toast.makeText(getBaseContext(), "changed", Toast.LENGTH_SHORT).show();
                adapter.submitList(notes);
            }
        });

        adapter.setOnClickItem(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClicked(Note note) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE,note.getTitle());
                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION,note.getDescription());
                intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY,note.getPriority());
                intent.putExtra(AddEditNoteActivity.EXTRA_ID,note.getId());

                startActivityForResult(intent,EDIT_NOTE_REQUEST);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Toast.makeText(MainActivity.this, "swiped", Toast.LENGTH_SHORT).show();
                Note note = adapter.getNoteAt(viewHolder.getAdapterPosition());
                mViewModel.delete(note);
            }
        }).attachToRecyclerView(mNotesRc);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.delete_all_notes_action):
                mViewModel.deleteAll();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 2);
            Note note = new Note(title, description, priority);
            mViewModel.insert(note);
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);
            int id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID,-1);
            if(id == -1){
                Toast.makeText(this, "cant update note", Toast.LENGTH_SHORT).show();
                return;
            }
            Note note = new Note(title,description,priority);
            note.setId(id);
            mViewModel.update(note);
        }
    }


}
