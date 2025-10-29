package com.github.martinfrank.nethackagent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyAgentService {

    private final NethackAgent agent;

    @Autowired
    public MyAgentService(NethackAgent agent){
        this.agent = agent;
    }
    public void startAgent() {
        System.out.println("starting agent...");
        agent.runAgent();
    }
}