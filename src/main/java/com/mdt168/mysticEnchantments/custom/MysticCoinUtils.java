package com.mdt168.mysticEnchantments.custom;

import com.mdt168.mysticEnchantments.MysticEnchantments;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class MysticCoinUtils {
    private static final NamespacedKey key = new NamespacedKey(JavaPlugin.getProvidingPlugin(PlayerDataUtils.class), "mystic_coins");

    /**
     * Gets a player's Mystic Coins balance from their persistent data.
     * @param player Target player
     * @return Coin balance (0.0 if not found)
     */
    public static double getCoin(Player player) {
        return Helper.round(player.getPersistentDataContainer().getOrDefault(key, PersistentDataType.DOUBLE, 0.0), 2);
    }

    /**
     * Sets the amount of Mystic Coins the player has (0 Minimum)
     * @param player target player
     * @param amount the new balance of the player
     */
    public static void setCoin(Player player, double amount) {
        player.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, Math.max(0, Helper.round(amount, 2)));
    }
    /**
     * Adds an amount of Mystic Coins to the player
     * @param player target player
     * @param amount the amount of mystic coins that the player will get
     */
    public static void addCoin(Player player, double amount) {
        setCoin(player, getCoin(player) + amount);
    }
    /**
     * Removes an amount of Mystic Coins from the player
     * @param player target player
     * @param amount the amount of mystic coins that the player will lose
     */
    public static void removeCoin(Player player, double amount) {
        setCoin(player, getCoin(player) - amount);
    }

    /**
     * Checks if the player has an amount of Mystic Coins or Higher
     * @param player Target Player
     * @param amount the amount to check
     * @return True if the player has The Amount or higher, false otherwise
     */
    public static boolean has(Player player, double amount) {
        return getCoin(player) >= amount;
    }

    /**
     * Sends an Amount of Mystic Coins from a player to a player
     * @param from The Sender Of The Mystic Coins
     * @param to The Receiver Of The Mystic Coins
     * @param amount The Amount To Transfer from-to
     * @return True if the process completed and the amount was transferred, false otherwise
     */
    public static boolean transfer(Player from, Player to, double amount) {
        if (!has(from, amount)) return false;
        removeCoin(from, amount);
        addCoin(to, amount);
        return true;
    }
    public static String format(Player player) {
        return Gradient.BLUE.toString() + getCoin(player) + " Mystic Coins ✪";
    }
}
