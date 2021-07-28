package com.example.wildusers.Database.OnlineDB.Model;

public class W_Rating {

    private int id;
    private String user_id;
    private int session;
    private double speed;
    private double accuracy;
    private String preference;
    private double easeOfUse;
    private String handPosture;
    private String comment;

    public W_Rating(int id, String user_id, int session, double speed, double accuracy, String preference, double easeOfUse, String handPosture, String comment) {
        this.id = id;
        this.user_id = user_id;
        this.session = session;
        this.speed = speed;
        this.accuracy = accuracy;
        this.preference = preference;
        this.easeOfUse = easeOfUse;
        this.handPosture = handPosture;
        this.comment = comment;
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

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public double getEaseOfUse() {
        return easeOfUse;
    }

    public void setEaseOfUse(double easeOfUse) {
        this.easeOfUse = easeOfUse;
    }

    public String getHandPosture() {
        return handPosture;
    }

    public void setHandPosture(String handPosture) {
        this.handPosture = handPosture;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
