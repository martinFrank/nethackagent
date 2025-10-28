package com.github.martinfrank.nethackagent.agent;

import dev.langchain4j.service.UserMessage;

public interface TaskExecutorValidator {
    @UserMessage("""
            Prüfe, ob die Ausführung des Tasks erfolgreich war. Wurde im Task gar nichts
            gemacht, ist der Task erfolgreich.
            
            Task: TaskExecutionValidationRequest.task
            Ausführung: TaskExecutionValidationRequest.result
            
            Antworte nur mit true/false, denn die Ausgabe wird geparsed (Boolean.parse)
            """)
    boolean isTaskSuccessful(TaskExecutionValidationRequest request);
}
