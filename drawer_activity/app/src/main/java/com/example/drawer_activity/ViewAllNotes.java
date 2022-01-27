package com.example.drawer_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ViewAllNotes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_notes2);
    }

    public void addNewNote(View view)
    {
        Intent intent = new Intent(this, CreateNote.class);
        startActivity(intent);
    }
}