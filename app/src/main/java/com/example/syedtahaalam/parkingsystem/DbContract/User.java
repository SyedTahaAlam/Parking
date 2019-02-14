package com.example.syedtahaalam.parkingsystem.DbContract;

public class User {
    private String mName;
    private String mEmail;
    private String mSeatNo;
    private String mPassword;
    private  String type;
    public User() {
    }

    public User(String mName, String mEmail, String mSeatNo, String mPassword, String mPhoneNo) {
        this.mName = mName;
        this.mEmail = mEmail;
        this.mSeatNo = mSeatNo;
        this.mPassword = mPassword;
        this.mPhoneNo = mPhoneNo;
        this.type="user";
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmSeatNo() {
        return mSeatNo;
    }

    public void setmSeatNo(String mSeatNo) {
        this.mSeatNo = mSeatNo;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmPhoneNo() {
        return mPhoneNo;
    }

    public void setmPhoneNo(String mPhoneNo) {
        this.mPhoneNo = mPhoneNo;
    }

    private String mPhoneNo;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
