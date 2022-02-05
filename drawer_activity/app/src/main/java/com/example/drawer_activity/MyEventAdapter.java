package com.example.drawer_activity;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class MyEventAdapter extends RecyclerView.Adapter<MyEventAdapter.ViewHolder> {

    ArrayList<MyEvents>myEvents;
    Context context;

    public MyEventAdapter(ArrayList<MyEvents> myEvents, ShowEvents activity) {
        this.myEvents = myEvents;
        this.context = activity;
        Log.d(TAG, "MyEventAdapter: "+ Arrays.toString(myEvents.toArray()));

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.events_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        final MyEvents events = myEvents.get(position);
        holder.textViewName.setText(events.getEventName());
        holder.textViewDate.setText(events.getEventDescription());
        holder.movieImage.setImageResource(events.getImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, events.getId(), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, events.getEventName(), Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = context.getSharedPreferences("eventDetail",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("eventid",events.getId());
                editor.putString("eventname",events.getEventName());
                editor.commit();
                Log.d(TAG, "onClick: "+events.getId()+ " "+ events.getEventName());
                Intent intent = new Intent(context,OneEvent.class);
                intent.putExtra("_id",events.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myEvents.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView movieImage;
        TextView textViewName;
        TextView textViewDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.event_image);
            textViewName = itemView.findViewById(R.id.guest_name);
            textViewDate = itemView.findViewById(R.id.guest_number);

        }
    }

}
