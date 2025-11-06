package com.github.martinfrank.nethackagent.tools.player;

import com.github.martinfrank.nethackagent.tools.LoginManager;
import com.github.martinfrank.nethackagent.tools.adventure.AdventureInfoTool;
import dev.langchain4j.agent.tool.Tool;
import net.sourceforge.kolmafia.KoLAdventure;
import net.sourceforge.kolmafia.KoLCharacter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerInfoTool {

    private static final Logger logger = LoggerFactory.getLogger(PlayerInfoTool.class);

    @Tool(
            name = "PlayerInfoTool"
            , value = """
            Liest Informationen über den Spielers (HP, MP, Meat usw.) aus.
            Verwende dieses Tool, wenn du wissen willst, wie welche
            Eigenschaften der aktuellen Spielers hat.
            """
    )
    public PlayerInfo getPlayerInfo() {
        logger.info("using PlayerInfoTool.getPlayerInfo()");
        LoginManager.ensureLogin();


        PlayerInfo summary = new PlayerInfo();
        summary.setUsername(LoginManager.USERNAME);
        summary.setPassword(LoginManager.PASSWORD);
        summary.setMuscularity(KoLCharacter.getBaseMuscle());
        summary.setMysticality(KoLCharacter.getBaseMysticality());
        summary.setMoxie(KoLCharacter.getBaseMoxie());
        summary.setCurrentHp(KoLCharacter.getCurrentHP());
        summary.setMaximumHp(KoLCharacter.getMaximumHP());
        summary.setCurrentMp(KoLCharacter.getCurrentMP());
        summary.setMaximumMp(KoLCharacter.getMaximumMP());
        summary.setAdventuresLeft(KoLCharacter.getAdventuresLeft());
        summary.setAvailableMeat(KoLCharacter.getAvailableMeat());
        summary.setInebrity(KoLCharacter.getInebriety());
        summary.setFullness(KoLCharacter.getFullness());
        summary.setClassName(KoLCharacter.getAscensionClass().getName());
        summary.setLastVisitedAdventure(AdventureInfoTool.mapToSummary(KoLAdventure.lastVisitedLocation()));

        logger.debug("character: {}", summary);

        return summary;
    }
}
