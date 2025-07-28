package com.mdt168.mysticEnchantments.custom;

import com.mdt168.mysticEnchantments.enchants.HumaneEnchantment;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class PlayerDataUtils {
    public static final NamespacedKey key = new NamespacedKey(JavaPlugin.getProvidingPlugin(PlayerDataUtils.class), "mysticenchanter");
    public static boolean removeData(Player player, String data) {
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        String current = pdc.getOrDefault(key, PersistentDataType.STRING, "");
        String[] entries = current.split(",");
        pdc.set(key, PersistentDataType.STRING, "");
        boolean removed = false;
        for (String entry : entries) {
            if (entry.equals(data)) {
                removed = true;
            } else {
                addDataToPlayer(player, entry);
            }
        }
        return removed;
    }
    public static String[] removeAndGetAllData(Player player) {
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        String current = pdc.getOrDefault(key, PersistentDataType.STRING, "");
        String[] data = current.split(",");
        pdc.set(key, PersistentDataType.STRING, "");
        return data;
    }

    /**
     * Adds a string to the player's PDC list-style storage.
     * @param player The player.
     * @param data The string to add.
     * @return true if the data was added (it didn’t exist before), false if it was already present.
     */
    public static boolean addDataToPlayer(Player player, String data) {
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        String current = pdc.getOrDefault(key, PersistentDataType.STRING, "");

        // Convert to a simple comma-separated list format
        String[] entries = current.isEmpty() ? new String[0] : current.split(",");
        for (String entry : entries) {
            if (entry.equals(data)) return false; // Already exists
        }

        // Add new entry
        String updated = current.isEmpty() ? data : current + "," + data;
        pdc.set(key, PersistentDataType.STRING, updated);
        return true;
    }


    /**
     * Checks if a given string is stored in the player's PDC.
     * @param player The player.
     * @param data The string to check.
     * @return true if the data exists, false otherwise.
     */
    public static boolean checkIfDataExists(Player player, String data) {
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        String current = pdc.get(key, PersistentDataType.STRING);
        if (current == null || current.isEmpty()) return false;

        for (String entry : current.split(",")) {
            if (entry.equals(data)) return true;
        }
        return false;
    }

    public static List<HumaneEnchantment> getEnchantmentsOnPlayer(Player player) {
        PersistentDataContainer container = player.getPersistentDataContainer();
        String data = container.getOrDefault(key, PersistentDataType.STRING, "");
        List<HumaneEnchantment> currentEnchantments = GuiHelper.getHumaneEnchantments();
        List<HumaneEnchantment> enchantments = new ArrayList<>();
        for (String enchantString : data.split(",")) {
            for (HumaneEnchantment enchantment : currentEnchantments) {
                if (enchantment.getId().equals(enchantString)) enchantments.add(enchantment);
            }
        }
        return enchantments;
    }
}
