package com.example.drawer_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

public class RemoveMember extends AppCompatActivity {

    private static final String TAG = "Alert ";
    TextInputEditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_member);
        name =  findViewById(R.id.member_remove_name);
    }

    public void removeMember(View view)
    {
        Log.d(TAG, "removeMember: "+ name.getText().toString());


    }
}