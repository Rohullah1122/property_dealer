package com.example.property_dealer.classess;

public class cls_user {
    public String userID,name,location,phone,image,receiverid;


    public cls_user() {

    }

    public cls_user(String userID, String name, String location, String phone, String image) {
        this.userID = userID;
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.image = image;
    }


    public String getReceiverid() {
        return receiverid;
    }

    public void setReceiverid(String receiverid) {
        this.receiverid = receiverid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

