package com.example.drawer_activity;

import android.widget.ImageView;

public class ViewMember
{

    int img;
    String name;
    String id;

    public ViewMember(int img, String name,String id)
    {
        this.img = img;
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
