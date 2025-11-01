package com.mdt168.mysticEnchantments.craft.custom;

import org.bukkit.event.player.PlayerInteractEvent;

@FunctionalInterface
public interface OnInteract {
    void run(PlayerInteractEvent event);
}