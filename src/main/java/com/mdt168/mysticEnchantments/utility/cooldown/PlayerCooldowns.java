package com.mdt168.mysticEnchantments.utility.cooldown;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerCooldowns {
    public static final PlayerCooldown SUGGEST = new PlayerCooldown("suggest");
    public static final PlayerCooldown HEALING_WAND = new PlayerCooldown("healing_wand");
    public static final PlayerCooldown LIGHTNING_WAND = new PlayerCooldown("lightning_wand");
    public static final PlayerCooldown POTION_OF_ENLIGHTENED_EFFORTS = new PlayerCooldown("potionOfEnlightenedEffortCooldowns");
    public static final PlayerCooldown CANCEL_FALL_DAMAGE = new PlayerCooldown("cancel_fall_damage");
    public static final PlayerCooldown LAUNCH_STEP = new PlayerCooldown("launch_step");
    public static final PlayerCooldown SLEEPER_LUCK = new PlayerCooldown("sleeper_luck");
    public static final PlayerCooldown ENKINDLED_CORE = new PlayerCooldown("enkindled_core");
    public static final PlayerCooldown ENKINDLED_CORE_EFFECT = new PlayerCooldown("enkindled_core_effect");
    public static final PlayerCooldown ECLIPSE_EDGE = new PlayerCooldown("eclipse_edge");


    public static class PlayerCooldown {
        private final String name;
        private final Map<UUID, Long> cooldowns = new HashMap<>();

        public PlayerCooldown(String name) {
            this.name = name;
        }

        /**
         * Adds a cooldown for the specified player
         * @param player The player's UUID
         * @param milliseconds The cooldown duration in milliseconds
         */
        public void addCooldown(UUID player, long milliseconds) {
            cooldowns.put(player, System.currentTimeMillis() + milliseconds);
        }
        public void addCooldown(Player player, long milliseconds) {
            cooldowns.put(player.getUniqueId(), System.currentTimeMillis() + milliseconds);
        }

        /**
         * Checks if a player is on cooldown
         * @param player The player's UUID to check
         * @return true if the player is on cooldown, false otherwise
         */
        public boolean isOnCooldown(UUID player) {
            Long expirationTime = cooldowns.get(player);
            if (expirationTime == null) {
                return false;
            }

            if (System.currentTimeMillis() >= expirationTime) {
                cooldowns.remove(player);
                return false;
            }
            return true;
        }
        public boolean isOnCooldown(Player player) {
            UUID uuid = player.getUniqueId();
            Long expirationTime = cooldowns.get(uuid);
            if (expirationTime == null) {
                return false;
            }

            if (System.currentTimeMillis() >= expirationTime) {
                cooldowns.remove(uuid);
                return false;
            }
            return true;
        }

        /**
         * Gets the remaining cooldown time in milliseconds
         * @param player The player's UUID
         * @return Remaining time in ms, or 0 if no cooldown
         */
        public long getRemainingTime(UUID player) {
            Long expirationTime = cooldowns.get(player);
            if (expirationTime == null) {
                return 0;
            }

            long remaining = expirationTime - System.currentTimeMillis();
            return remaining > 0 ? remaining : 0;
        }
        public long getRemainingTime(Player player) {
            Long expirationTime = cooldowns.get(player.getUniqueId());
            if (expirationTime == null) {
                return 0;
            }

            long remaining = expirationTime - System.currentTimeMillis();
            return remaining > 0 ? remaining : 0;
        }

        /**
         * Clears the cooldown for a player
         * @param player The player's UUID
         */
        public void clearCooldown(UUID player) {
            cooldowns.remove(player);
        }
        public void clearCooldown(Player player) {
            cooldowns.remove(player.getUniqueId());
        }
    }
}
