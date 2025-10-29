package com.github.martinfrank.nethackagent.tools.skill;


import com.github.martinfrank.nethackagent.LoginManager;
import dev.langchain4j.agent.tool.Tool;
import net.sourceforge.kolmafia.KoLCharacter;
import net.sourceforge.kolmafia.persistence.SkillDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SkillTool {

    @Tool(
            name = "SkillTool"
            , value = """
            erzeugt eine Liste von allen Skills, die der Spieler besitzt. Verwende diese Tool, wenn du
            wissen willst, welche Skills ein Spieler anwenden kann.
            """
    )
    public List<Skill> getSkills() {
        System.out.println("using skill tool");
        LoginManager.ensureLogin();

        List<Skill> skills = new ArrayList<>();
        for(Map.Entry<Integer, String> kolSkill : SkillDatabase.entrySet()){
            int skillId = kolSkill.getKey();
            boolean hasSkill = KoLCharacter.hasSkill(skillId);
            if(hasSkill) {
                List<String> tags = SkillDatabase.getSkillTags(skillId).stream()
                        .map(Enum::name).toList();

                Skill skill = new Skill();
                skill.setSkillId(skillId);
                skill.setName(kolSkill.getValue());
                skill.setTags(tags);
                skills.add(skill);
            }
        }

        skills.forEach(System.out::println);

        return skills;
    }
}
