package com.example.drawer_activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

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

public class LoginPage extends AppCompatActivity {

    LinearLayout layout;
    private ProgressBar spinner;
    TextInputEditText username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        layout = findViewById(R.id.login_wrapper);
        spinner=(ProgressBar)findViewById(R.id.progressBar);

    }
    public void onLogin(View view)
    {
        spinner.setVisibility(View.VISIBLE);
        Log.d(TAG, "onLogin: "+username.getText().toString() +" "+ password.getText().toString());
        String postUrl = "https://glacial-ridge-23454.herokuapp.com/login";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {
            postData.put("name", username.getText().toString().trim());
            postData.put("password", password.getText().toString().trim());

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
                        spinner.setVisibility(View.INVISIBLE);
                        JSONObject user = response.getJSONObject("users");
                        SharedPreferences preferences = getSharedPreferences("users",MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();

                        editor.putString("username",user.getString("name"));
                        editor.putString("userid",user.getString("_id"));
                        editor.putString("email", user.getString("email"));

                        GlobalValues.user_id = user.getString("_id");
                        GlobalValues.user_name = user.getString("name");


                        editor.commit();

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run()
                            {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);

                            }
                        },1500);
                    }
                    else
                    {
                        spinner.setVisibility(View.INVISIBLE);
                        Snackbar.make(layout,"Wrong Username or password",Snackbar.LENGTH_LONG).show();
                    }
                } catch (JSONException e)
                {
                    spinner.setVisibility(View.INVISIBLE);
                    Snackbar.make(layout,"Error while Logging",Snackbar.LENGTH_LONG).show();
                    e.printStackTrace();

                }
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                spinner.setVisibility(View.INVISIBLE);
                Snackbar.make(layout,"Networ Error",Snackbar.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequest);



    }
    public void CallRegister(View view)
    {
        Intent intent = new Intent(this,RegisterUser.class);
        startActivity(intent);
 //       finish();
    }
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }

}