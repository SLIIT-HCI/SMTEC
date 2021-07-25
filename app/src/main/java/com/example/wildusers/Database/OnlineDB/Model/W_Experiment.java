package com.example.wildusers.Database.OnlineDB.Model;

public class W_Experiment {
    private int id;
    private String user_id;
    private int session;
    private String date;
    private String stimulus;
    private String response;
    private String edit_distance;
    private int sentenseNo;
    private double duration;

    public W_Experiment(int id, String user_id, int session, String date, String stimulus, String response, String edit_distance, int sentenseNo, double duration) {
        this.id = id;
        this.user_id = user_id;
        this.session = session;
        this.date = date;
        this.stimulus = stimulus;
        this.response = response;
        this.edit_distance = edit_distance;
        this.sentenseNo = sentenseNo;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getSession() {
        return session;
    }

    public void setSession(int session) {
        this.session = session;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStimulus() {
        return stimulus;
    }

    public void setStimulus(String stimulus) {
        this.stimulus = stimulus;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getEdit_distance() {
        return edit_distance;
    }

    public void setEdit_distance(String edit_distance) {
        this.edit_distance = edit_distance;
    }

    public int getSentenseNo() {
        return sentenseNo;
    }

    public void setSentenseNo(int sentenseNo) {
        this.sentenseNo = sentenseNo;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }
}
