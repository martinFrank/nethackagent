package com.github.martinfrank.nethackagent;

import com.google.gson.Gson;

//Agenten können keine Records verarbeiten
public class PlanValidationRequest {
    public String planRequest;
    public String planResult;

    public PlanValidationRequest(String planRequest, String planResult) {
        this.planRequest = planRequest;
        this.planResult = planResult;
    }

    public String toJson(){
        return new Gson().toJson(this);
    }
}
