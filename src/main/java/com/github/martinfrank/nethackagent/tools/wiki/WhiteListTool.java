package com.github.martinfrank.nethackagent.tools.wiki;

import dev.langchain4j.agent.tool.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WhiteListTool {

    private static final Logger logger = LoggerFactory.getLogger(WhiteListTool.class);

    @Tool(name = "WhiteListTool",
            value = """
                    Dieses Tool liefert dir eine Liste aller erlaubten WikiUrls. Wenn du das WikiTool verwendest, kannst
                    du nur urls aus dieser Liste verwenden, andere sind nicht erlaubt. nutze dieses Tool um eine gültige
                    wikiUrl zu finden. ein guter anfang zu suchen ist die wiki main page 'https://wiki.kingdomofloathing.com/Main_Page'
                    """)
    public String[] getWhiteListed(){
        logger.info("using SkillTool.getSkills()");
        return WhiteList.getWhiteList().toArray(new String[]{});
    }
}
