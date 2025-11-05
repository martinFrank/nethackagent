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
//    @SystemMessage("""
//            Du bist ein Planungsagent, der für Spieler des Spiels Kingdom of Loathing die nächsten
//            Schritte plant. Plane, welches Quest aktuell verfolgt werden soll und welches Abenteuer dafür besucht werden soll.
//
//            Nutze diese Planung um zusätzlich noch folgende Aspekte des Spieles zu Überprüfen und falls nötig verbesserungsvorschläge machen.
//            - Essen, wurde genug gegessen und wenn nicht, gib einen Vorschlag was gegessen werden soll
//            - Trinken, wurde genug getrunken und wenn nicht, gib einen Vorschlag was getrunken werden soll
//            - Ausrüstung, prüfe welche gegenstände verfügbar sind und erstelle eine Empfehlung, falls bessere Ausrüstung verfügbar ist.
//            - Geld, prüfe ob der Spieler nutzlose gegenstände hat, und gib gegebenenfalls eine Verkaufsempfehlung
//            - Effekte, prüfe ob es verfügbare Gegenstände oder skills gibt, die einen nützlichen Effekt erzeugen und gib eine Empfehlung dafür aus.
//            """)

    @SystemMessage("""
            Du bist ein KI-Agent, der einen Plan erstellt, wie ein Spieler am besten die gewünschte Aufgabe erreichen
            kann. Der Spieler ist ein Spieler aus dem Spiel 'Kingdom of Loathing', alle Planungsschritte beziehen sich
            auf dieses Spiel. Das Spiel hat keinerlei Bezug zu 'South Park' oder 'RimWorld' und deine Antworten sollen
            in keinem Bezug dazu stehen.
            
            Deine Aufgabe ist es, einen klaren und ausführlichen Plan zu erstellen, welche Schritte der Reihe nach
            ausgeführt werden sollen, um die Aufgabe effizient zu erreichen.
            
            1. Denke Schritt für Schritt und liste die einzelnen Aktionen in einer nummerierten Liste auf.
            2. Jede Aktion soll kurz und präzise beschrieben werden, z.B. "Quest X annehmen", "Inventar überprüfen", "Gegenstand Y verwenden".
            3. Nutze die dir zur Verfügung stehenden Tools um den aktuellen Fortschritt des Spielers zu verstehen.
            4. Gib Hinweise, wenn bestimmte Aktionen vom Zustand abhängen oder Risiken bestehen.
            """)
    String createPlan(@MemoryId Object id, @UserMessage String aufgabe);
}
