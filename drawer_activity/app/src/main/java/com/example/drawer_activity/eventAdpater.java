package com.example.drawer_activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class eventAdpater extends RecyclerView.Adapter<eventAdpater.ViewHolder> {

    MyEvents[] events;
    Context context;

    public eventAdpater(MyEvents[] myEvents, ShowEvents activity) {
                events = myEvents;
                context = activity;
                System.out.println(myEvents+" kkkk");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recyclerview,parent,false);
        ViewHolder viewholder = new ViewHolder(view);
        System.out.println("inflated");

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MyEvents eventsList = events[position];
        System.out.println(eventsList.getEventName()+" hello world");
        holder.txtEventName.setText(eventsList.getEventName());
        holder.txtEventDescription.setText(eventsList.getEventDescription());
        holder.image.setImageResource(eventsList.getImage());
        System.out.println("onbind");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, eventsList.getEventName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView txtEventName;
        TextView txtEventDescription;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_image);
            txtEventName = itemView.findViewById(R.id.event_name);
            txtEventDescription = itemView.findViewById(R.id.event_description);
            System.out.println("viewholder");

        }
    }
}
