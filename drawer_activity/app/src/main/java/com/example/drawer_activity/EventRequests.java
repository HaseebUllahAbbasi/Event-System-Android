package com.example.drawer_activity;

public class EventRequests {
    String title;
    String descriptipon;
    String eventId;

    public EventRequests(String title, String descriptipon,String eventId) {
        this.title = title;
        this.descriptipon = descriptipon;
        this.eventId = eventId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
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
