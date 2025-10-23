package com.github.martinfrank.nethackagent.tools.skill;

import java.util.List;

public class Skill {

    private int skillId;
    private String name;
    private List<String> tags;

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "skillId=" + skillId +
                ", name=" + name +
                ", tags=" + tags +
                '}';
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
