package com.mdt168.mysticEnchantments.custom;

import com.mdt168.mysticEnchantments.MysticEnchantments;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.Map;

public class EnchantedCrateUtils {
    private static final NamespacedKey key = new NamespacedKey(MysticEnchantments.getInstance(), "enchanted_crate_key");

    /**
     * Gets the amount of Enchanted Crate keys the player currently has
     * @param player the player to get the amount of enchanted crate keys of
     * @return An integer, showing the amount of crate keys the player has
     */
    public static int getKey(Player player) {
        return player.getPersistentDataContainer().getOrDefault(key, PersistentDataType.INTEGER, 0);
    }

    /**
     * Changes the amount of Enchanted Crate Keys the player currently has to a specific number
     * @param player The entity that the Amount of Enchanted Crate Keys will get changed
     * @param amount the new amount of crate keys the player currently has
     */
    public static void setKey(Player player, int amount) {
        player.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, amount);
    }

    /**
     * Removes a number of keys the player has (0 Minimum)
     * @param player to remove the keys from
     * @param amount the amount of keys will get removed
     */
    public static void removeKey(Player player, int amount) {
        setKey(player, Math.max(getKey(player) - amount, 0));
    }
    /**
     * Adds a number of keys to the player
     * @param player to add the keys to
     * @param amount the amount of keys will get added
     */
    public static void addKey(Player player, int amount) {
        setKey(player, getKey(player) + amount);
    }

    /**
     * Rolls the crate, removing 1 key and giving a random item
     * @param player to roll the crate for
     */
    public static void rollCrate(Player player) {
        if (getKey(player) > 0) {
            EnchantedItem chosen = Helper.rollWeightedItem(EnchantedItem.getEnchantedItems());
            if (chosen == null) {
                Helper.sendWarningMessage(player, "A problem has been detected, the weights of the items are 0 (The Item Chosen = null)");
                return;
            }
            if (!Helper.hasEmptySlot(player)) {
                Helper.sendWarningMessage(player, "You need to have an empty spot in your inventory to open the crate!");
                return;
            }
            if (chosen.giveReward(player)) {
                removeKey(player, 1);
            }
        } else Helper.sendWarningMessage(player, "you don't have enough Enchanted Crate Keys to roll this crate!");
    }
    /**
     * Rolls the crate, for free and giving a random item
     * @param player to roll the crate for
     */
    public static void freeRollCrate(Player player, CommandSender sender) {
        EnchantedItem chosen = Helper.rollWeightedItem(EnchantedItem.getEnchantedItems());
        if (chosen == null) {
            Helper.sendWarningMessage(player, "A problem has been detected, the weights of the items are 0 (The Item Chosen = null)");
            return;
        }
        if (!Helper.hasEmptySlot(player)) {
            Helper.sendWarningMessage(sender, "Rolling failed, the player doesn't have an empty spot in their inventory");
            return;
        }
        chosen.giveReward(player);
    }
}
