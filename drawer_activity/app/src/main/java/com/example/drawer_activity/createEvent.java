package com.example.drawer_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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

public class createEvent extends AppCompatActivity {

    TextInputEditText eventName,eventDescription;
    String username,userid;

    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        layout = findViewById(R.id.InviteMemberMain);
        eventName = findViewById(R.id.event_name);
        eventDescription = findViewById(R.id.event_description);
        SharedPreferences pref = getSharedPreferences("users",MODE_PRIVATE);
        username = pref.getString("username",null);
        userid = pref.getString("userid",null);
    }
    public void onCreateEvent(View view)
    {
        if(eventName.getText().toString().equals("") || eventDescription.getText().toString().equals("")
        || username.equals("") || userid.equals(""))
        {
            Snackbar.make(layout, "Error! Missing Fields", Snackbar.LENGTH_SHORT).show();
        }
        else
        {
            String postUrl = "https://glacial-ridge-23454.herokuapp.com/event";
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject postData = new JSONObject();
            try {
                postData.put("eventName", eventName.getText().toString());
                postData.put("eventDesc", eventDescription.getText().toString());
                postData.put("plannerId", userid);
                postData.put("userName", username);
                postData.put("eventStatus",false);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getBoolean("success")) {
                            Snackbar.make(layout, "Event Created", Snackbar.LENGTH_SHORT).show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                }
                            },3000);

                        } else {
                            Snackbar.make(layout, "Error! Unable to Create Event", Snackbar.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Snackbar.make(layout, "Error! Unable to Create Event", Snackbar.LENGTH_SHORT).show();

                }
            });

            requestQueue.add(jsonObjectRequest);
        }
    }
}