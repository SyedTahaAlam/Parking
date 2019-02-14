package com.example.syedtahaalam.parkingsystem.DbContract;

public class ReplyContract {

    private String Reply;
    private String Feedback;
    private String UID;

    public ReplyContract() {
    }

    public ReplyContract(String reply, String feedback, String UID) {
        Reply = reply;
        Feedback = feedback;
        this.UID = UID;
    }

    public String getReply() {
        return Reply;
    }

    public void setReply(String reply) {
        Reply = reply;
    }

    public String getFeedback() {
        return Feedback;
    }

    public void setFeedback(String feedback) {
        Feedback = feedback;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
