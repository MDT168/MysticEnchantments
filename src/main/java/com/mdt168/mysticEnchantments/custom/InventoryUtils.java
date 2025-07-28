package com.mdt168.mysticEnchantments.custom;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryUtils {

    public static boolean hasItems(Player player, List<ItemStack> requiredItems) {
        Inventory inv = player.getInventory();

        for (ItemStack required : requiredItems) {
            int amountNeeded = required.getAmount();
            int amountFound = 0;

            for (ItemStack item : inv.getContents()) {
                if (item == null) continue;

                if (item.isSimilar(required)) {
                    amountFound += item.getAmount();
                    if (amountFound >= amountNeeded) break;
                }
            }

            if (amountFound < amountNeeded) return false;
        }

        return true;
    }

    public static void removeItems(Player player, List<ItemStack> requiredItems) {
        Inventory inv = player.getInventory();

        for (ItemStack required : requiredItems) {
            int amountToRemove = required.getAmount();

            for (int i = 0; i < inv.getSize(); i++) {
                ItemStack item = inv.getItem(i);
                if (item == null) continue;

                if (item.isSimilar(required)) {
                    int amountInSlot = item.getAmount();

                    if (amountInSlot > amountToRemove) {
                        item.setAmount(amountInSlot - amountToRemove);
                        break;
                    } else {
                        inv.setItem(i, null);
                        amountToRemove -= amountInSlot;
                        if (amountToRemove <= 0) break;
                    }
                }
            }
        }
    }

}
