package com.example.wildusers.Database.OnlineDB.Model;

public class W_Questionnaire {
    private int id;
    private String user_id;
    private double move;
    private double walk;
    private double busy;
    private double tired;

    public W_Questionnaire(int id, String user_id, double move, double walk, double busy, double tired) {
        this.id = id;
        this.user_id = user_id;
        this.move = move;
        this.walk = walk;
        this.busy = busy;
        this.tired = tired;
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

    public double getMove() {
        return move;
    }

    public void setMove(double move) {
        this.move = move;
    }

    public double getWalk() {
        return walk;
    }

    public void setWalk(double walk) {
        this.walk = walk;
    }

    public double getBusy() {
        return busy;
    }

    public void setBusy(double busy) {
        this.busy = busy;
    }

    public double getTired() {
        return tired;
    }

    public void setTired(double tired) {
        this.tired = tired;
    }
}
