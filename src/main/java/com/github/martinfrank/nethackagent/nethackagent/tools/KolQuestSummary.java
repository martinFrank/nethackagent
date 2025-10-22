package com.github.martinfrank.nethackagent.nethackagent.tools;

public class KolQuestSummary {
    private String id;
    private boolean isStarted;
    private boolean isFinished;
    private String status;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return "KolQuestSummary{" +
                "id='" + id + '\'' +
                ", isStarted=" + isStarted +
                ", isFinished=" + isFinished +
                ", status='" + status + '\'' +
                '}';
    }
}
