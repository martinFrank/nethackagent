package com.github.martinfrank.nethackagent.nethackagent.tools;

import dev.langchain4j.agent.tool.Tool;
import net.sourceforge.kolmafia.KoLAdventure;
import net.sourceforge.kolmafia.persistence.AdventureDatabase;
import net.sourceforge.kolmafia.request.LoginRequest;
import net.sourceforge.kolmafia.request.LogoutRequest;

import java.util.List;

public class KolAdventureSummaryTool {

    @Tool(
            name = "KolAdventureSummaryTool"
            , value = """
            erzeugt eine Liste von Adventures, die der Spieler erreichen kann.
            Verwende dieses Tool, wenn du wissen willst, welche Orte vom Spieler besucht werden können.
            """
    )
    public List<KolAdventureSummary> execute() {
        System.out.println("execute KolAdventureSummaryTool...");


        LoginRequest login = new LoginRequest(KolLoginData.USERNAME, KolLoginData.PASSWORD);

        login.run();
        List<KoLAdventure> adventuresRaw = AdventureDatabase.getAsLockableListModel();
        List<KolAdventureSummary> adventures = adventuresRaw.stream()
                .map(KolAdventureSummaryTool::mapToSummary)
                .filter(adv -> ! adv.getParentZone().equals("Clan Basement"))
                .filter(adv -> adv.getAdventureNumber() >= 0)
                .filter(KolAdventureSummary::isCanAdventure)
                .toList();

        LogoutRequest.getLastResponse();
        System.out.println("adventures");
        adventures.forEach(System.out::println);

        return adventures;
    }

    private static KolAdventureSummary mapToSummary(KoLAdventure adv) {
        KolAdventureSummary summary = new KolAdventureSummary();
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
