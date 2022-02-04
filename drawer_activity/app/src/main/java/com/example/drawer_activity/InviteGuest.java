package com.example.drawer_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class InviteGuest extends AppCompatActivity {

    String eventid;
    LinearLayout layout;
    private static final String TAG = "Alert" ;
    TextInputEditText name,number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_guest);
        name = findViewById(R.id.guest_invite_name);
        number = findViewById(R.id.guest_invite_number);
        layout = findViewById(R.id.InviteMemberMain);
        Intent intent = getIntent();
        eventid = intent.getStringExtra("eventid");

    }

    public void inviteGuest(View view)
    {
        Log.d(TAG, "removeMember:  name "+ name.getText().toString().trim());
        Log.d(TAG, "removeMember: number "+ number.getText().toString().trim());

        Log.d(TAG, "inviteGuest: ");

        String postUrl = HardCoded.apiLink+"/addGuest";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        Log.d(TAG, "inviteMember: "+name.getText().toString());
        Log.d(TAG, "inviteMember: "+UserID.user_id);


        JSONObject postData = new JSONObject();
        try {
            postData.put("guestName", name.getText().toString());
            postData.put("guestNumber", number.getText().toString());
            postData.put("eventId", eventid);
            postData.put("plannerId", UserID.user_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                Snackbar.make(layout,"Guest Has been invited",Snackbar.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Snackbar.make(layout,"You have not created this event or user not found",Snackbar.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }
}