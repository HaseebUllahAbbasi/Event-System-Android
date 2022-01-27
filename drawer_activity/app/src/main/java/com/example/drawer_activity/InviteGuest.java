package com.example.drawer_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

public class InviteGuest extends AppCompatActivity {

    private static final String TAG = "Alert" ;
    TextInputEditText name,number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_guest);
        name = findViewById(R.id.guest_invite_name);
        number = findViewById(R.id.guest_invite_number);
    }

    public void inviteGuest(View view)
    {
        Log.d(TAG, "removeMember:  name "+ name.getText().toString().trim());
        Log.d(TAG, "removeMember: number "+ number.getText().toString().trim());

        Log.d(TAG, "inviteGuest: ");

    }
}