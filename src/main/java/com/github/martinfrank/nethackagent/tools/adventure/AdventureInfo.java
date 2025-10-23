package com.github.martinfrank.nethackagent.tools.adventure;


public class AdventureInfo {


    private String zone;
    private String parentZone;
    private String adventureId;
    private int recommendedStat;
    private boolean isNonCombatsOnly;
    private int adventureNumber;
    private boolean canAdventure;
    private String rootZone;

    private String name;

    private String parentZoneDescription;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public void setParentZone(String parentZone) {
        this.parentZone = parentZone;
    }

    public void setAdventureId(String adventureId) {
        this.adventureId = adventureId;
    }

    public void setRecommendedStat(int recommendedStat) {
        this.recommendedStat = recommendedStat;
    }

    public void setNonCombatsOnly(boolean nonCombatsOnly) {
        isNonCombatsOnly = nonCombatsOnly;
    }

    public void setAdventureNumber(int adventureNumber) {
        this.adventureNumber = adventureNumber;
    }

    public void setCanAdventure(boolean canAdventure) {
        this.canAdventure = canAdventure;
    }

    public String getRootZone() {
        return rootZone;
    }

    public void setRootZone(String rootZone) {
        this.rootZone = rootZone;
    }

    public String getZone() {
        return zone;
    }

    public String getParentZone() {
        return parentZone;
    }

    public String getAdventureId() {
        return adventureId;
    }

    public int getRecommendedStat() {
        return recommendedStat;
    }

    public boolean isNonCombatsOnly() {
        return isNonCombatsOnly;
    }

    public int getAdventureNumber() {
        return adventureNumber;
    }

    public boolean isCanAdventure() {
        return canAdventure;
    }

    public String getParentZoneDescription() {
        return parentZoneDescription;
    }

    public void setParentZoneDescription(String parentZoneDescription) {
        this.parentZoneDescription = parentZoneDescription;
    }

    @Override
    public String toString() {
        return "KolAdventureSummary{" +
                "name='" + name + '\'' +
                ", zone='" + zone + '\'' +
                ", parentZone='" + parentZone + '\'' +
                ", adventureId='" + adventureId + '\'' +
                ", recommendedStat=" + recommendedStat +
//                ", isNonCombatsOnly=" + isNonCombatsOnly +
//                ", adventureNumber=" + adventureNumber +
//                ", canAdventure=" + canAdventure +
//                ", parentZoneDescription='" + parentZoneDescription + '\'' +
//                ", rootZone='" + rootZone + '\'' +
                '}';
    }
}
