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
import android.widget.TextView;

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

public class ViewAllGuests extends AppCompatActivity {

    TextView guestName,guestNumber;
    ListView listView;
    String id;
    CoordinatorLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_guests);
        guestName = findViewById(R.id.guest_name);
        guestNumber = findViewById(R.id.guest_number);
        listView  =findViewById(R.id.guestListview);
        layout = findViewById(R.id.view_All_Guests_Main);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        GlobalValues.eventId =id;

        final JSONObject[] jsonObject = new JSONObject[1];
        final JSONArray[] array = new JSONArray[1];

        ArrayList<GuestModel> guests = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url =  HardCoded.apiLink+"/getEventGuest/"+id;
        StringRequest stringRequest = new StringRequest(GET, url, new Response.Listener() {
            @Override
            public synchronized void onResponse(Object response)
            {
                try {
                    jsonObject[0] = new JSONObject(response.toString());
                    //Log.d(TAG, "onResponse: "+ jsonObject[0].get("guestList"));
                    array[0] = jsonObject[0].getJSONArray("guestList");
                    Log.d(TAG, "onResponse: "+ array[0].toString());
                    Log.d(TAG, "onResponse: "+array[0].length());
                    for (int i = 0; i < array[0].length(); i++) {
                        JSONObject eventObject = array[0].getJSONObject(i);
                        guests.add(new GuestModel(R.drawable.image1,eventObject.getString("name"),eventObject.getString("number"),eventObject.getString("_id")));
                        Log.d(TAG, "onResponse: "+eventObject.toString());


                    }
                    CustomGuestAdapter guestAdapter = new CustomGuestAdapter(getApplicationContext(),R.layout.view_all_guests_layout,guests);
                    Log.d(TAG, "onCreate: "+  Arrays.toString(guests.toArray()));
                    listView.setAdapter(guestAdapter);


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

    public void addNewGuest(View view)
    {
        if(!GlobalValues.eventStatus) {
            Intent intent = new Intent(this, InviteGuest.class);
            intent.putExtra("eventid", id);
            startActivity(intent);
        }
        else
        {
            Snackbar.make(layout,"Event is Already Completed", Snackbar.LENGTH_LONG).show();
        }

    }
}