package com.example.drawer_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class ViewEventRequests extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event_requests);
        listView = findViewById(R.id.listview);

        EventRequests[] requests = new EventRequests[]{
                new EventRequests("Party","Party ho rhi he"),
                new EventRequests("Shadi","Party nhi rhi he"),
                new EventRequests("birthday","birthday sb ki rhi he"),
                new EventRequests("deathday","Party ho rhi he"),
                new EventRequests("kuch bhe","Party ho rhi he"),
                new EventRequests("treat","Party ho rhi he"),
                new EventRequests("biscoot","Party ho rhi he"),
                new EventRequests("chae","Party ho rhi he")};

        ArrayList list = new ArrayList(Arrays.asList(requests));

        customEventRequestAdapter adapter = new customEventRequestAdapter(this,R.layout.event_request_list,list);
        listView.setAdapter(adapter);
    }
}