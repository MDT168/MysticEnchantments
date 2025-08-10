package com.mdt168.mysticEnchantments.enchants;

import com.mdt168.mysticEnchantments.MysticEnchantments;
import com.mdt168.mysticEnchantments.custom.BookTiers;
import com.mdt168.mysticEnchantments.custom.Gradient;
import com.mdt168.mysticEnchantments.custom.Helper;
import com.mdt168.mysticEnchantments.custom.dataUtils.EnchantmentsUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class MysticTicket {
    private final NamespacedKey key;
    private final List<MysticEnchantmentComponent> enchantments;
    private static final Plugin plugin = MysticEnchantments.getInstance();
    public MysticTicket(BookTiers tier) {
        enchantments = EnchantmentsUtils.getAllEnchantments(tier);
        this.key = new NamespacedKey(plugin, "mystic_enchantments_tickets_" + tier.getName());
    }
    public int getTickets(Player player) {
        return player.getPersistentDataContainer().getOrDefault(key, PersistentDataType.INTEGER, 0);
    }
    public void setTickets(Player player, int tickets) {
        player.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, Math.max(0, tickets));
    }
    public boolean hasTickets(Player player, int tickets) {
        return getTickets(player) >= tickets;
    }
    public void removeTickets(Player player, int amount) {
        setTickets(player, getTickets(player) - amount);
    }
    public void addTickets(Player player, int amount) {
        setTickets(player, getTickets(player) + amount);
    }
    public void rollTicket(Player player) {
        if (!hasTickets(player, 1)) {
            Helper.sendWarningMessage(player, "You don't have enough tickets!");
            player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1, 1);
            return;
        }
        if (!Helper.hasEmptySlot(player)) {
            Helper.sendWarningMessage(player, "No Inventory Space");
            player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1, 1);
            return;
        }
        MysticEnchantmentComponent rolled = Helper.rollFromList(enchantments);
        if (rolled instanceof HumaneEnchantment enchantment) {
            if (Helper.giveBook(enchantment, player)) {
                removeTickets(player, 1);
                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                Helper.sendMessage(player, "You rolled " + Gradient.YELLOW + rolled.getName());
            }
        } else if (rolled instanceof EnchantmentStack enchantment) {
            if (Helper.giveBook(enchantment, player)) {
                removeTickets(player, 1);
                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                Helper.sendMessage(player, "You rolled " + Gradient.YELLOW + rolled.getName());
            }
        }
    }
    public boolean rollTicket(Player player, boolean noNotification) {
        if (!hasTickets(player, 1)) {
            if (!noNotification) {
                Helper.sendWarningMessage(player, "You don't have enough tickets!");
                player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1, 1);
            }
            return false;
        }
        if (!Helper.hasEmptySlot(player)) {
            if (!noNotification) {
                Helper.sendWarningMessage(player, "No Inventory Space");
                player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1, 1);
            }
            return false;
        }
        MysticEnchantmentComponent rolled = Helper.rollFromList(enchantments);
        if (rolled instanceof HumaneEnchantment enchantment) {
            if (Helper.giveBook(enchantment, player)) {
                removeTickets(player, 1);
                if (!noNotification) {
                    player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                    Helper.sendMessage(player, "You rolled " + Gradient.YELLOW + rolled.getName());
                }
            }
        } else if (rolled instanceof EnchantmentStack enchantment) {
            if (Helper.giveBook(enchantment, player)) {
                removeTickets(player, 1);
                if (!noNotification) {
                    player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                    Helper.sendMessage(player, "You rolled " + Gradient.YELLOW + rolled.getName());
                }
            }
        }
        return true;
    }
}