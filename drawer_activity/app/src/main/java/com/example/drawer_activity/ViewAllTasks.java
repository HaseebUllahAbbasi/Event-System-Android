package com.example.drawer_activity;

import static android.content.ContentValues.TAG;
import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
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

public class ViewAllTasks extends AppCompatActivity {

    String eventid;
    ListView listView;
    CoordinatorLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_tasks);
        listView = findViewById(R.id.tasks_listview);
        Intent intent = getIntent();
        eventid = intent.getStringExtra("id");
        layout = findViewById(R.id.view_All_Tasks_Main);

        final JSONObject[] jsonObject = new JSONObject[1];
        final JSONArray[] array = new JSONArray[1];

        ArrayList<TaskModel> tasks = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url =  HardCoded.apiLink+"/assignTask/"+eventid;
        StringRequest stringRequest = new StringRequest(POST, url, new Response.Listener() {
            @Override
            public synchronized void onResponse(Object response)
            {
                try {
                    jsonObject[0] = new JSONObject(response.toString());
                    Log.d(TAG, "onResponse: "+ jsonObject[0].get("tasks"));
                    array[0] = jsonObject[0].getJSONArray("tasks");
                    Log.d(TAG, "onResponse: "+ array[0].toString());
                    Log.d(TAG, "onResponse: "+array[0].length());
                    for (int i = 0; i < array[0].length(); i++) {
                        JSONObject eventObject = array[0].getJSONObject(i);
                        tasks.add(new TaskModel(eventObject.getString("_id"),eventObject.getString("assignTo"),eventObject.getString("taskText"),eventObject.getBoolean("taskStatus")));
                        Log.d(TAG, "onResponse: "+eventObject.toString());


                    }
                    CustomTaskAdapter taskAdapter = new CustomTaskAdapter(getApplicationContext(),R.layout.all_tasks_layout,tasks);
                    Log.d(TAG, "onCreate: "+        Arrays.toString(tasks.toArray()));
                    listView.setAdapter(taskAdapter);


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

    public void addNewTask(View view)
    {
        if(!GlobalValues.eventStatus)
        {

            Intent intent = new Intent(this,AssignTask.class);
            intent.putExtra("eventid",eventid);
            startActivity(intent);

        }
        else
        {
            Snackbar.make(layout,"Event is Already Completed", Snackbar.LENGTH_LONG).show();
        }
    }
}