package com.example.drawer_activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomTaskAdapter  extends ArrayAdapter<TaskModel> {

    List<TaskModel> list;

    public CustomTaskAdapter(@NonNull Context context, int resource, @NonNull List<TaskModel> objects)
    {
        super(context, resource, objects);
        list = objects;
    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.viewallmemberlayout, parent, false);
        TaskModel request = list.get(position);
        TextView task = convertView.findViewById(R.id.member_name_view);
        TextView desc = convertView.findViewById(R.id.member_name_view);
        task.setText(request.getTask());
        desc.setText(request.getDescription());
        return convertView;
    }
}
