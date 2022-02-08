package com.example.drawer_activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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

public class CustomGuestAdapter extends ArrayAdapter<GuestModel> {

    List<GuestModel> list;
    Button removeGuest;

    public CustomGuestAdapter(@NonNull Context context, int resource, @NonNull List<GuestModel> objects)
    {
        super(context, resource, objects);
        list = objects;
    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_all_guests_layout, parent, false);
        GuestModel request = list.get(position);
        ImageView img =  convertView.findViewById(R.id.guest_profile_image);
        TextView name = convertView.findViewById(R.id.guest_name);
        TextView number = convertView.findViewById(R.id.guest_number);
        removeGuest = convertView.findViewById(R.id.remove_guest_btn);
        img.setImageResource( request.getImage());
        name.setText(request.getUserName());
        number.setText(request.getPhoneNumber());
        removeGuest.setTag(position);

        removeGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postUrl = HardCoded.apiLink+"/removeGuest";
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());

                System.out.println("btn tag : "+v.getTag());
                JSONObject postData = new JSONObject();
                try {
                    postData.put("plannerId", GlobalValues.user_id);
                    postData.put("eventId", GlobalValues.eventId);
                    postData.put("guestId",list.get(Integer.parseInt(v.getTag().toString())).getGuestId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {
                            if(response.getBoolean("success")) {
                                Toast.makeText(getContext(), "Guest Removed", Toast.LENGTH_SHORT).show();
                                list.remove(Integer.parseInt(v.getTag().toString()));
                                notifyDataSetChanged();
                            }
                            else
                                Toast.makeText(getContext(), "You are not planner of this event, so you can't remove guests", Toast.LENGTH_SHORT).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
