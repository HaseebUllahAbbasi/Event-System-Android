package com.example.drawer_activity;

import static android.content.ContentValues.TAG;
import static com.android.volley.Request.Method.GET;

import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewEventRequests extends AppCompatActivity {

    ListView listView;
    View view_1 ;
    View view_2 ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event_requests);
        listView = findViewById(R.id.listview);

        view_1  =    findViewById(R.id.not_found_requests);
        view_2  =   findViewById(R.id.listview);


//        EventRequests[] requests = new EventRequests[]{
//                new EventRequests("Party","Party ho rhi he"),
//                new EventRequests("Shadi","Party nhi rhi he"),
//                new EventRequests("birthday","birthday sb ki rhi he"),
//                new EventRequests("deathday","Party ho rhi he"),
//                new EventRequests("kuch bhe","Party ho rhi he"),
//                new EventRequests("treat","Party ho rhi he"),
//                new EventRequests("biscoot","Party ho rhi he"),
//                new EventRequests("chae","Party ho rhi he")};
//
//        ArrayList list = new ArrayList(Arrays.asList(requests));
    loadEventRequests();



    }
    public void loadEventRequests()
    {
        final JSONObject[] jsonObject = new JSONObject[1];
        final JSONArray[] array = new JSONArray[1];

        ArrayList<EventRequests> requestsList = new ArrayList<>();


        RequestQueue queue = Volley.newRequestQueue(this);
        String url =  HardCoded.apiLink+"/requestsDetails/"+ GlobalValues.user_id;
        StringRequest stringRequest = new StringRequest(GET, url, new Response.Listener() {
            @Override
            public synchronized void onResponse(Object response)
            {
                try {
                    jsonObject[0] = new JSONObject(response.toString());
                    Log.d(TAG, "onResponse: "+ jsonObject[0].get("requestDetailedData"));
                    array[0] = jsonObject[0].getJSONArray("requestDetailedData");
                    Log.d(TAG, "onResponse: "+ array[0].toString());
                    Log.d(TAG, "onResponse: "+array[0].length());
                    for (int i = 0; i < array[0].length(); i++) {
                        JSONObject eventObject = array[0].getJSONObject(i);
                        requestsList.add(new EventRequests(eventObject.getString("eventName"),eventObject.getString("eventDesc"),eventObject.getString("_id")));
                        Log.d(TAG, "onResponse: "+eventObject.toString());
                    }

                    customEventRequestAdapter requestAdapter = new customEventRequestAdapter(getApplicationContext(),R.layout.event_request_list,requestsList);
                    listView.setAdapter(requestAdapter);


                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view_1.getLayoutParams();
                    params.weight = 1;
                    view_1.setLayoutParams(params);

                    params = (LinearLayout.LayoutParams) view_2.getLayoutParams();
                    params.weight = 0;
                    view_2.setLayoutParams(params);



                } catch (JSONException e) 
                {
                    
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public synchronized void onErrorResponse(VolleyError error) 
            {

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view_1.getLayoutParams();
                params.weight = 0;
                view_1.setLayoutParams(params);

                params = (LinearLayout.LayoutParams) view_2.getLayoutParams();
                params.weight = 1;

                view_2.setLayoutParams(params);


                Log.d(TAG, "onErrorResponse: 401 now display the icon");
                
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
}