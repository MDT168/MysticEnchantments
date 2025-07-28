package com.mdt168.mysticEnchantments.custom.pluginoptions;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface OnPurchase {
    boolean run(Player player);
}
