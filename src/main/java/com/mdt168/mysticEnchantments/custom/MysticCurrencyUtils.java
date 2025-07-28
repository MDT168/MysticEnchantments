package com.mdt168.mysticEnchantments.custom;

import com.mdt168.mysticEnchantments.enchants.MysticEnchants;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public class MysticCurrencyUtils {
    private static final Plugin PLUGIN = JavaPlugin.getProvidingPlugin(MysticCurrencyUtils.class);
    private static final NamespacedKey SOULS = new NamespacedKey(PLUGIN, "mystic_souls");
    private static final NamespacedKey FRAGMENTS = new NamespacedKey(PLUGIN, "mystic_fragments");
    private static final NamespacedKey SAP = new NamespacedKey(PLUGIN, "mystic_sap");

    public static final Map<Material, Double> ORE_DROP_MODIFIERS = Map.ofEntries(
            Map.entry(Material.COAL_ORE, 0.3),
            Map.entry(Material.COPPER_ORE, 0.4),
            Map.entry(Material.IRON_ORE, 0.6),
            Map.entry(Material.GOLD_ORE, 0.9),
            Map.entry(Material.REDSTONE_ORE, 0.8),
            Map.entry(Material.LAPIS_ORE, 0.85),
            Map.entry(Material.DIAMOND_ORE, 1.5),
            Map.entry(Material.EMERALD_ORE, 1.8),
            Map.entry(Material.NETHER_QUARTZ_ORE, 0.7),
            Map.entry(Material.DEEPSLATE_COAL_ORE, 0.35),
            Map.entry(Material.DEEPSLATE_COPPER_ORE, 0.45),
            Map.entry(Material.DEEPSLATE_IRON_ORE, 0.65),
            Map.entry(Material.DEEPSLATE_GOLD_ORE, 1.0),
            Map.entry(Material.DEEPSLATE_DIAMOND_ORE, 1.6),
            Map.entry(Material.DEEPSLATE_EMERALD_ORE, 2.0),
            Map.entry(Material.DEEPSLATE_LAPIS_ORE, 0.9),
            Map.entry(Material.DEEPSLATE_REDSTONE_ORE, 0.85),
            Map.entry(Material.NETHER_GOLD_ORE, 0.5),
            Map.entry(Material.ANCIENT_DEBRIS, 3.0)
    );

    private static final double SAP_BASE_CHANCE = 0.5;
    private static final double FRAGMENTS_BASE_CHANCE = 1.5;
    private static final double SOULS_BASE_CHANCE = 5;

    /**
     * Adds a specified number of Mystic Souls to the player.
     *
     * @param player the player to add the souls to
     * @param amount the number of souls to add
     */
    public static void addSouls(Player player, int amount) {
        setSouls(player, getSouls(player) + amount);
    }
    /**
     * Removes a specified number of Mystic Souls from the player.
     *
     * @param player the player to remove the souls from
     * @param amount the number of souls to remove
     * @return {@code true} if the player had enough souls, and they were removed;
     *         {@code false} if the player did not have enough souls
     */
    public static boolean removeSouls(Player player, int amount) {
        if (getSouls(player) >= amount) {
            setSouls(player, getSouls(player) - amount);
            return true;
        } else return false;
    }
    /**
     * Sets the amount of Mystic Souls The Player currently has.
     *
     * @param player the player to set the souls of
     * @param amount the new amount of souls
     */
    public static void setSouls(Player player, int amount) {
        player.getPersistentDataContainer().set(SOULS, PersistentDataType.INTEGER, amount);
    }
    /**
     * Gets the amount of Mystic Souls the player currently has
     *
     * @param player the player to get the amount of souls from
     * @return amount of souls the player has
     *
     */
    public static int getSouls(Player player) {
        return player.getPersistentDataContainer().getOrDefault(SOULS, PersistentDataType.INTEGER, 0);
    }


    /**
     * Adds a specified number of Mystic Fragments to the player.
     *
     * @param player the player to add the Mystic Fragments to
     * @param amount the number of souls to add
     */
    public static void addFragments(Player player, int amount) {
        setFragments(player, getFragments(player) + amount);
    }
    /**
     * Removes a specified number of Mystic Fragments from the player.
     *
     * @param player the player to remove the souls from
     * @param amount the number of fragments to remove
     * @return {@code true} if the player had enough fragments, and they were removed;
     *         {@code false} if the player did not have enough fragments
     */
    public static boolean removeFragments(Player player, int amount) {
        if (getFragments(player) >= amount) {
            setFragments(player, getFragments(player) - amount);
            return true;
        } else return false;
    }
    /**
     * Sets the amount of Mystic Fragments The Player currently has.
     *
     * @param player the player to set the fragments of
     * @param amount the new amount of fragments
     */
    public static void setFragments(Player player, int amount) {
        player.getPersistentDataContainer().set(FRAGMENTS, PersistentDataType.INTEGER, amount);
    }
    /**
     * Gets the amount of Mystic Fragments the player currently has
     *
     * @param player the player to get the amount of fragments from
     * @return amount of fragments the player has
     *
     */
    public static int getFragments(Player player) {
        return player.getPersistentDataContainer().getOrDefault(FRAGMENTS, PersistentDataType.INTEGER, 0);
    }

    /**
     * Adds a specified number of Mystic Sap to the player.
     *
     * @param player the player to add the Mystic Sap to
     * @param amount the number of sap to add
     */
    public static void addSap(Player player, int amount) {
        setSap(player, getSap(player) + amount);
    }
    /**
     * Removes a specified number of Mystic Sap from the player.
     *
     * @param player the player to remove the Sap from
     * @param amount the number of Sap to remove
     * @return {@code true} if the player had enough Sap, and they were removed;
     *         {@code false} if the player did not have enough Sap
     */
    public static boolean removeSap(Player player, int amount) {
        if (getSap(player) >= amount) {
            setSap(player, getSap(player) - amount);
            return true;
        } else return false;
    }
    /**
     * Sets the amount of Mystic Sap The Player currently has.
     *
     * @param player the player to set the sap of
     * @param amount the new amount of sap
     */
    public static void setSap(Player player, int amount) {
        player.getPersistentDataContainer().set(SAP, PersistentDataType.INTEGER, amount);
    }
    /**
     * Gets the amount of Mystic Sap the player currently has
     *
     * @param player the player to get the amount of sap from
     * @return amount of sap the player has
     *
     */
    public static int getSap(Player player) {
        return player.getPersistentDataContainer().getOrDefault(SAP, PersistentDataType.INTEGER, 0);
    }


    /**
     * Gives the final chance after enchantments additions
     *
     * @param player The player to calculate the chance for
     * @return the current chance of giving a Mystic Soul on action
     */
    public static double calculateSoulsChance(Player player) {
        double chance = SOULS_BASE_CHANCE;
        if (Helper.enchantmentExists(player, MysticEnchants.REFINED_SOULS_HUNTER)) chance *= (1 + 0.1);
        if (Helper.enchantmentExists(player, MysticEnchants.ELITE_SOULS_HUNTER)) chance *= (1 + 0.25);
        if (Helper.enchantmentExists(player, MysticEnchants.MYTHIC_SOULS_HUNTER)) chance *= (1 + 0.4);
        return chance;
    }
    /**
     * Gives the final chance after enchantments additions
     *
     * @param player The player to calculate the chance for
     * @return the current chance of giving a Mystic Fragments on action
     */
    public static double calculateFragmentsChance(Player player, Block oreBlock) {
        if (!Helper.isFragmentsOre(oreBlock)) return 0;
        double chance = FRAGMENTS_BASE_CHANCE * ORE_DROP_MODIFIERS.get(oreBlock.getType());
        if (Helper.enchantmentExists(player, MysticEnchants.REFINED_FRAGMENTATION)) chance *= (1 + 0.1);
        if (Helper.enchantmentExists(player, MysticEnchants.ELITE_FRAGMENTATION)) chance *= (1 + 0.25);
        if (Helper.enchantmentExists(player, MysticEnchants.MYTHIC_FRAGMENTATION)) chance *= (1 + 0.4);
        return chance;
    }
    /**
     * Gives the final chance after enchantments additions
     *
     * @param player The player to calculate the chance for
     * @return the current chance of giving a Mystic Sap on action
     */
    public static double calculateSapChance(Player player) {
        double chance = SAP_BASE_CHANCE;
        if (Helper.enchantmentExists(player, MysticEnchants.REFINED_SAPSURGE)) chance *= (1 + 0.1);
        if (Helper.enchantmentExists(player, MysticEnchants.ELITE_SAPSURGE)) chance *= (1 + 0.25);
        if (Helper.enchantmentExists(player, MysticEnchants.MYTHIC_SAPSURGE)) chance *= (1 + 0.4);
        return chance;
    }

    /**
     * Calculates the soul worth, can be affected with Humane enchantments
     * @param player the player to calculate the worth of souls of
     * @return the amount of xp the soul worth for a player
     */
    public static double calculateSoulsExchange(Player player) {
        double mysticWorth = 2.6;
        if (Helper.enchantmentExists(player, MysticEnchants.REFINED_LEECHBOUND)) mysticWorth = Helper.round(mysticWorth * 1.2, 2);
        if (Helper.enchantmentExists(player, MysticEnchants.ELITE_LEECHBOUND)) mysticWorth = Helper.round(mysticWorth * 1.4, 2);
        if (Helper.enchantmentExists(player, MysticEnchants.MYTHIC_LEECHBOUND)) mysticWorth = Helper.round(mysticWorth * 1.75, 2);
        return mysticWorth;
    }

    /**
     * Calculates the Sap worth, can be affected with Humane enchantments
     * @param player the player to calculate the worth of saps of
     * @return the amount of xp the saps worth for a player
     */
    public static double calculateSapExchange(Player player) {
        double mysticWorth = 0.7;
        if (Helper.enchantmentExists(player, MysticEnchants.REFINED_VERDURE)) mysticWorth = Helper.round(mysticWorth * 1.2, 2);
        if (Helper.enchantmentExists(player, MysticEnchants.ELITE_VERDURE)) mysticWorth = Helper.round(mysticWorth * 1.4, 2);
        if (Helper.enchantmentExists(player, MysticEnchants.MYTHIC_VERDURE)) mysticWorth = Helper.round(mysticWorth * 1.75, 2);
        return mysticWorth;
    }
    /**
     * Calculates the fragments worth, can be affected with Humane enchantments
     * @param player the player to calculate the worth of fragments of
     * @return the amount of xp the fragments worth for a player
     */
    public static double calculateFragmentsExchange(Player player) {
        double mysticWorth = 1.4;
        if (Helper.enchantmentExists(player, MysticEnchants.REFINED_CRYSTABURST)) mysticWorth = Helper.round(mysticWorth * 1.2, 2);
        if (Helper.enchantmentExists(player, MysticEnchants.ELITE_CRYSTABURST)) mysticWorth = Helper.round(mysticWorth * 1.4, 2);
        if (Helper.enchantmentExists(player, MysticEnchants.MYTHIC_CRYSTABURST)) mysticWorth = Helper.round(mysticWorth * 1.75, 2);
        return mysticWorth;
    }

    /**
     * Calculates the total xp the player should get after exchanging his souls
     * @param player the player to calculate the souls of
     * @return the total xp the player should get for all of their souls
     */
    public static double calculateTotalSoulsXpWorth(Player player) {
        return Helper.round(getSouls(player) * calculateSoulsExchange(player), 2);
    }
    /**
     * Calculates the total xp the player should get after exchanging his saps
     * @param player the player to calculate the saps of
     * @return the total xp the player should get for all of their saps
     */
    public static double calculateTotalSapXpWorth(Player player) {
        return Helper.round(getSap(player) * calculateSapExchange(player), 2);
    }

    public static double calculateTotalFragmentsXpWorth(Player player) {
        return Helper.round(getFragments(player) * calculateFragmentsExchange(player), 2);
    }

    /**
     * Takes all souls from the player and converts them to xp
     * @param player the player to give them the xp they deserve depending on their amount of resources
     */
    public static void exchangeSouls(Player player) {
        double mysticCoinsToGive = calculateTotalSoulsXpWorth(player);
        Helper.sendMessage(player, "<gold>You exchanged <red>" + MysticCurrencyUtils.getSouls(player) + " souls <gold>to <green>" + mysticCoinsToGive + " Mystic Coins<gold>!");
        setSouls(player, 0);
        MysticCoinUtils.addCoin(player, mysticCoinsToGive);
    }

    /**
     * Takes all sap from the player and converts them to xp
     * @param player the player to give them the xp they deserve depending on their amount of resources
     */
    public static void exchangeSap(Player player) {
        double mysticCoinsToGive = calculateTotalSapXpWorth(player);
        Helper.sendMessage(player, "<gold>You exchanged <red>" + MysticCurrencyUtils.getSap(player) + " saps <gold>to <green>" + mysticCoinsToGive + " Mystic Coins<gold>!");
        setSap(player, 0);
        MysticCoinUtils.addCoin(player, mysticCoinsToGive);
    }

    /**
     * Takes all fragments from the player and converts them to xp
     * @param player the player to give them the xp they deserve depending on their amount of resources
     */
    public static void exchangeFragments(Player player) {
        double mysticCoinsToGive = calculateTotalFragmentsXpWorth(player);
        Helper.sendMessage(player, "<gold>You exchanged <red>" + MysticCurrencyUtils.getFragments(player) + " fragments <gold>to <green>" + mysticCoinsToGive + " Mystic Coins<gold>!");
        setFragments(player, 0);
        MysticCoinUtils.addCoin(player, mysticCoinsToGive);
    }
}
