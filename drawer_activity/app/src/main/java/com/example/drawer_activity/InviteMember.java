package com.example.drawer_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
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

public class InviteMember extends AppCompatActivity {

    LinearLayout layout;
    private static final String TAG = "Alert ";
    TextInputEditText name;
    String eventID,eventName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_member);
        name =  findViewById(R.id.member_invite_name);
        SharedPreferences preferences = getSharedPreferences("eventDetail",MODE_PRIVATE);
        eventID = preferences.getString("eventid","");
        eventName = preferences.getString("eventname","");
        layout = findViewById(R.id.InviteMemberMain);

    }

    public void inviteMember(View view)
    {
        String postUrl = HardCoded.apiLink+"/sendReqByName";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        Log.d(TAG, "inviteMember: "+name.getText().toString());
        Log.d(TAG, "inviteMember: "+UserID.user_id);
        Log.d(TAG, "inviteMember: "+eventID);
        Log.d(TAG, "inviteMember: "+eventName);

        JSONObject postData = new JSONObject();
        try {
            postData.put("recipientName", name.getText().toString());
            postData.put("plannerId", UserID.user_id);
            postData.put("eventId", eventID);
            postData.put("eventName", eventName);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                Snackbar.make(layout,"Invitation Request has been Sent",Snackbar.LENGTH_LONG).show();

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