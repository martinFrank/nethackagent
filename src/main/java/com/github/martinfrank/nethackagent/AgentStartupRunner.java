package com.github.martinfrank.nethackagent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AgentStartupRunner implements ApplicationRunner {

    private final MyAgentService myAgentService;

    @Autowired
    public AgentStartupRunner(MyAgentService myAgentService) {
        this.myAgentService = myAgentService;
    }

    @Override
    public void run(ApplicationArguments args) {
        myAgentService.startAgent();
    }
}