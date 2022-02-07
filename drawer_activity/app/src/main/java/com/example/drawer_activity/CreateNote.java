package com.example.drawer_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

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

public class CreateNote extends AppCompatActivity {

    private static final String TAG =  "Note";
    TextInputEditText note_text;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        note_text = findViewById(R.id.note_text);
        layout = findViewById(R.id.create_note_main);
    }

    public void createNote(View view)
    {
        Log.d(TAG, "createNote: "+note_text.getText().toString());
        String postUrl = HardCoded.apiLink+"/addNote";
        RequestQueue requestQueue = Volley.newRequestQueue(this);


        JSONObject postData = new JSONObject();
        try {
            postData.put("eventId", GlobalValues.eventId);
            postData.put("plannerId", GlobalValues.user_id);
            postData.put("noteText", note_text.getText().toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                try {
                    if(response.getBoolean("success"))
                        Snackbar.make(layout,"Note has been created",Snackbar.LENGTH_LONG).show();
                    else
                        Snackbar.make(layout,"You are not a member of this event",Snackbar.LENGTH_LONG).show();



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Snackbar.make(layout,"Error",Snackbar.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }
}