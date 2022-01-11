package com.example.drawer_activity.ui.home;

public class EventBasic
{

    int photo;
    String name;

    public EventBasic() {
    }

    public EventBasic(int photo, String name, String desc) {
        this.photo = photo;
        this.name = name;
        this.desc = desc;
    }

    String desc;

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
