package com.github.martinfrank.nethackagent;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/nethack")
public class NetHackAgentController {

    private static final Logger logger = LoggerFactory.getLogger(NetHackAgentController.class);
    private final MyAgentService myAgentService;
    private final NethackChat chat;


    @Autowired
    public NetHackAgentController(MyAgentService myAgentService, NethackChat chat) {
        this.myAgentService = myAgentService;
        this.chat = chat;
    }


    @PostMapping("/agent")
    public ResponseEntity<String> startAgent() {
        String thePlan = myAgentService.runNethackAgent();
        return ResponseEntity.ok(thePlan);
    }

    @PostMapping("/chat-chain")
    public ResponseEntity<String> chatChain(@RequestBody String question) {
        String response = chat.askChain(question);
        logger.info("response: {}", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/chat-model")
    public ResponseEntity<String> chatModel(@RequestBody String question) {
        String response = chat.askModel(question);
        logger.info("response: {}", response);
        return ResponseEntity.ok(response);
    }
}
