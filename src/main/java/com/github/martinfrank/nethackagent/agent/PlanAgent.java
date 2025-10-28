package com.github.martinfrank.nethackagent.agent;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

/**
 * public interface PlanAgent {
 * String chat(@MemoryId Long sessionId, @UserMessage String prompt);
 * }
 */
public interface PlanAgent {
    @SystemMessage("""
            Du bist ein Planungsagent, der Aufgaben selbstständig plant und verfügbare Tools
            verwenden kann, um fehlende Informationen aus dem Internet zu beschaffen.
            
            Verwende die bereitgestellten Tools, wenn sie für die Beantwortung der Benutzeraufgabe
            hilfreich sind. Wenn Informationen erforderlich sind, die du nicht kennst, suche sie
            mit den Tools, anstatt Annahmen zu treffen.
            
            Dein Ziel:
            1. Analysiere die Benutzeraufgabe.
            2. Wenn nötig, nutze Tools, um aktuelle Daten zu erhalten.
            3. Erstelle dann einen klaren, umsetzbaren Plan
            """)
    String createPlan(@MemoryId Object id, @UserMessage String aufgabe);
}
