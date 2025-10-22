package com.github.martinfrank.nethackagent.nethackagent;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
class NetHackAgentApplicationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(NetHackAgentApplicationTests.class);

//	@Test
	void contextLoads() {
        LOGGER.debug("debug test!");
	}

}
