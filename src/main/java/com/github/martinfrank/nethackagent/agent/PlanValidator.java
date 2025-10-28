package com.github.martinfrank.nethackagent.agent;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface PlanValidator {
    @SystemMessage("""
            Du bekommst einen Datenpaket, das aus einem entwickelte Plan der dazu gehörigen Aufgabe besteht.
            Deine Aufgabe ist es zu überprüfen, ob der Plan plausibel erscheint.
            
            Struktur des Datenpakets:
            {
                "planRequest":"die Aufgabenstellung",
                "planResult":"der entwickelte Plan"
            }
            
            Antworte nur mit true/false, denn die Ausgabe wird geparsed (Boolean.parse)
            """)
    String isPlanSuccessful(@UserMessage String requestJson);
}
