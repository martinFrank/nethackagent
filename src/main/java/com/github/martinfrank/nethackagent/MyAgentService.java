package com.github.martinfrank.nethackagent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyAgentService {

    private static final Logger logger = LoggerFactory.getLogger(MyAgentService.class);

    private final NethackAgent agent;

    @Autowired
    public MyAgentService(NethackAgent agent){
        this.agent = agent;
    }
    public void startAgent() {
        logger.info("starting agent...");
        agent.runAgent();
    }
}
