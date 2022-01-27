package com.example.drawer_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ViewAllGuests extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_guests);
    }

    public void addNewGuest(View view)
    {
        Intent intent = new Intent(this,InviteGuest.class);
        startActivity(intent);

    }
}