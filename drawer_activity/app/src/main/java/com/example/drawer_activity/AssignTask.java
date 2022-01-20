package com.example.drawer_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

public class AssignTask extends AppCompatActivity {

    private static final String TAG = "Alert ";
    TextInputEditText name,task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_task);
        name = findViewById(R.id.task_assignTo);
        task = findViewById(R.id.task_desc);

    }

    public void assignTask(View view)
    {
        Log.d(TAG, "assignTask:  name "+ name.getText().toString());
        Log.d(TAG, "assignTask: task "+ name.getText().toString());
    }
}