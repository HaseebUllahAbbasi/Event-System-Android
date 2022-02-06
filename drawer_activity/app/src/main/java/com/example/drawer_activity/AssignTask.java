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

public class AssignTask extends AppCompatActivity {

    private static final String TAG = "Alert ";
    TextInputEditText name,task;
    String eventid;
    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_task);
        name = findViewById(R.id.task_assignTo);
        task = findViewById(R.id.task_desc);
        layout = findViewById(R.id.assign_task_layout);
        Intent intent = getIntent();
        eventid = intent.getStringExtra("eventid");

    }

    public void assignTask(View view)
    {
        String postUrl = HardCoded.apiLink+"/assignTaskByName";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        Log.d(TAG, "inviteMember: "+name.getText().toString());
        Log.d(TAG, "inviteMember: "+ GlobalValues.user_id);


        JSONObject postData = new JSONObject();
        try {
            postData.put("taskAssignedTo", name.getText().toString());
            postData.put("taskText", task.getText().toString());
            postData.put("eventId", eventid);
            postData.put("plannerId", GlobalValues.user_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                try {
                    if(response.getBoolean("success")) {
                        Snackbar.make(layout, "Task has been created", Snackbar.LENGTH_LONG).show();
                    }
                    else
                    {
                        Snackbar.make(layout, "This person is not a member for this event", Snackbar.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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