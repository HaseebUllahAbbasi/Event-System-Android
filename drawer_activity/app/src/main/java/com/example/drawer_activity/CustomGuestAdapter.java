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

public class CustomGuestAdapter extends ArrayAdapter<GuestModel> {

    List<GuestModel> list;

    public CustomGuestAdapter(@NonNull Context context, int resource, @NonNull List<GuestModel> objects)
    {
        super(context, resource, objects);
        list = objects;
    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.viewallmemberlayout, parent, false);
        GuestModel request = list.get(position);
        ImageView img =  convertView.findViewById(R.id.profileImage_view);
        TextView name = convertView.findViewById(R.id.member_name_view);
        TextView number = convertView.findViewById(R.id.member_name_view);
        img.setImageResource( request.getImage());
        name.setText(request.getUserName());
        number.setText(request.getPhoneNumber());

        return convertView;
    }
}
