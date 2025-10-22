package com.github.martinfrank.nethackagent.nethackagent;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AgentStartupRunner implements ApplicationRunner {

    private final MyAgentService myAgentService;

    public AgentStartupRunner(MyAgentService myAgentService) {
        this.myAgentService = myAgentService;
    }

    @Override
    public void run(ApplicationArguments args) {
        myAgentService.startAgent();
    }
}