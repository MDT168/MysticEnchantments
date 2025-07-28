package com.mdt168.mysticEnchantments.settings;

import com.mdt168.mysticEnchantments.craft.MysticItems;
import org.bukkit.Material;

public class Settings {
    public static final Setting BOSS_BAR_SHOW = new Setting("Show Mystic Boss Bar", "Show a boss bar on Mystic Efforts Obtainment", MysticItems.MYSTIC_LEVEL_ITEM);
    public static final Setting RECEIVE_WORLD_MESSAGES = new Setting("Receive Mystic World Messages", "Whether to receive world messages from Mystic Enchantments or not", Material.OAK_SIGN);
    public static final Setting RECEIVE_MESSAGES = new Setting("Receive Mystic Messages", "Whether to receive messages from Mystic Enchantments or not", Material.BIRCH_SIGN);
    public static final Setting RECEIVE_WARNING_MESSAGES = new Setting("Receive Mystic Warning Messages", "Whether to receive warning messages from Mystic Enchantments or not", Material.CRIMSON_SIGN);
    public static final Setting RECEIVE_ACTIONBAR = new Setting(
            "Receive Mystic Actionbar Messages",
            "Whether to receive messages from Mystic Enchantments in your actionbar or not",
            Material.BAMBOO_SIGN
    );
}
