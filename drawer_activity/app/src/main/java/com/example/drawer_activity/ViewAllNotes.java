package com.example.drawer_activity;

import static android.content.ContentValues.TAG;
import static com.android.volley.Request.Method.GET;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class ViewAllNotes extends AppCompatActivity {

    CoordinatorLayout layout;
    ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_notes2);
        layout = findViewById(R.id.view_All_Notes_Main);
        listview = findViewById(R.id.notes_listView);

        getNotes();

    }

    public void addNewNote(View view)
    {
        if(!GlobalValues.eventStatus) {
            Intent intent = new Intent(this, CreateNote.class);
            startActivity(intent);
        }
        else
        {
            Snackbar.make(layout,"Event is Already Completed", Snackbar.LENGTH_LONG).show();
        }
    }

    public void getNotes()
    {
        final JSONObject[] jsonObject = new JSONObject[1];
        final JSONArray[] array = new JSONArray[1];

        ArrayList<NotesModel> notes = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url =  HardCoded.apiLink+"/notesOfEvent/"+GlobalValues.eventId;
        StringRequest stringRequest = new StringRequest(GET, url, new Response.Listener() {
            @Override
            public synchronized void onResponse(Object response)
            {
                try {
                    jsonObject[0] = new JSONObject(response.toString());
                    Log.d(TAG, "onResponse: "+ jsonObject[0].get("noteListsFound"));
                    array[0] = jsonObject[0].getJSONArray("noteListsFound");
                    Log.d(TAG, "onResponse: "+ array[0].toString());
                    Log.d(TAG, "onResponse: "+array[0].length());
                    for (int i = 0; i < array[0].length(); i++) {
                        JSONObject eventObject = array[0].getJSONObject(i);
                        notes.add(new NotesModel(eventObject.getString("NotesText"),eventObject.getString("_id")));
                        Log.d(TAG, "onResponse: "+eventObject.toString());


                    }
                    CustomNotesAdapter customNotesAdapter = new CustomNotesAdapter(getApplicationContext(),R.layout.view_all_note_layout,notes);
                    customNotesAdapter.notifyDataSetChanged();
                    Log.d(TAG, "onCreate: "+   Arrays.toString(notes.toArray()));
                    listview.setAdapter(customNotesAdapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Log.d(TAG, "onResponse: " + response.toString());
//                 data[0] = response.toString();
//                Log.d(TAG, "onResponse: "+data[0]);;
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