package com.github.martinfrank.nethackagent.agent;

import com.google.gson.Gson;

//Agenten können keine Records verarbeiten
public class PlanValidationRequest {
    public final String planRequest;
    public final String planResult;

    public PlanValidationRequest(String planRequest, String planResult) {
        this.planRequest = planRequest;
        this.planResult = planResult;
    }

    public String toJson(){
        return new Gson().toJson(this);
    }
}
