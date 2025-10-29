package com.github.martinfrank.nethackagent.tools.inventory;

import com.github.martinfrank.nethackagent.LoginManager;
import org.junit.jupiter.api.Test;

import java.util.List;

class InventoryListToolTest {

//    @Test
    void testInventoryList(){
        LoginManager.ensureLogin();

        System.out.println("inventory");
        List<Item> inventory = new InventoryTool().getInventory();

//        System.out.println("\nequipment");
//        List<Item> equipment = new EquipmentTool().getEquipment();

        LoginManager.logout();
    }
}