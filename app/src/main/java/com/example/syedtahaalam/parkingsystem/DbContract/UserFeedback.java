package com.example.syedtahaalam.parkingsystem.DbContract;

public class UserFeedback {
    private String mFeedback;
    private String mName;

    public UserFeedback() {
    }

    public UserFeedback(String mFeedback, String mName) {

        this.mFeedback = mFeedback;
        this.mName = mName;
    }

    public String getmFeedback() {
        return mFeedback;
    }

    public void setmFeedback(String mFeedback) {
        this.mFeedback = mFeedback;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
}
