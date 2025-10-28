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
    private AdventureInfo lastVisitedAdventure;

    private String className;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getMuscularity() {
        return muscularity;
    }

    public void setMuscularity(long muscularity) {
        this.muscularity = muscularity;
    }

    public long getMysticality() {
        return mysticality;
    }

    public void setMysticality(long mysticality) {
        this.mysticality = mysticality;
    }

    public long getMoxie() {
        return moxie;
    }

    public void setMoxie(long moxie) {
        this.moxie = moxie;
    }

    public long getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(long currentHp) {
        this.currentHp = currentHp;
    }

    public long getMaximumHp() {
        return maximumHp;
    }

    public void setMaximumHp(long maximumHp) {
        this.maximumHp = maximumHp;
    }

    public long getCurrentMp() {
        return currentMp;
    }

    public void setCurrentMp(long currentMp) {
        this.currentMp = currentMp;
    }

    public long getMaximumMp() {
        return maximumMp;
    }

    public void setMaximumMp(long maximumMp) {
        this.maximumMp = maximumMp;
    }

    public long getAdventuresLeft() {
        return adventuresLeft;
    }

    public void setAdventuresLeft(long adventuresLeft) {
        this.adventuresLeft = adventuresLeft;
    }

    public long getAvailableMeat() {
        return availableMeat;
    }

    public void setAvailableMeat(long availableMeat) {
        this.availableMeat = availableMeat;
    }

    public long getInebrity() {
        return inebrity;
    }

    public void setInebrity(long inebrity) {
        this.inebrity = inebrity;
    }

    public long getFullness() {
        return fullness;
    }

    public void setFullness(long fullness) {
        this.fullness = fullness;
    }

    public AdventureInfo getLastVisitedAdventure() {
        return lastVisitedAdventure;
    }

    public void setLastVisitedAdventure(AdventureInfo lastVisitedAdventure) {
        this.lastVisitedAdventure = lastVisitedAdventure;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "KolCharacterSummary{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", className=" + className +
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
                ", lastVisitedAdventure=" + lastVisitedAdventure +
                '}';
    }
}
