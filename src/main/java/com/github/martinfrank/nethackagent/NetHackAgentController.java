package com.github.martinfrank.nethackagent;


import com.github.martinfrank.nethackagent.embedding.WikiIngestor;
import com.github.martinfrank.nethackagent.tools.wiki.WhiteList;
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
    private final WikiIngestor ingestor;


    @Autowired
    public NetHackAgentController(MyAgentService myAgentService, NethackChat chat, WikiIngestor ingestor) {
        this.myAgentService = myAgentService;
        this.chat = chat;
        this.ingestor = ingestor;
    }


    @PostMapping("/agent")
    public ResponseEntity<String> startAgent( ) {
        String thePlan = myAgentService.runNethackAgent();
        return ResponseEntity.ok(thePlan);
    }

    @PostMapping("/knowhow")
    public ResponseEntity<String> startKnowhow( ) {
        String url = "https://wiki.kingdomofloathing.com/Quest_Spoilers";
//        for(String url: WhiteList.getWhiteList()) {
            logger.info("start ingesting {}", url);
//            ingestor.ingest(url);
            ingestor.ingestFromDb(url);
//        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/chat-chain")
    public ResponseEntity<String> chatChain(@RequestBody String question) {
        String response = chat.askChain(question);
        logger.info("response: {}",response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/chat-model")
    public ResponseEntity<String> chatModel(@RequestBody String question) {
        String response = chat.askModel(question);
        logger.info("response: {}",response);
        return ResponseEntity.ok(response);
    }


}
