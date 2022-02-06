package com.example.drawer_activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class customEventRequestAdapter extends ArrayAdapter<EventRequests> {
    List<EventRequests> list;
    LinearLayout layout;
    public customEventRequestAdapter(@NonNull Context context, int resource, @NonNull List<EventRequests> objects) {
        super(context, resource, objects);
        list = objects;
    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_request_list, parent, false);
        EventRequests request = list.get(position);
        TextView title =  convertView.findViewById(R.id.req_title);
        TextView desc = convertView.findViewById(R.id.req_desc);
        title.setText(request.title);
        desc.setText(request.descriptipon);
        layout = convertView.findViewById(R.id.viewRequestLayout);
        Button acceptBtn  = convertView.findViewById(R.id.acceptButton);
        acceptBtn.setTag(position);
        Button rejectBtn =  convertView.findViewById(R.id.rejectButton);
        rejectBtn.setTag(position);
        //convertView.setTag();
                acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /// Log.d(TAG, "onClick: "+v.getParent().toString());
                String postUrl = HardCoded.apiLink+"/acceptRequest";
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());


                JSONObject postData = new JSONObject();
                try {
                    postData.put("userId", GlobalValues.user_id);
                    postData.put("eventId", list.get(Integer.parseInt(v.getTag().toString())).getEventId());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        Toast.makeText(getContext(), "Request Accepted", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });

                requestQueue.add(jsonObjectRequest);
            }
        });
        rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /// Log.d(TAG, "onClick: "+v.getParent().toString());
                String postUrl = HardCoded.apiLink+"/cancelRequest";
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());


                JSONObject postData = new JSONObject();
                try {
                    postData.put("userId", GlobalValues.user_id);
                    postData.put("eventId", list.get(Integer.parseInt(v.getTag().toString())).getEventId());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        Toast.makeText(getContext(), "Request Rejected", Toast.LENGTH_SHORT).show();
                        ViewEventRequests requests = new ViewEventRequests();
                        requests.loadEventRequests();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });

                requestQueue.add(jsonObjectRequest);
            }
        });
        return convertView;

    }


}
