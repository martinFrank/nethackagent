package com.github.martinfrank.nethackagent.tools.wiki;

import dev.langchain4j.agent.tool.Tool;

public class WhiteListTool {

    @Tool(name = "WhiteListTool",
            value = """
                    Dieses Tool liefert dir eine Liste aller erlaubten WikiUrls. Wenn du das WikiTool verwendest, kannst
                    du nur urls aus dieser Liste verwenden, andere sind nicht erlaubt. nutze dieses Tool um eine gültige
                    wikiUrl zu finden. ein guter anfang zu suchen ist die wiki main page 'https://wiki.kingdomofloathing.com/Main_Page'
                    """)
    public String[] getWhiteListed(){
        return WhiteList.getWhiteList().toArray(new String[]{});
    }
}
