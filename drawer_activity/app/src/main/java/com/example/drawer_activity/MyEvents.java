package com.example.drawer_activity;

public class MyEvents {
    String eventName;
    String eventDescription;
    int image;

    public MyEvents(String eventName, String eventDescription, int image) {
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.image = image;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
