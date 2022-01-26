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

public class CustomMemberAdapter extends ArrayAdapter<ViewMember> {

    List<ViewMember> list;

    public CustomMemberAdapter(@NonNull Context context, int resource, @NonNull List<ViewMember> objects)
    {
        super(context, resource, objects);
        list = objects;
    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_request_list, parent, false);
        ViewMember request = list.get(position);
        ImageView img =  convertView.findViewById(R.id.req_title);
        TextView name = convertView.findViewById(R.id.req_desc);
        img.setImageResource(request.getImg());
        name.setText(request.getName());
        return convertView;
    }

}

