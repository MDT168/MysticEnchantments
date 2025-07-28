package com.mdt168.mysticEnchantments.custom;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface PlayerAction {
    boolean run(Player player);
}