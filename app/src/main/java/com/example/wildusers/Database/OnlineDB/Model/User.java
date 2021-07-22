package com.example.wildusers.Database.OnlineDB.Model;

public class User {
    private int id;
    private String user_id;
    private String condition_wild;
    private String rotational_sequence;

    public User(int id, String user_id, String condition_wild, String rotational_sequence) {
        this.id = id;
        this.user_id = user_id;
        this.condition_wild = condition_wild;
        this.rotational_sequence = rotational_sequence;
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

    public String getCondition_wild() {
        return condition_wild;
    }

    public void setCondition_wild(String condition_wild) {
        this.condition_wild = condition_wild;
    }

    public String getRotational_sequence() {
        return rotational_sequence;
    }

    public void setRotational_sequence(String rotational_sequence) {
        this.rotational_sequence = rotational_sequence;
    }
}
