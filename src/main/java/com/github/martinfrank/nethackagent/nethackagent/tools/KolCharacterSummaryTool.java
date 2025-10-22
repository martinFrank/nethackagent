package com.github.martinfrank.nethackagent.nethackagent.tools;

import dev.langchain4j.agent.tool.Tool;
import net.sourceforge.kolmafia.KoLCharacter;
import net.sourceforge.kolmafia.request.LoginRequest;
import net.sourceforge.kolmafia.request.LogoutRequest;

public class KolCharacterSummaryTool {

    @Tool(
            name = "KolCharacterSummaryTool"
            , value = """
            Liest den aktuellen Status des Kingdom of Loathing Spielers (HP, MP, Meat usw.).
            Verwende dieses Tool, wenn du Spielzustand oder Fortschritt analysieren willst.
            """
    )
    public KolCharacterSummary execute() {

        System.out.println("execute KolCharacterSummaryTool...");

        LoginRequest login = new LoginRequest(KolLoginData.USERNAME, KolLoginData.PASSWORD);
        login.run();

        if (!KoLCharacter.getUserName().equalsIgnoreCase(KolLoginData.USERNAME)) {
            System.out.println("Login fehlgeschlagen! - es konnten keine Spielerinformation ausgewertet werden");
            throw new IllegalStateException("Login fehlgeschlagen! - es konnten keine Spielerinformation ausgewertet werden");
        }
        
        KolCharacterSummary summary = new KolCharacterSummary();
        summary.setUsername(KolLoginData.USERNAME);
        summary.setPassword(KolLoginData.PASSWORD);
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

        LogoutRequest.getLastResponse();

        System.out.println("character");
        System.out.println(summary);

        return summary;
    }
}
