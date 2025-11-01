package com.mdt168.mysticEnchantments.custom.pluginoptions;

import com.mdt168.mysticEnchantments.config.ConfigSettings;
import com.mdt168.mysticEnchantments.custom.Helper;
import com.mdt168.mysticEnchantments.custom.PlayerDataUtils;
import com.mdt168.mysticEnchantments.enchants.HumaneEnchantment;
import com.mdt168.mysticEnchantments.enchants.MysticTickets;
import com.mdt168.mysticEnchantments.utility.buffs.MysticBuffs;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@SuppressWarnings("all")
public class MysticOptions {
    public static final MysticOption PURITY_CRYSTAL = MysticOption.register(
            "Purity Crystal",
            "Makes The Soul Clean From Humane Enchantments",
            ConfigSettings.PURITY_CRYSTAL_PRICE,
            player -> {
                List<HumaneEnchantment> enchantments = PlayerDataUtils.getEnchantmentsOnPlayer(player);
                if (enchantments.isEmpty()) {
                    Helper.sendWarningMessage(player, "You don't have active Humane Enchantments");
                    return false;
                }
                int removed = 0;
                for (HumaneEnchantment enchantment : enchantments) {
                    if (Helper.giveBook(enchantment, player, true)) {
                        removed++;
                        PlayerDataUtils.removeData(player, enchantment.getId());
                    } else {
                        Helper.sendWarningMessage(player, removed == 0 ? "No Humane enchantments got remove due to full inventory" : "Some Humane Enchantments didn't get removed due to full inventory");
                        break;
                    }
                }
                return removed != 0;
            },
            true
    );
    public static final MysticOption ANVIL_GUI = MysticOption.register(
            "Anvil",
            "Easier way to get an anvil for enchantments",
            ConfigSettings.ANVIL_PRICE,
            Material.ANVIL,
            player -> Helper.safeGiveItem(player, new ItemStack(Material.ANVIL)),
            true
    );

    public static final MysticOption BASIC_TICKETS = MysticOption.register(
            "Basic Enchantments Ticket",
            "A Ticket to give a random Basic Tier Enchantment",
            ConfigSettings.BASIC_ENCHANTMENTS_PRICE,
            Material.PAPER,
            player -> {
                MysticTickets.BASIC_TICKETS.addTickets(player, 1);
                return true;
            },
            true
    );
    public static final MysticOption ENHANCED_TICKETS = MysticOption.register(
            "Enhanced Enchantments Ticket",
            "A Ticket to give a random Basic Tier Enchantment",
            ConfigSettings.ENHANCED_ENCHANTMENTS_PRICE,
            Material.PAPER,
            player -> {
                MysticTickets.ENHANCED_TICKETS.addTickets(player, 1);
                return true;
            },
            true
    );
    public static final MysticOption REFINED_TICKETS = MysticOption.register(
            "Refined Enchantments Ticket",
            "A Ticket to give a random Basic Tier Enchantment",
            ConfigSettings.REFINED_ENCHANTMENTS_PRICE,
            Material.PAPER,
            player -> {
                MysticTickets.REFINED_TICKETS.addTickets(player, 1);
                return true;
            },
            true
    );
    public static final MysticOption ELITE_TICKETS = MysticOption.register(
            "Elite Enchantments Ticket",
            "A Ticket to give a random Basic Tier Enchantment",
            ConfigSettings.ELITE_ENCHANTMENTS_PRICE,
            Material.PAPER,
            player -> {
                MysticTickets.ELITE_TICKETS.addTickets(player, 1);
                return true;
            },
            true
    );
    public static final MysticOption MYTHIC_TICKETS = MysticOption.register(
            "Mythic Enchantments Ticket",
            "A Ticket to give a random Basic Tier Enchantment",
            ConfigSettings.MYTHIC_ENCHANTMENTS_PRICE,
            Material.PAPER,
            player -> {
                MysticTickets.MYTHIC_TICKETS.addTickets(player, 1);
                return true;
            },
            true
    );
    public static final MysticOption MYSTIC_INSIGHT_PERK = MysticOption.register(
            "Mystic Insight Perk",
            "Be able to claim the worth of Mystic Resources",
            ConfigSettings.MYSTIC_INSIGHT_PERK_PRICE,
            Material.POTION,
            player -> {
                if (MysticBuffs.MYSTIC_INSIGHT.hasBuff(player)) {
                    Helper.sendWarningMessage(player, "You already have this perk!");
                    return false;
                }
                MysticBuffs.MYSTIC_INSIGHT.giveBuff(player);
                return true;
            },
            true
    );
    public static void init() {}
}
