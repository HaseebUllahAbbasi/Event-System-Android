package com.example.drawer_activity;

import static android.content.ContentValues.TAG;
import static com.android.volley.Request.Method.GET;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OneEvent extends AppCompatActivity {

    TextView eventName, description,  planner, teamMembers, tasks, notes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_event);

        eventName = findViewById(R.id.one_event_name);
        description = findViewById(R.id.eventDesc_txt);
        planner = findViewById(R.id.plannerID_txt);
        teamMembers = findViewById(R.id.teamMembers_txt);
        tasks = findViewById(R.id.tasksAssigned_txt);
        notes = findViewById(R.id.notes_txt);

        Intent intent = getIntent();
        String id = intent.getStringExtra("_id");
        getEvent(id);


    }

    public void getEvent(String id)
    {
        Log.d(TAG, "getEvent: "+id);
        final JSONObject[] jsonObject = new JSONObject[1];
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = HardCoded.apiLink+"/event/"+id;
        StringRequest stringRequest = new StringRequest(GET, url, new Response.Listener() {
            @Override
            public synchronized void onResponse(Object response)
            {
                try {
                    jsonObject[0] = new JSONObject(response.toString());
                    JSONObject event = jsonObject[0].getJSONObject("event");

                    eventName.setText(event.get("eventName").toString());
                    planner.setText(event.get("userId").toString());
                    description.setText(event.get("eventDesc").toString());

                    JSONArray teamsArray = event.getJSONArray("team");
                    JSONArray tasksArray = event.getJSONArray("tasks");
                    JSONArray notesArray = event.getJSONArray("notes");

                    teamMembers.setText(""+teamsArray.length());
                    tasks.setText(""+tasksArray.length());
                    notes.setText(""+notesArray.length());


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public synchronized void onErrorResponse(VolleyError error) {
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}