package com.example.drawer_activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginPage extends AppCompatActivity {

    TextInputEditText username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
    }
    public void onLogin(View view)
    {
        Log.d(TAG, "onLogin: "+username.getText().toString() +" "+ password.getText().toString());
        String postUrl = "https://glacial-ridge-23454.herokuapp.com/login";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {
            postData.put("name", username.getText().toString());
            postData.put("password", password.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    System.out.println(response.get("success"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    if (response.getBoolean("success")) {
                        JSONObject user = response.getJSONObject("users");
                        SharedPreferences preferences = getSharedPreferences("users",MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();

                        editor.putString("username",user.getString("name"));
                        editor.putString("userid",user.getString("_id"));
                        editor.putString("email", user.getString("email"));
                        editor.commit();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequest);


    }
    public void CallRegister(View view)
    {
        Intent intent = new Intent(this,RegisterUser.class);
        startActivity(intent);
//        finish();
    }
}