package com.example.labexperiment;

import java.time.LocalDateTime;

public class Experiment {

    String email, stimulus, response;
    int session,editDistance;
    String []durationTimeStamps;

    public Experiment(String email, int session, String[] durationTimeStamps, String stimulus, String response, int editDistance) {
        this.email = email;
        this.session = session;
        this.durationTimeStamps = durationTimeStamps;
        this.stimulus = stimulus;
        this.response = response;
        this.editDistance = editDistance;
    }

    public String getEmail() {
        return email;
    }

    public String getStimulus() {
        return stimulus;
    }

    public String getResponse() {
        return response;
    }

    public int getSession() {
        return session;
    }

    public int getEditDistance() {
        return editDistance;
    }

    public String[] getDurationTimeStamps() {
        return durationTimeStamps;
    }
}
