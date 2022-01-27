package com.example.drawer_activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

public class InviteMember extends AppCompatActivity {

    private static final String TAG = "Alert ";
    TextInputEditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_member);
        name =  findViewById(R.id.guest_invite_number);

    }

    public void inviteMember(View view)
    {
        Log.d(TAG, "removeMember: "+ name.getText().toString().trim());


    }
}