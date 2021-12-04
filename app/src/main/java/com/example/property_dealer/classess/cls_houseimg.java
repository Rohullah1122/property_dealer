package com.example.property_dealer.classess;

public class cls_houseimg {
    String hid,userid,userimg,houseimg;

    public cls_houseimg(){}


    public cls_houseimg(String hid, String userid, String userimg, String houseimg) {
        this.hid = hid;
        this.userid = userid;
        this.userimg = userimg;
        this.houseimg = houseimg;
    }


    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserimg() {
        return userimg;
    }

    public void setUserimg(String userimg) {
        this.userimg = userimg;
    }

    public String getHouseimg() {
        return houseimg;
    }

    public void setHouseimg(String houseimg) {
        this.houseimg = houseimg;
    }
}
