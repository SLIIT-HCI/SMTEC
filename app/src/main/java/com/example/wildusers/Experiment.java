package com.example.wildusers;

public class Experiment {

    int editDistance;
    long duration;
    String stimulus,response;
    String []durationTimeStamps;

    public Experiment(String[] durationTimeStamps,long duration, String stimulus,String response, int editDistance) {

        this.durationTimeStamps = durationTimeStamps;
        this.duration = duration;
        this.stimulus = stimulus;
        this.response = response;
        this.editDistance = editDistance;
    }

    public String getStimulus() {
        return stimulus;
    }
    public String getResponse() {
        return response;
    }

    public int getEditDistance() {
        return editDistance;
    }

    public String[] getDurationTimeStamps() {
        return durationTimeStamps;
    }
    public long getDuration() {
        return duration;
    }
}
