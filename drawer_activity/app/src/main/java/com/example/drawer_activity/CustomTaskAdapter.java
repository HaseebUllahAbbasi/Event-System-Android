package com.example.drawer_activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class CustomTaskAdapter extends ArrayAdapter<TaskModel>
{
    List<TaskModel> list;
    Button complete_task;

    public CustomTaskAdapter(@NonNull Context context, int resource, @NonNull List<TaskModel> objects)
    {
        super(context, resource, objects);
        list = objects;
    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.all_tasks_layout, parent, false);
        TaskModel request = list.get(position);
        TextView task = convertView.findViewById(R.id.task_member);
        TextView desc = convertView.findViewById(R.id.task_detail);
        complete_task = convertView.findViewById(R.id.complete_task_btn);
        complete_task.setTag(position);
        if(list.get(position).getTaskStatus())
        {
            complete_task.setText("✅");
            complete_task.setEnabled(false);
            complete_task.setTextSize(25);
            complete_task.getBackground().setAlpha(0);
        }
        task.setText(request.getName());
        desc.setText(request.getDescription());

        complete_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postUrl = HardCoded.apiLink+"/completeTask";
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());


                JSONObject postData = new JSONObject();
                try {
                    postData.put("userId", GlobalValues.user_name);
                    postData.put("taskId", list.get(Integer.parseInt(v.getTag().toString())).getId());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {
                            if(response.getBoolean("success"))
                                Toast.makeText(getContext(), "Task Completed", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getContext(), "You can not complete other's task", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getContext(), "You can not complete other's task", Toast.LENGTH_SHORT).show();

                        error.printStackTrace();
                    }
                });

                requestQueue.add(jsonObjectRequest);

            }
        });
        return convertView;
    }
}
