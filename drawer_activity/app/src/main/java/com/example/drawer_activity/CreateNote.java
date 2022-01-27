package com.example.drawer_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

public class CreateNote extends AppCompatActivity {

    private static final String TAG =  "Note";
    TextInputEditText note_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        note_text = findViewById(R.id.note_text);

    }

    public void createNote(View view)
    {
        Log.d(TAG, "createNote: "+note_text.getText().toString());

    }
}