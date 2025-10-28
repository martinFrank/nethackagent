package com.github.martinfrank.nethackagent;

import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

@Service
public class MyAgentService {

    private NethackAgent agent;

    @Inject
    public MyAgentService(NethackAgent agent){
        this.agent = agent;
    }
    public void startAgent() {
        System.out.println("agent could be startet here");
        agent.runAgent();
    }
}