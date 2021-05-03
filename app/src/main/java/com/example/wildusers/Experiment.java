package com.example.wildusers;

public class Experiment {

    int editDistance;
    String stimulus,response;
    String []durationTimeStamps;

    public Experiment(String[] durationTimeStamps, String stimulus,String response, int editDistance) {

        this.durationTimeStamps = durationTimeStamps;
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
}