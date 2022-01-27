package com.example.drawer_activity;

import static android.content.ContentValues.TAG;
import static com.android.volley.Request.Method.GET;

import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class ViewAllMembers extends AppCompatActivity
{



    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_members2);
        listView = findViewById(R.id.memberListView);


        Intent intent = getIntent();
        String id = intent.getStringExtra("id");


        final JSONObject[] jsonObject = new JSONObject[1];
        final JSONArray[] array = new JSONArray[1];

        ArrayList<ViewMember> events = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url =  HardCoded.apiLink+"/getMembers/"+id;
        StringRequest stringRequest = new StringRequest(GET, url, new Response.Listener() {
            @Override
            public synchronized void onResponse(Object response)
            {
                try {
                    jsonObject[0] = new JSONObject(response.toString());
                    Log.d(TAG, "onResponse: "+ jsonObject[0].get("team"));
                    array[0] = jsonObject[0].getJSONArray("team");
                    Log.d(TAG, "onResponse: "+ array[0].toString());
                    Log.d(TAG, "onResponse: "+array[0].length());
                    for (int i = 0; i < array[0].length(); i++) {
                        JSONObject eventObject = array[0].getJSONObject(i);
                        events.add(new ViewMember(R.drawable.image1,eventObject.getString("name"),eventObject.getString("id")));
                        Log.d(TAG, "onResponse: "+eventObject.toString());


                    }
                    CustomMemberAdapter memberAdapter = new CustomMemberAdapter(getApplicationContext(),R.layout.viewallmemberlayout,events);
                    Log.d(TAG, "onCreate: "+        Arrays.toString(events.toArray()));
                    listView.setAdapter(memberAdapter);


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

    public void addNewMember(View view)
    {
        Intent intent = new Intent(this,InviteMember.class);
        startActivity(intent);

    }
}