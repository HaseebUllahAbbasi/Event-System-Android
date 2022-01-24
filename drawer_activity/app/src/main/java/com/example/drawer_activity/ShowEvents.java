package com.example.drawer_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class ShowEvents extends AppCompatActivity {

    @Override                                                      
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_events);

        System.out.println("inside showevents");
//        RecyclerView recyclerView = findViewById(R.id.recycler_view);
//        recyclerView.setHasFixedSize(true);
////        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
////        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
////        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        MyEvents[] events = new MyEvents[]{
//                new MyEvents("BirthDay", "Celebrate Birthday on Monday", R.drawable.image1),
//                new MyEvents("Planting", "Plantation on 14 August", R.drawable.image3),
//                new MyEvents("TechFest", "Computer Science event", R.drawable.image1),
//                new MyEvents("Sports Gala", "Sports week for all games", R.drawable.image1),
//                new MyEvents("TechFest", "Computer Science event", R.drawable.image3),
//                new MyEvents("Sports Gala", "Sports week for all games", R.drawable.image1),
//                new MyEvents("BirthDay", "Celebrate Birthday on Monday", R.drawable.image3)
//        };
//
//        eventAdpater myEventAdapter = new eventAdpater(events,ShowEvents.this)       ;
//        recyclerView.setAdapter(myEventAdapter);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyEvents[] MyEvents = new MyEvents[]{
                new MyEvents("Avengers","2019 film",R.drawable.avenger),
                new MyEvents("Venom","2018 film",R.drawable.venom),
                new MyEvents("Batman Begins","2005 film",R.drawable.batman),
                new MyEvents("Jumanji","2019 film",R.drawable.jumanji),
                new MyEvents("Good Deeds","2012 film",R.drawable.good_deeds),
                new MyEvents("Hulk","2003 film",R.drawable.hulk),
                new MyEvents("Avatar","2009 film",R.drawable.avatar),
        };

        MyEventAdapter myEventAdapter = new MyEventAdapter(MyEvents,ShowEvents.this);
        recyclerView.setAdapter(myEventAdapter);

    }
}