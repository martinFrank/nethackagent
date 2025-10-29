package com.github.martinfrank.nethackagent.agent;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface TaskExecutorAgent {
    @SystemMessage("""
            Du bist ein Agent, der ein Computerspiel bedienen kann. Du bekommst Aufgaben, die für das
            Spiel "Kingdom of Loathing" erstellt sind.
            
            Dein Ziel:
            1. Analysiere die Benutzeraufgabe.
            2. Wenn nötig, nutze Tools, um um die Aufgabe durchzuführen.
            3. Liefere einen Bericht, wie gut die Ausführung der Aufgabe geklappt hat
            """)
    String executeTask(@UserMessage String aufgabe);
}
