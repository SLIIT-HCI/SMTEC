package com.example.wildusers;

public class WildExperiment {
    private int userId;
    private String duration;
    private String s1;
    private String s2;
    private int editDistance;

    public WildExperiment(int userId, String duration, String s1, String s2, int editDistance) {
        this.userId = userId;
        this.duration = duration;
        this.s1 = s1;
        this.s2 = s2;
        this.editDistance = editDistance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
