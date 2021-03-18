package com.example.smtec;

public class Experiment {

    private int userId;
    private String email;
    private String duration;
    private String s1;
    private String s2;
    private int editDistance;
    private int id;
    private int sync_status;

    public Experiment(int userId,String email,String duration,String s1,String s2,int editDistance,int id,int sync_status) {
        this.userId = userId;
        this.email = email;
        this.duration = duration;
        this.s1 = s1;
        this.s2 = s2;
        this.editDistance = editDistance;
        this.id = id;
        this.sync_status = sync_status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSync_status() {
        return sync_status;
    }

    public void setSync_status(int sync_status) {
        this.sync_status = sync_status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEditDistance() {
        return editDistance;
    }

    public void setEditDistance(int editDistance) {
        this.editDistance = editDistance;
    }

    public String getS2() {
        return s2;
    }

    public void setS2(String s2) {
        this.s2 = s2;
    }

    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
