package com.example.drawer_activity;

import static android.content.ContentValues.TAG;
import static com.android.volley.Request.Method.GET;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ramotion.circlemenu.CircleMenuView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OneEvent extends AppCompatActivity {

    TextView eventName, description,  planner, teamMembers, tasks, notes, guests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_event);

        eventName = findViewById(R.id.one_event_name);
        description = findViewById(R.id.eventDesc_txt);
        planner = findViewById(R.id.plannerName);
        teamMembers = findViewById(R.id.teamMembers_txt);
        tasks = findViewById(R.id.tasksAssigned_txt);
        notes = findViewById(R.id.notes_txt);
        guests = findViewById(R.id.guests_txt);
        Intent intent = getIntent();
        String id = intent.getStringExtra("_id");
        getEvent(id);



        final CircleMenuView menu = findViewById(R.id.circle_menu_one_event);
        
        menu.setEventListener(new CircleMenuView.EventListener(){
            @Override
            public void onMenuOpenAnimationStart(@NonNull CircleMenuView view) {
                Log.d("D","onMenuOpenAnimationStart");
            }
            @Override
            public void onMenuOpenAnimationEnd(@NonNull CircleMenuView view) {
                Log.d("D","onMenuOpenAnimationEnd");
            }
            @Override
            public void onMenuCloseAnimationStart(@NonNull CircleMenuView view) {
                Log.d("D","onMenuCloseAnimationStart");
            }
            @Override
            public void onMenuCloseAnimationEnd(@NonNull CircleMenuView view) {
                Log.d("D","onMenuCloseAnimationEnd");
            }
            @Override
            public void onButtonClickAnimationStart(@NonNull CircleMenuView view, int index) {
                Log.d("D","onButtonClickAnimationStart|index: "+index);

            }
            @Override
            public void onButtonClickAnimationEnd(@NonNull CircleMenuView view, int index) {
                Log.d("D","onButtonClickAnimationEnd|index: "+index);
                Log.d(TAG, "onButtonClickAnimationEnd: To be implemented more options here");
                switch (index)
                {
                    case 0:
                        Intent task = new Intent(getApplicationContext(),ViewAllTasks.class);
                        startActivity(task);
                        break;

                    case 1:
                        Intent member = new Intent(getApplicationContext(),ViewAllMembers.class);
                        member.putExtra("id",id);
                        startActivity(member);
                        break;

                    case 2:
                        Intent note = new Intent(getApplicationContext(),ViewAllNotes.class);
                        startActivity(note);
                        break;

                    case 3:
                        Intent guest = new Intent(getApplicationContext(),ViewAllGuests.class);
                        guest.putExtra("id",id);
                        startActivity(guest);
                        break;
                }
            }
            @Override
            public boolean onButtonLongClick(@NonNull CircleMenuView view, int buttonIndex) {
                Log.d("D","onButtonLongClick|index: "+buttonIndex);
                return true;
            }
            @Override
            public void onButtonLongClickAnimationStart(@NonNull CircleMenuView view, int buttonIndex) {
                Log.d("D","onButtonLongClickAnimationStart|index: "+buttonIndex);
            }
            @Override
            public void onButtonLongClickAnimationEnd(@NonNull CircleMenuView view, int buttonIndex) {
                Log.d("D","onButtonLongClickAnimationEnd|index: "+buttonIndex);
            }
        });


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
                    planner.setText(event.get("userName").toString());
                    description.setText(event.get("eventDesc").toString());

                    JSONArray teamsArray = event.getJSONArray("team");
                    JSONArray tasksArray = event.getJSONArray("tasks");
                    JSONArray notesArray = event.getJSONArray("notes");
                    JSONArray guestsArray = event.getJSONArray("guestList");

                    teamMembers.setText(""+teamsArray.length());
                    tasks.setText(""+tasksArray.length());
                    notes.setText(""+notesArray.length());
                    guests.setText(""+guestsArray.length());


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