package com.mdt168.mysticEnchantments.custom;

import com.mdt168.mysticEnchantments.MysticEnchantments;
import com.mdt168.mysticEnchantments.config.ConfigSettings;
import com.mdt168.mysticEnchantments.enchants.MysticEnchants;
import com.mdt168.mysticEnchantments.custom.dataUtils.MysticBossBarUtils;
import com.mdt168.mysticEnchantments.utility.cooldown.PlayerCooldowns;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class MysticCoinHandler {
    private static final Plugin plugin = MysticEnchantments.getInstance();
    private static final NamespacedKey minCoinsEffort = new NamespacedKey(plugin, "min_coins_effort");
    private static final NamespacedKey maxCoinsEffort = new NamespacedKey(plugin, "max_coins_effort");

    private static final NamespacedKey effortPoints = new NamespacedKey(plugin, "effort_points");
    private static final NamespacedKey requiredEffortPoints = new NamespacedKey(plugin, "required_effort_points");

    private static final double neededEffortPoints = ConfigSettings.NEEDED_EFFORT_POINTS.getValue();

    private static final NamespacedKey level = new NamespacedKey(plugin, "mystic_level");
    private static final NamespacedKey booster = new NamespacedKey(plugin, "mystic_booster");

    private static final double baseDamageCoins = 0.33;
    private static final double baseBlockBreakCoins = 0.355;

    private static final double baseMin = 1.7;
    private static final double baseMax = 4.3;
    /**
     * Sets the current effort Points the player did
     * @param player Target player
     * @param amount the new effort Points amount
     */
    public static void setEffortPoints(Player player, double amount) {
        player.getPersistentDataContainer().set(effortPoints, PersistentDataType.DOUBLE, amount);
        evaluateEfforts(player);
    }
    private static void setEffortPointsNoRecurse(Player player, double amount) {
        player.getPersistentDataContainer().set(effortPoints, PersistentDataType.DOUBLE, amount);
    }

    /**
     * removes effort Points from the player
     * @param player Target player
     * @param amount the amount of effort Points will be removed
     */
    public static void removeEffortPoints(Player player, double amount) {
        setEffortPoints(player, getEffortPoints(player) - amount);
    }
    /**
     * adds effort Points for the player
     * @param player Target player
     * @param amount the amount of effort Points will be added
     */
    public static void addEffortPoints(Player player, double amount) {
        if (amount <= 0) return;
        setEffortPoints(player, getEffortPoints(player) + amount);
        MysticBossBarUtils.showBossBar(player, amount);
    }
    /**
     * The amount of effort points the player currently has
     * @param player Target player
     * @return the amount of effort points the player has
     */
    public static double getEffortPoints(Player player) {
        return Helper.round(player.getPersistentDataContainer().getOrDefault(effortPoints, PersistentDataType.DOUBLE, 0.0), 2);
    }

    public static void evaluateEfforts(Player player) {
        double currentEfforts = getEffortPoints(player);
        double requiredEfforts = getNeededEfforts();
        double givenCoins = 0;
        while (currentEfforts >= requiredEfforts) {
            currentEfforts -= requiredEfforts;
            levelUp(player);
            givenCoins += getRewardForEfforts(player);
        }
        givenCoins = Helper.round(givenCoins, 2);
        if (givenCoins > 0) {
            Helper.sendActionBar(player, MiniMessage.miniMessage().deserialize(Gradient.GREEN + "You obtained " + givenCoins + " Mystic Coins for your efforts"));
            MysticCoinUtils.addCoin(player, givenCoins);
        }
        setEffortPointsNoRecurse(player, currentEfforts);
    }

    /**
     * The final minimum amount of coins the player should have after completing a loop
     * @param player Target Player
     * @return the minimum amount of coins the player should have
     */
    public static double getMinMysticCoinsReward(Player player) {
        double min = baseMin;
        if (Helper.enchantmentExists(player, MysticEnchants.COLLECTOR)) min *= 1.1;
        double boosters = getBooster(player);
        if (boosters > 0) min *= (1 + (boosters / 100.0));
        return Helper.round(min, 2);
    }

    /**
     * The final maximum amount of coins the player should have after completing a loop
     * @param player Target Player
     * @return the maximum amount of coins the player should have
     */
    public static double getMaxMysticCoinsReward(Player player) {
        double max = baseMax;
        if (Helper.enchantmentExists(player, MysticEnchants.COLLECTOR)) max *= 1.1;
        double boosters = getBooster(player);
        if (boosters > 0) max *= (1 + (boosters / 100.0));
        return Helper.round(max, 2);
    }

    /**
     * Gives a number of Mystic Coins to the player between Min and max
     * @param player Target Player
     */
    public static void giveRewardForEfforts(Player player) {
        double min = getMinMysticCoinsReward(player);
        double max = getMaxMysticCoinsReward(player);
        double reward = Helper.round(Helper.randomBetweenInclusive(min, max), 2);
        MysticCoinUtils.addCoin(player, reward);
        Helper.sendActionBar(player, MiniMessage.miniMessage().deserialize(Gradient.BLUE + "You obtained " + reward + " Mystic Coins"));
    }
    /**
     * Gives a number of Mystic Coins to the player between Min and max
     * @param player Target Player
     */
    public static double getRewardForEfforts(Player player) {
        double min = getMinMysticCoinsReward(player);
        double max = getMaxMysticCoinsReward(player);
        if (Helper.enchantmentExists(player, MysticEnchants.COLLECTOR)) {
            min *= 1.1;
            max *= 1.1;
        }
        return Helper.round(Helper.randomBetweenInclusive(min, max), 2);
    }

    public static double getEffortsPointsForDamage(Player player, double damage) {
        double base = baseDamageCoins;
        if (Helper.enchantmentExists(player, MysticEnchants.BASIC_EXPERIENCER)) base *= Helper.getEnchantmentLevel(player, MysticEnchants.BASIC_EXPERIENCER) * 0.1 + 1;
        if (Helper.enchantmentExists(player, MysticEnchants.ENHANCED_EXPERIENCER)) base *= Helper.getEnchantmentLevel(player, MysticEnchants.ENHANCED_EXPERIENCER) * 0.15 + 1;
        if (Helper.enchantmentExists(player, MysticEnchants.REFINED_EXPERIENCER)) base *= Helper.getEnchantmentLevel(player, MysticEnchants.REFINED_EXPERIENCER) * 0.2 + 1;
        if (Helper.enchantmentExists(player, MysticEnchants.ELITE_EXPERIENCER)) base *= Helper.getEnchantmentLevel(player, MysticEnchants.ELITE_EXPERIENCER) * 0.35 + 1;
        if (Helper.enchantmentExists(player, MysticEnchants.MYTHIC_PROSPECTOR)) base *= Helper.getEnchantmentLevel(player, MysticEnchants.MYTHIC_PROSPECTOR) * 0.4 + 1;
        if (PlayerCooldowns.POTION_OF_ENLIGHTENED_EFFORTS.isOnCooldown(player)) base *= 1.5;
        return Helper.round(damage * base, 2);
    }
    public static double getEffortPointsForBlock(Player player, Block block) {
        double base = baseBlockBreakCoins;
        if (Helper.enchantmentExists(player, MysticEnchants.BASIC_PROSPECTOR)) base *= Helper.getEnchantmentLevel(player, MysticEnchants.BASIC_PROSPECTOR) * 0.1 + 1;
        if (Helper.enchantmentExists(player, MysticEnchants.ENHANCED_PROSPECTOR)) base *= Helper.getEnchantmentLevel(player, MysticEnchants.ENHANCED_PROSPECTOR) * 0.15 + 1;
        if (Helper.enchantmentExists(player, MysticEnchants.REFINED_PROSPECTOR)) base *= Helper.getEnchantmentLevel(player, MysticEnchants.REFINED_PROSPECTOR) * 0.2 + 1;
        if (Helper.enchantmentExists(player, MysticEnchants.ELITE_PROSPECTOR)) base *= Helper.getEnchantmentLevel(player, MysticEnchants.ELITE_PROSPECTOR) * 0.35 + 1;
        if (Helper.enchantmentExists(player, MysticEnchants.MYTHIC_PROSPECTOR)) base *= Helper.getEnchantmentLevel(player, MysticEnchants.MYTHIC_PROSPECTOR) * 0.45 + 1;
        if (PlayerCooldowns.POTION_OF_ENLIGHTENED_EFFORTS.isOnCooldown(player)) base *= 1.5;
        ItemRarity rarity = block.getType().asItemType().getItemRarity();
        float hardness = block.getType().getHardness();
        if (rarity == null) rarity = ItemRarity.COMMON;

        double value = switch (rarity) {
            case EPIC -> hardness * 2;
            case RARE -> hardness * 1.5;
            case COMMON -> hardness * 0.9;
            case UNCOMMON -> hardness * 1.2;
        };
        if (hardness <= 1) value = 0;

        return Helper.round(value * base, 2);
    }
    public static double getNeededEfforts() {
        return neededEffortPoints;
    }
    public static void setNeededEfforts(Player player, double amount) {
        player.getPersistentDataContainer().set(requiredEffortPoints, PersistentDataType.DOUBLE, amount);
    }

    public static double getBooster(Player player) {
        return player.getPersistentDataContainer().getOrDefault(booster, PersistentDataType.DOUBLE, 0.);
    }
    public static int getLevel(Player player) {
        return player.getPersistentDataContainer().getOrDefault(level, PersistentDataType.INTEGER, 0);
    }
    public static void setLevel(Player player, int amount) {
        player.getPersistentDataContainer().set(level, PersistentDataType.INTEGER, amount);
    }
    public static void addLevel(Player player, int amount) {
        setLevel(player, getLevel(player) + amount);
    }
    public static void levelUp(Player player) {
        addLevel(player, 1);
        setBooster(player, Helper.round(getBooster(player) + Helper.randomDouble(1.3, 3), 2));
    }
    public static void setBooster(Player player, double amount) {
        player.getPersistentDataContainer().set(booster, PersistentDataType.DOUBLE, amount);
    }

    public static float getProgress(Player player) {
        return (float) Helper.round(Math.min(1.0, getEffortPoints(player) / getNeededEfforts()) * 100, 2);
    }
    public static float getProgress(Player player, boolean percentage) {
        if (percentage) return (float) Math.min(1.0, getEffortPoints(player) / getNeededEfforts());
        return getProgress(player);
    }
}
