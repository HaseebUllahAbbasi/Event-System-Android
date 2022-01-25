package com.example.drawer_activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class customEventRequestAdapter extends ArrayAdapter<EventRequests> {
    List<EventRequests> list;
    public customEventRequestAdapter(@NonNull Context context, int resource, @NonNull List<EventRequests> objects) {
        super(context, resource, objects);
        list = objects;
    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_request_list, parent, false);
        EventRequests request = list.get(position);
        TextView title =  convertView.findViewById(R.id.req_title);
        TextView desc = convertView.findViewById(R.id.req_desc);
        title.setText(request.title);
        desc.setText(request.descriptipon);
        return convertView;
    }
}
