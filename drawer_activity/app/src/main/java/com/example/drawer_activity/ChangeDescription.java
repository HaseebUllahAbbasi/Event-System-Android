package com.example.drawer_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

public class ChangeDescription extends AppCompatActivity {

    private static final String TAG = "MSG";
    TextInputEditText desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_description);
        desc =  findViewById(R.id.desc_text);
    }

    public void changeDescription(View view)
    {
        Log.d(TAG, "changeDescription: "+ desc.getText().toString());

    }
}