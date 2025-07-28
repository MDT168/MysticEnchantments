package com.mdt168.mysticEnchantments.modifiers;

import com.mdt168.mysticEnchantments.custom.PlayerDataUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class ModifierKeys {
    public static final NamespacedKey OVERCHARGE_MODIFIER = new NamespacedKey(JavaPlugin.getProvidingPlugin(PlayerDataUtils.class), "overcharge_modifier");
}
