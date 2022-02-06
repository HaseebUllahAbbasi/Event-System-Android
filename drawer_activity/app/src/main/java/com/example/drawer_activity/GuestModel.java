package com.example.drawer_activity;

public class GuestModel
{
    int image;
    String guestId;

    public GuestModel(int image, String userName, String phoneNumber,String guestId) {
        this.image = image;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.guestId = guestId;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "GuestModel{" +
                "image=" + image +
                ", userName='" + userName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    String userName;
    String phoneNumber;
    public GuestModel(String userName, String phoneNumber) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
