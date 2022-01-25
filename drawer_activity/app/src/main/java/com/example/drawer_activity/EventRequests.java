package com.example.drawer_activity;

public class EventRequests {
    String title;
    String descriptipon;

    public EventRequests(String title, String descriptipon) {
        this.title = title;
        this.descriptipon = descriptipon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescriptipon() {
        return descriptipon;
    }

    public void setDescriptipon(String descriptipon) {
        this.descriptipon = descriptipon;
    }
}
