package com.example.drawer_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterUser extends AppCompatActivity {

    TextInputEditText name,email,phone,password;
    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        name = findViewById(R.id.register_username);
        email = findViewById(R.id.register_email);
        phone = findViewById(R.id.register_phone);
        password = findViewById(R.id.register_password);
        layout = findViewById(R.id.registerUserScreen);
    }
    public void onRegister(View view)
    {
        if(name.getText().toString().equals("") || email.getText().toString().equals("") ||
                password.getText().toString().equals("") || name.getText().toString().equals(""))
        {
            Snackbar.make(layout,"Error! Missing Fields", Snackbar.LENGTH_SHORT).show();

        }
        else {
            String postUrl = "https://glacial-ridge-23454.herokuapp.com/person";
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject postData = new JSONObject();
            try {
                postData.put("name", name.getText().toString());
                postData.put("number", phone.getText().toString());
                postData.put("email", email.getText().toString());
                postData.put("password", password.getText().toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getBoolean("success")) {
                            Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                            startActivity(intent);
                        } else {

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
                    Snackbar.make(layout, "Error! User Already exist", Snackbar.LENGTH_SHORT).show();


                }
            });

            requestQueue.add(jsonObjectRequest);
        }
    }
    public void callLogin(View view)
    {

    }
}