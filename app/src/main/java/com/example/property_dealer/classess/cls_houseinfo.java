package com.example.property_dealer.classess;


public class cls_houseinfo {
    public String hid,username,userid,userprofile,houseloc,houserent,houseexactlocation,houseimg,phone,numberofroom,type,housewidth,househight,numberkitchen,numberwashroom;

    public cls_houseinfo() {


    }

    public cls_houseinfo(String hid, String username, String userid, String userprofile, String houseloc, String houserent, String houseexactlocation, String houseimg, String phone, String numberofroom, String type, String housewidth, String househight, String numberkitchen, String numberwashroom) {
        this.hid = hid;
        this.username = username;
        this.userid = userid;
        this.userprofile = userprofile;
        this.houseloc = houseloc;
        this.houserent = houserent;
        this.houseexactlocation = houseexactlocation;
        this.houseimg = houseimg;
        this.phone = phone;
        this.numberofroom = numberofroom;
        this.type = type;
        this.housewidth = housewidth;
        this.househight = househight;
        this.numberkitchen = numberkitchen;
        this.numberwashroom = numberwashroom;
    }

    public String getHousewidth() {
        return housewidth;
    }

    public void setHousewidth(String housewidth) {
        this.housewidth = housewidth;
    }

    public String getHousehight() {
        return househight;
    }

    public void setHousehight(String househight) {
        this.househight = househight;
    }

    public String getNumberkitchen() {
        return numberkitchen;
    }

    public void setNumberkitchen(String numberkitchen) {
        this.numberkitchen = numberkitchen;
    }

    public String getNumberwashroom() {
        return numberwashroom;
    }

    public void setNumberwashroom(String numberwashroom) {
        this.numberwashroom = numberwashroom;
    }

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    public String getNumberofroom() {
        return numberofroom;
    }

    public void setNumberofroom(String numberofroom) {
        this.numberofroom = numberofroom;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserprofile() {
        return userprofile;
    }

    public void setUserprofile(String userprofile) {
        this.userprofile = userprofile;
    }

    public String getHouseloc() {
        return houseloc;
    }

    public void setHouseloc(String houseloc) {
        this.houseloc = houseloc;
    }

    public String getHouserent() {
        return houserent;
    }

    public void setHouserent(String houserent) {
        this.houserent = houserent;
    }

    public String getHouseexactlocation() {
        return houseexactlocation;
    }

    public void setHouseexactlocation(String houseexactlocation) {
        this.houseexactlocation = houseexactlocation;
    }



    public String getHouseimg() {
        return houseimg;
    }

    public void setHouseimg(String houseimg) {
        this.houseimg = houseimg;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}