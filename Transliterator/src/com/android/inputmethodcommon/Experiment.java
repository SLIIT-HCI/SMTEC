package com.example.smtec;

public class Experiment {

    private int session;
    private String email;
    private String duration;
    private String s1;
    private String s2;
    private int editDistance;
    private int sync_status;
    private String sensorName;
    private double val_x;
    private double val_y;
    private double val_z;

    public Experiment(String email,int session,String duration,String s1,String s2,int editDistance,int sync_status,String sensorName,double val_x,double val_y,double val_z) {
        this.setEmail(email);
        this.setSession(session);
        this.setDuration(duration);
        this.setS1(s1);
        this.setS2(s2);
        this.setEditDistance(editDistance);
        this.setSync_status(sync_status);
        this.setSensorName(sensorName);
        this.setVal_x(val_x);
        this.setVal_y(val_y);
        this.setVal_z(val_z);
    }

    public int getSession() {
        return session;
    }

    public void setSession(int session) {
        this.session = session;
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

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public double getVal_x() {
        return val_x;
    }

    public void setVal_x(double val_x) {
        this.val_x = val_x;
    }

    public double getVal_y() {
        return val_y;
    }

    public void setVal_y(double val_y) {
        this.val_y = val_y;
    }

    public double getVal_z() {
        return val_z;
    }

    public void setVal_z(double val_z) {
        this.val_z = val_z;
    }
}
