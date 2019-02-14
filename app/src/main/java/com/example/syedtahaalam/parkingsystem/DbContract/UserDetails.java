package com.example.syedtahaalam.parkingsystem.DbContract;

public class UserDetails {
    private String mName;
    private String mEmail;
    private String mSeatNo;
    private String mPassword;
    private String type;
    private String reference;

    public UserDetails(){

    }
    public UserDetails(User user,String reference) {
        this.mName=user.getmName();
        this.mEmail=user.getmEmail();
        this.mSeatNo=user.getmSeatNo();
        this.mPassword=user.getmPassword();
        this.type=user.getType();
        this.reference = reference;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
