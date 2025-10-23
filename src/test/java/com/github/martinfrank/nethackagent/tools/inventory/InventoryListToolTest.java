package com.github.martinfrank.nethackagent.tools.inventory;

import com.github.martinfrank.nethackagent.LoginManager;

import java.util.List;

class InventoryListToolTest {

//    @Test
    void testInventoryList(){
        LoginManager.ensureLogin();

        System.out.println("inventory");
        List<Item> inventory = new InventoryListTool().getInventory();
        inventory.forEach(System.out::println);

        System.out.println("\nequipment");
        List<Item> equipment = new EquipmentListTool().getEquipment();
        equipment.forEach(System.out::println);

        LoginManager.logout();
    }
}