package com.github.martinfrank.nethackagent.tools.player;

import com.github.martinfrank.nethackagent.tools.adventure.AdventureInfo;

import java.util.Optional;

public class PlayerInfo {
    private String username;
    private String password;

    private long muscularity;
    private long mysticality;
    private long moxie;

    private long currentHp;
    private long maximumHp;
    private long currentMp;
    private long maximumMp;
    private long adventuresLeft;
    private long availableMeat;
    private long inebrity;
    private long fullness;
    Optional<AdventureInfo> currentFight;


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMuscularity(long muscularity) {
        this.muscularity = muscularity;
    }

    public void setMysticality(long mysticality) {
        this.mysticality = mysticality;
    }

    public void setMoxie(long moxie) {
        this.moxie = moxie;
    }

    public void setCurrentHp(long currentHp) {
        this.currentHp = currentHp;
    }

    public void setMaximumHp(long maximumHp) {
        this.maximumHp = maximumHp;
    }

    public void setCurrentMp(long currentMp) {
        this.currentMp = currentMp;
    }

    public void setMaximumMp(long maximumMp) {
        this.maximumMp = maximumMp;
    }

    public void setAdventuresLeft(long adventuresLeft) {
        this.adventuresLeft = adventuresLeft;
    }

    public void setAvailableMeat(long availableMeat) {
        this.availableMeat = availableMeat;
    }

    public void setInebrity(long inebrity) {
        this.inebrity = inebrity;
    }

    public void setFullness(long fullness) {
        this.fullness = fullness;
    }

    public Optional<AdventureInfo> getCurrentFight() {
        return currentFight;
    }

    public void setCurrentFight(AdventureInfo adventureSummary) {
        this.currentFight = Optional.of(adventureSummary);
    }

    public void deleteCurrentFight() {
        this.currentFight = Optional.empty();
    }

    @Override
    public String toString() {
        return "KolCharacterSummary{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", muscularity=" + muscularity +
                ", mysticality=" + mysticality +
                ", moxie=" + moxie +
                ", currentHp=" + currentHp +
                ", maximumHp=" + maximumHp +
                ", currentMp=" + currentMp +
                ", maximumMp=" + maximumMp +
                ", adventuresLeft=" + adventuresLeft +
                ", availableMeat=" + availableMeat +
                ", inebrity=" + inebrity +
                ", fullness=" + fullness +
                ", currentFight=" + currentFight +
                '}';
    }
}
