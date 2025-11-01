package com.mdt168.mysticEnchantments.custom.dataUtils;

import com.mdt168.mysticEnchantments.craft.builders.ItemStackBuilder;
import com.mdt168.mysticEnchantments.custom.*;
import com.mdt168.mysticEnchantments.enchants.HumaneEnchantment;
import com.mdt168.mysticEnchantments.enchants.MysticTickets;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Guis {
    private static final Inventory main = GuiHelper.getMainGui();

    private static final MiniMessage mm = MiniMessage.miniMessage();
    private static final ItemStackBuilder EFFORTS = ItemStackBuilder.of(Material.EMERALD)
            .setDisplayName("<gradient:#BA55D3:#FF6EC7><bold><!i>✧ Mystic Effort Points ✧</bold></gradient>");
    private static void updateEfforts(Inventory inventory, Player player) {
        inventory.setItem(13, EFFORTS.setLore(List.of(
                mm.deserialize("<gray><!i>• Get Mystic Effort Points by"),
                mm.deserialize("<gradient:#FFACFC:#FF77A9><!i>  Damaging and Breaking Blocks (Hardness > 1)</gradient>"),
                Component.empty(),

                mm.deserialize("<!i>" + Gradient.GREEN + "• Progress: " +
                        MysticCoinHandler.getEffortPoints(player) + "/" +
                        MysticCoinHandler.getNeededEfforts()),
                Component.empty(),
                mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>"),
                mm.deserialize("<!i>" + Gradient.PINK + "• Level: " + MysticCoinHandler.getLevel(player)),
                mm.deserialize("<!i>" + Gradient.AQUA + "• Booster: " + MysticCoinHandler.getBooster(player) + "%"),
                mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>"),
                Component.empty(),
                mm.deserialize("<!i>" + Gradient.AQUA + "• Earn " +
                        MysticCoinHandler.getMinMysticCoinsReward(player) + "-" +
                        MysticCoinHandler.getMaxMysticCoinsReward(player) + " Mystic Coins"),
                mm.deserialize("<!i>" + Gradient.YELLOW + "when you reach the goal!")
        )).build());
    }
    public static Inventory getMainGui(Player player) {
        String name = "<!i><light_purple>Mystic Enchanter";
        Inventory inventory = Helper.cloneInventory(main, name);

        ItemStack basic = inventory.getItem(20);
        ItemStack enhanced = inventory.getItem(21);
        ItemStack refined = inventory.getItem(22);
        ItemStack elite = inventory.getItem(23);
        ItemStack mythic = inventory.getItem(24);

        updateEfforts(inventory, player);

        if (basic != null)
            basic.editMeta(meta -> {
                List<Component> lore = meta.lore();
                lore = lore == null ? new ArrayList<>() : lore;
                lore.add(GuiHelper.mm.deserialize(Gradient.GREEN + "<!i>You have <yellow>" + MysticTickets.BASIC_TICKETS.getTickets(player) + "</yellow> Basic Tickets"));
                meta.lore(lore);
            });

        if (enhanced != null)
            enhanced.editMeta(meta -> {
                List<Component> lore = meta.lore();
                lore = lore == null ? new ArrayList<>() : lore;
                lore.add(GuiHelper.mm.deserialize(Gradient.GREEN + "<!i>You have <yellow>" + MysticTickets.ENHANCED_TICKETS.getTickets(player) + "</yellow> Enhanced Tickets"));
                meta.lore(lore);
            });

        if (refined != null)
            refined.editMeta(meta -> {
                List<Component> lore = meta.lore();
                lore = lore == null ? new ArrayList<>() : lore;
                lore.add(GuiHelper.mm.deserialize(Gradient.GREEN + "<!i>You have <yellow>" + MysticTickets.REFINED_TICKETS.getTickets(player) + "</yellow> Refined Tickets"));
                meta.lore(lore);
            });

        if (elite != null)
            elite.editMeta(meta -> {
                List<Component> lore = meta.lore();
                lore = lore == null ? new ArrayList<>() : lore;
                lore.add(GuiHelper.mm.deserialize(Gradient.GREEN + "<!i>You have <yellow>" + MysticTickets.ELITE_TICKETS.getTickets(player) + "</yellow> Elite Tickets"));
                meta.lore(lore);
            });

        if (mythic != null)
            mythic.editMeta(meta -> {
                List<Component> lore = meta.lore();
                lore = lore == null ? new ArrayList<>() : lore;
                lore.add(GuiHelper.mm.deserialize(Gradient.GREEN + "<!i>You have <yellow>" + MysticTickets.MYTHIC_TICKETS.getTickets(player) + "</yellow> Mythic Tickets"));
                meta.lore(lore);
            });

        Helper.updateCrate(player, inventory);

        inventory.setItem(inventory.getSize() - 5, GuiHelper.createViewer(player));
        return inventory;
    }

    public static Inventory getHumaneEnchantsGui(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 36, MiniMessage.miniMessage().deserialize(Gradient.PINK + "Active Humane Enchantments"));
        List<HumaneEnchantment> enchants = PlayerDataUtils.getEnchantmentsOnPlayer(player);
        List<ItemStack> books = new ArrayList<>();
        for (HumaneEnchantment enchantment : enchants) {
            books.add(Helper.createBook(enchantment));
        }
        for (ItemStack book : books) {
            inventory.addItem(book);
        }
        if (inventory.isEmpty()) {
            inventory.setItem(13, ItemStackBuilder.of(Material.BARRIER).setDisplayName("<red><!i>You don't have any active Humane Enchantments!").build());
        }
        inventory.setItem(inventory.getSize() - 5, GuiHelper.createViewer(player));
        inventory.setItem(inventory.getSize() - 1, GuiHelper.createBack());
        return inventory;
    }
}
