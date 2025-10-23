package com.github.martinfrank.nethackagent.tools.skill;

import com.github.martinfrank.nethackagent.LoginManager;

import java.util.List;

class SkillToolTest {

//    @Test
    void testSkills(){
        LoginManager.ensureLogin();

        List< Skill> skills = new SkillTool().getSkills();
        skills.forEach(System.out::println);

        LoginManager.logout();
    }
}