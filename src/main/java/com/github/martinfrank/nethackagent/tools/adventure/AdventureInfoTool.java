package com.github.martinfrank.nethackagent.tools.adventure;

import com.github.martinfrank.nethackagent.tools.LoginManager;
import dev.langchain4j.agent.tool.Tool;
import net.sourceforge.kolmafia.KoLAdventure;
import net.sourceforge.kolmafia.persistence.AdventureDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AdventureInfoTool {

    private static final Logger logger = LoggerFactory.getLogger(AdventureInfoTool.class);

    @Tool(
            name = "AdventureInfoTool"
            , value = """
            erzeugt eine Liste von Adventures, die der Spieler gerade jetzt erreichen kann.
            Verwende dieses Tool, wenn du wissen willst, welche Orte vom Spieler besucht werden können.
            """
    )
    public List<AdventureInfo> getAdventures() {
        logger.debug("using AdventureInfoTool...");

        LoginManager.ensureLogin();

        List<KoLAdventure> adventuresRaw = AdventureDatabase.getAsLockableListModel();
        List<AdventureInfo> adventures = adventuresRaw.stream()
                .map(AdventureInfoTool::mapToSummary)
                .filter(adv -> ! adv.getParentZone().equals("Clan Basement"))
                .filter(adv -> adv.getAdventureNumber() >= 0)
                .filter(AdventureInfo::isCanAdventure)
                .toList();

        logger.debug("adventures:");
        adventures.forEach(adv -> logger.debug(" - {}", adv));

        return adventures;
    }

    public static AdventureInfo mapToSummary(KoLAdventure adv) {
        AdventureInfo summary = new AdventureInfo();
        summary.setAdventureId(adv.getAdventureId());
        summary.setAdventureNumber(adv.getAdventureNumber());
        summary.setNonCombatsOnly(adv.isNonCombatsOnly());
        summary.setName(adv.getAdventureName());
        summary.setZone(adv.getZone());
        summary.setParentZone(adv.getParentZone());
        summary.setRecommendedStat(adv.getRecommendedStat());
        summary.setCanAdventure(adv.canAdventure());
        summary.setRootZone(adv.getRootZone());
        summary.setParentZoneDescription(adv.getParentZoneDescription());
        return summary;
    }

}
