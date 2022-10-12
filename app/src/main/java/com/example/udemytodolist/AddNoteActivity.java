package com.example.udemytodolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextAddNote;
    private Button buttonNewNote;
    private RadioButton radioButtonLow;
    private RadioButton radioButtonMedium;
    private RadioButton radioButtonHigh;

    private AddNoteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        viewModel = new ViewModelProvider(this).get(AddNoteViewModel.class);
        viewModel.getIsFinish().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldClose) {
                if (shouldClose) {
                    finish();
                }
            }
        });
        initView();

        buttonNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });
    }

    private void initView() {
        editTextAddNote = findViewById(R.id.editTextTask);
        buttonNewNote = findViewById(R.id.buttonCreateNote);
        radioButtonHigh = findViewById(R.id.radioButtonImmediately);
        radioButtonLow = findViewById(R.id.radioButtonLater);
        radioButtonMedium = findViewById(R.id.radioButtonImportant);
    }

    private void saveNote() {
        String text = editTextAddNote.getText().toString().trim();

        int priority = 2;
        if (radioButtonLow.isChecked()) {
            priority = 0;
        }
        if (radioButtonMedium.isChecked()) {
            priority = 1;
        }
        if (!editTextAddNote.getText().toString().equals("")) {
            Note note = new Note(text, priority);

            viewModel.add(note);

        } else {
            Toast.makeText(this, "Fill the field", Toast.LENGTH_SHORT).show();
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, AddNoteActivity.class);
    }
}