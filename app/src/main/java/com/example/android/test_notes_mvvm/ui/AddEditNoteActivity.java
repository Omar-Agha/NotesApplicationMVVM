package com.example.android.test_notes_mvvm.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.test_notes_mvvm.R;

public class AddEditNoteActivity extends AppCompatActivity {

    private NumberPicker mPriorityNumberPicker;
    private EditText titleEt;
    private EditText descriptionEt;

    public static final String EXTRA_ID =
            "com.example.android.test_notes_mvvm.EXTRA_ID";

    public static final String EXTRA_TITLE =
            "com.example.android.test_notes_mvvm.EXTRA_TITLE";

    public static final String EXTRA_DESCRIPTION =
            "com.example.android.test_notes_mvvm.EXTRA_DESCRIPTION";

    public static final String EXTRA_PRIORITY =
            "com.example.android.test_notes_mvvm.EXTRA_PRIORITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        mPriorityNumberPicker = findViewById(R.id.priority_number_picker);
        titleEt = findViewById(R.id.note_title_et);
        descriptionEt = findViewById(R.id.note_description_et);
        mPriorityNumberPicker.setMinValue(1);
        mPriorityNumberPicker.setMaxValue(10);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_TITLE)){
            String title = intent.getStringExtra(EXTRA_TITLE);
            String description = intent.getStringExtra(EXTRA_DESCRIPTION);
            int priority = intent.getIntExtra(EXTRA_PRIORITY, 1);
            titleEt.setText(title);
            descriptionEt.setText(description);
            mPriorityNumberPicker.setValue(priority);

        }
        setTitle("Add Note");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.save_note_menu_action):
                saveNote();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void saveNote() {
        String title = titleEt.getText().toString();
        String description = descriptionEt.getText().toString();
        int priority = mPriorityNumberPicker.getValue();
        Toast.makeText(this, "" + priority, Toast.LENGTH_SHORT).show();
        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "enter valid values", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_DESCRIPTION, description);
        intent.putExtra(EXTRA_PRIORITY, priority);
        Intent data = getIntent();
        int id = data.getIntExtra(EXTRA_ID,-1);
        if(id !=-1)
        {
            intent.putExtra(EXTRA_ID , id);
        }
        setResult(RESULT_OK, intent);
        finish();
    }
}
