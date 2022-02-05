package com.example.drawer_activity;

import static android.content.ContentValues.TAG;
import static com.android.volley.Request.Method.GET;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileScreen extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);
        onload();
    }

    public void Edit_Profile(View view)
    {
        Intent intent = new Intent(getApplicationContext(),ModifyProfile.class);
        startActivity(intent);
    }
   private void onload()
    {


        final JSONObject[] jsonObject = new JSONObject[1];
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = HardCoded.apiLink+"/person/"+UserID.user_id;
        StringRequest stringRequest = new StringRequest(GET, url, new Response.Listener() {
            @Override
            public synchronized void onResponse(Object response)
            {
                try {
                    jsonObject[0] = new JSONObject(response.toString());
                    Log.d(TAG, "onResponse: "+                    jsonObject[0].toString());



                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public synchronized void onErrorResponse(VolleyError error)
            {
                Log.d(TAG, "onErrorResponse: "+error.toString());

            }
        });

        queue.add(stringRequest);

    }
}