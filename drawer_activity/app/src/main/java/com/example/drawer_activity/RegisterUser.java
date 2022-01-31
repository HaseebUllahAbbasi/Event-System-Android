package com.example.drawer_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

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
    private ProgressBar spinner;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        name = findViewById(R.id.register_username);
        email = findViewById(R.id.register_email);
        phone = findViewById(R.id.register_phone);
        password = findViewById(R.id.register_password);
        layout = findViewById(R.id.registerUserScreen);
        spinner=(ProgressBar)findViewById(R.id.progressBar);
    }
    public void onRegister(View view)
    {
        if(name.getText().toString().equals("") || email.getText().toString().trim().equals("") ||
                password.getText().toString().equals("") || name.getText().toString().trim().equals(""))
        {
            Snackbar.make(layout,"Error! Missing Fields", Snackbar.LENGTH_SHORT).show();
        }
        else {
            spinner.setVisibility(View.VISIBLE);
            String postUrl = "https://glacial-ridge-23454.herokuapp.com/person";
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject postData = new JSONObject();
            try {
                postData.put("name", name.getText().toString().trim());
                postData.put("number", phone.getText().toString().trim());
                postData.put("email", email.getText().toString().trim());
                postData.put("password", password.getText().toString().trim());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getBoolean("success"))
                        {
                            spinner.setVisibility(View.VISIBLE);
                            Snackbar.make(layout, "Successful Registered New User ", Snackbar.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                            startActivity(intent);
                        } else {
                            spinner.setVisibility(View.VISIBLE);
                            Snackbar.make(layout, "Error While Registering ", Snackbar.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Snackbar.make(layout, "Error While Registering ", Snackbar.LENGTH_SHORT).show();

                    }
                    System.out.println(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    spinner.setVisibility(View.INVISIBLE);
                    Snackbar.make(layout, "Error! User Already exist", Snackbar.LENGTH_SHORT).show();
                }
            });

            requestQueue.add(jsonObjectRequest);
        }
    }
    public void callLogin(View view)
    {
        Intent login = new Intent(getApplicationContext(),LoginPage.class);
        startActivity(login);
        finish();
    }
}