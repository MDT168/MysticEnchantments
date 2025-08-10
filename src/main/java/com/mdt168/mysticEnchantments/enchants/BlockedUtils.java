package com.mdt168.mysticEnchantments.enchants;

import com.mdt168.mysticEnchantments.config.ConfigSettings;
import com.mdt168.mysticEnchantments.custom.PlayerDataUtils;

import java.util.List;

public class BlockedUtils {
    public static boolean isBlocked(EnchantmentStack enchantmentStack) {
        return ConfigSettings.BLOCKED_ENCHANTMENTS.getValue().contains(enchantmentStack.getId()) || ConfigSettings.BLOCKED_ENCHANTMENTS.getValue().contains(enchantmentStack.getName());
    }
    public static boolean isBlocked(HumaneEnchantment humaneEnchantment) {
        return ConfigSettings.BLOCKED_ENCHANTMENTS.getValue().contains(humaneEnchantment.getId()) || ConfigSettings.BLOCKED_ENCHANTMENTS.getValue().contains(humaneEnchantment.getName());
    }
}
