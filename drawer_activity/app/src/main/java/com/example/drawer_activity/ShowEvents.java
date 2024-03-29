package com.example.drawer_activity;

import static android.content.ContentValues.TAG;
import static com.android.volley.Request.Method.GET;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

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

public class ShowEvents extends AppCompatActivity {


    View view_1 ;
    View view_2 ;
    @Override                                                      
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_events);
        view_1 = findViewById(R.id.not_found_events);
        view_2  = findViewById(R.id.recyclerView);

        System.out.println("inside showevents");
//        RecyclerView recyclerView = findViewById(R.id.recycler_view);
//        recyclerView.setHasFixedSize(true);
////        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
////        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
////        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        MyEvents[] events = new MyEvents[]{
//                new MyEvents("BirthDay", "Celebrate Birthday on Monday", R.drawable.image1),
//                new MyEvents("Planting", "Plantation on 14 August", R.drawable.image3),
//                new MyEvents("TechFest", "Computer Science event", R.drawable.image1),
//                new MyEvents("Sports Gala", "Sports week for all games", R.drawable.image1),
//                new MyEvents("TechFest", "Computer Science event", R.drawable.image3),
//                new MyEvents("Sports Gala", "Sports week for all games", R.drawable.image1),
//                new MyEvents("BirthDay", "Celebrate Birthday on Monday", R.drawable.image3)
//        };
//
//        eventAdpater myEventAdapter = new eventAdpater(events,ShowEvents.this)       ;
//        recyclerView.setAdapter(myEventAdapter);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        final JSONObject[] jsonObject = new JSONObject[1];
        final JSONArray[] array = new JSONArray[1];

        ArrayList<MyEvents> events = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url =  HardCoded.apiLink+"/getEventByUser/"+ GlobalValues.user_id;
        StringRequest stringRequest = new StringRequest(GET, url, new Response.Listener() {
            @Override
            public synchronized void onResponse(Object response)
            {
                try {
                    jsonObject[0] = new JSONObject(response.toString());
                    Log.d(TAG, "onResponse: "+ jsonObject[0].get("events"));
                    array[0] = jsonObject[0].getJSONArray("events");
                    Log.d(TAG, "onResponse: "+ array[0].toString());
                    Log.d(TAG, "onResponse: "+array[0].length());
                    for (int i = 0; i < array[0].length(); i++) {
                      JSONObject eventObject = array[0].getJSONObject(i);
                      events.add(new MyEvents(eventObject.getString("_id"),eventObject.getString("eventName"),
                              eventObject.getString("eventDesc"),R.drawable.image1));
                        Log.d(TAG, "onResponse: "+eventObject.toString());


                    }
                    MyEventAdapter myEventAdapter = new MyEventAdapter(events,ShowEvents.this);
                    Log.d(TAG, "onCreate: "+   Arrays.toString(events.toArray()));
                    recyclerView.setAdapter(myEventAdapter);


                    if(array[0].length()>0) {
                        CardView.LayoutParams params = (CardView.LayoutParams) view_1.getLayoutParams();
                        params.height = 0;
                        ;
                        view_1.setLayoutParams(params);

                        params = (CardView.LayoutParams) view_2.getLayoutParams();
                        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                        view_2.setLayoutParams(params);
                    }
                    else
                    {
                        CardView.LayoutParams params = (CardView.LayoutParams) view_1.getLayoutParams();
                        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                        view_1.setLayoutParams(params);

                        params = (CardView.LayoutParams) view_2.getLayoutParams();
                        params.height = 0;;

                        view_2.setLayoutParams(params);
                    }


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
                CardView.LayoutParams params = (CardView.LayoutParams) view_1.getLayoutParams();
                params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                view_1.setLayoutParams(params);

                params = (CardView.LayoutParams) view_2.getLayoutParams();
                params.height = 0;;

                view_2.setLayoutParams(params);
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
}