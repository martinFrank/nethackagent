package com.github.martinfrank.nethackagent.nethackagent.tools;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KolCharacterSummaryToolTest {

//        @Test
    void testAdventureSummary(){
        KolCharacterSummary result = new KolCharacterSummaryTool().execute();
        System.out.println(result);
    }

}