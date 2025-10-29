package com.github.martinfrank.nethackagent.tools.quest;

public class Quest {
    private String id;

    private String name;
    private boolean isStarted;
    private boolean isFinished;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "Quest{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", isStarted=" + isStarted +
                ", isFinished=" + isFinished +
                ", status='" + status + '\'' +
                '}';
    }
}
