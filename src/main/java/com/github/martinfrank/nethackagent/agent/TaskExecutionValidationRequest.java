package com.github.martinfrank.nethackagent.agent;

import com.fasterxml.jackson.annotation.JsonProperty;

//Agenten können keine Records verarbeiten
public class TaskExecutionValidationRequest {
    @JsonProperty
    public String task;
    @JsonProperty
    public String result;

    public TaskExecutionValidationRequest(String task, String result) {
        this.task = task;
        this.result = result;
    }
}
