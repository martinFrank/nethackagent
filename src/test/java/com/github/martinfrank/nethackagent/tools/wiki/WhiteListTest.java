package com.github.martinfrank.nethackagent.tools.wiki;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WhiteListTest {

    @Test
    void testWhiteList(){
        String candidate = "https://wiki.kingdomofloathing.com/Ascension";
        Assertions.assertTrue(WhiteList.isWhiteListed(candidate));
    }

}