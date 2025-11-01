package com.mdt168.mysticEnchantments.custom;

import com.mdt168.mysticEnchantments.config.ConfigSettings;
import com.mdt168.mysticEnchantments.craft.builders.ItemStackBuilder;
import com.mdt168.mysticEnchantments.craft.recipes.utility.MysticRecipeUtils;
import com.mdt168.mysticEnchantments.custom.dataUtils.EnchantmentsUtils;
import com.mdt168.mysticEnchantments.enchants.EnchantmentStack;
import com.mdt168.mysticEnchantments.enchants.HumaneEnchantment;
import com.mdt168.mysticEnchantments.craft.recipes.MysticRecipe;
import com.mdt168.mysticEnchantments.settings.Setting;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiHelper {
    public static final MiniMessage mm = MiniMessage.miniMessage();
    private static final ItemStack BASIC_ENCHANTS = ItemStackBuilder.of(Material.BOOK)
            .setDisplayName(BookTiers.BASIC.getColor() + "Basic Enchantments")
            .setId("basic_tab")
            .setLore(List.of(
                    mm.deserialize("<dark_gray><!i>Tier I"),
                    Component.empty(),
                    mm.deserialize(Gradient.YELLOW + "<!i><!i>Left-Click To Inspect Enchantments"),
                    mm.deserialize(Gradient.YELLOW + "<!i><!i>Right-Click To Roll"),
                    Component.empty()
            ))
            .build();
    private static final ItemStack ENHANCED_ENCHANTS = ItemStackBuilder.of(Material.BOOK)
            .setDisplayName(BookTiers.ENHANCED.getColor() + "Enhanced Enchantments")
            .setId("enhanced_tab")
            .setLore(List.of(
                    mm.deserialize("<dark_gray><!i>Tier II"),
                    Component.empty(),
                    mm.deserialize(Gradient.YELLOW + "<!i>Left-Click To Inspect Enchantments"),
                    mm.deserialize(Gradient.YELLOW + "<!i>Right-Click To Roll"),
                    Component.empty()
            ))
            .build();
    private static final ItemStack REFINED_ENCHANTS = ItemStackBuilder.of(Material.BOOK)
            .setDisplayName(BookTiers.REFINED.getColor() + "Refined Enchantments")
            .setId("refined_tab")
            .setLore(List.of(
                    mm.deserialize("<dark_gray><!i>Tier III"),
                    Component.empty(),
                    mm.deserialize(Gradient.YELLOW + "<!i>Left-Click To Inspect Enchantments"),
                    mm.deserialize(Gradient.YELLOW + "<!i>Right-Click To Roll"),
                    Component.empty()
            ))
            .build();
    private static final ItemStack ELITE_ENCHANTS = ItemStackBuilder.of(Material.BOOK)
            .setDisplayName(BookTiers.ELITE.getColor() + "Elite Enchantments")
            .setId("elite_tab")
            .setLore(List.of(
                    mm.deserialize("<dark_gray><!i>Tier IV"),
                    Component.empty(),
                    mm.deserialize(Gradient.YELLOW + "<!i>Left-Click To Inspect Enchantments"),
                    mm.deserialize(Gradient.YELLOW + "<!i>Right-Click To Roll"),
                    Component.empty()
            ))
            .build();
    private static final ItemStack MYTHIC_ENCHANTS = ItemStackBuilder.of(Material.BOOK)
            .setDisplayName(BookTiers.MYTHIC.getColor() + "Mythic Enchantments")
            .setId("mythic_tab")
            .setLore(List.of(
                    mm.deserialize("<dark_gray><!i>Tier V"),
                    Component.empty(),
                    mm.deserialize(Gradient.YELLOW + "<!i>Left-Click To Inspect Enchantments"),
                    mm.deserialize(Gradient.YELLOW + "<!i>Right-Click To Roll"),
                    Component.empty()
            ))
            .build();
    public static List<EnchantmentStack> getBasicEnchantments() {
        return EnchantmentsUtils.getEnchantments(BookTiers.BASIC);
    }
    public static List<EnchantmentStack> getEnhancedEnchantments() {
        return EnchantmentsUtils.getEnchantments(BookTiers.ENHANCED);
    }
    public static List<EnchantmentStack> getRefinedEnchantments() {
        return EnchantmentsUtils.getEnchantments(BookTiers.REFINED);
    }
    public static List<EnchantmentStack> getEliteEnchantments() {
        return EnchantmentsUtils.getEnchantments(BookTiers.ELITE);
    }
    public static List<EnchantmentStack> getMythicEnchantments() {
        return EnchantmentsUtils.getEnchantments(BookTiers.MYTHIC);
    }
    public static List<HumaneEnchantment> getHumanBasicEnchantments() {
        return EnchantmentsUtils.getHumaneEnchantments(BookTiers.BASIC);
    }
    public static List<HumaneEnchantment> getHumanEnhancedEnchantments() {
        return EnchantmentsUtils.getHumaneEnchantments(BookTiers.ENHANCED);
    }
    public static List<HumaneEnchantment> getHumanRefinedEnchantments() {
        return EnchantmentsUtils.getHumaneEnchantments(BookTiers.REFINED);
    }
    public static List<HumaneEnchantment> getHumanEliteEnchantments() {
        return EnchantmentsUtils.getHumaneEnchantments(BookTiers.ELITE);
    }
    public static List<HumaneEnchantment> getHumanMythicEnchantments() {
        return EnchantmentsUtils.getHumaneEnchantments(BookTiers.MYTHIC);
    }
    public static List<HumaneEnchantment> getHumaneEnchantments() {
        return HumaneEnchantment.getRegistries();
    }

    public static Inventory getMainGui() {
        Inventory inventory = Bukkit.createInventory(null, 54);

        ItemStack exit = new ItemStack(Material.BARRIER);
        ItemStack pluginItems = new ItemStack(Material.BEACON);
        ItemStack recipesMenu = new ItemStack(Material.CRAFTING_TABLE);

        ItemMeta exitMeta = exit.getItemMeta();
        ItemMeta pluginItemsMeta = pluginItems.getItemMeta();
        ItemMeta recipesMenuMeta = recipesMenu.getItemMeta();

        pluginItemsMeta.displayName(MiniMessage.miniMessage().deserialize("<!i><bold><gold>Mystic Options"));
        pluginItemsMeta.lore(List.of(
                Component.empty(),
                MiniMessage.miniMessage().deserialize("<!i><gray>Mystic Enchantments Options"),
                MiniMessage.miniMessage().deserialize("<!i><green>Click To Open")
        ));
        pluginItems.setItemMeta(pluginItemsMeta);

        recipesMenu.setItemMeta(recipesMenuMeta);
        recipesMenuMeta.displayName(MiniMessage.miniMessage().deserialize("<!i>" + Gradient.YELLOW + "Mystic Recipes"));
        recipesMenuMeta.lore(List.of(
                Component.empty(),
                MiniMessage.miniMessage().deserialize("<!i><yellow>You can craft special Mystic Items Here"),
                MiniMessage.miniMessage().deserialize("<!i><green>Click to Open")
        ));
        recipesMenu.setItemMeta(recipesMenuMeta);

        exitMeta.displayName(MiniMessage.miniMessage().deserialize("<!i><gray>Exit"));
        exit.setItemMeta(exitMeta);

        ItemStack mysticResources = ItemStackBuilder.of(Material.COMPASS)
                        .setLore(List.of(mm.deserialize("<!i><white>Mystic Enchantments Custom Resources"),
                                Component.empty(),
                                mm.deserialize("<!i><yellow>Click To Open")))
                .setDisplayName(Gradient.YELLOW + "Mystic Resources Codex")
                .setId("mystic_resources_tab").build();

        ItemDataUtils.addData(exit, "exit");
        ItemDataUtils.addData(pluginItems, "options_tab");
        ItemDataUtils.addData(recipesMenu, "recipes_menu");

        if (ConfigSettings.BASIC_ENCHANTMENTS_ACCESS.getValue()) inventory.setItem(20, BASIC_ENCHANTS.clone());
        if (ConfigSettings.ENHANCED_ENCHANTMENTS_ACCESS.getValue()) inventory.setItem(21, ENHANCED_ENCHANTS.clone());
        if (ConfigSettings.REFINED_ENCHANTMENTS_ACCESS.getValue()) inventory.setItem(22, REFINED_ENCHANTS.clone());
        if (ConfigSettings.ELITE_ENCHANTMENTS_ACCESS.getValue()) inventory.setItem(23, ELITE_ENCHANTS.clone());
        if (ConfigSettings.MYTHIC_ENCHANTMENTS_ACCESS.getValue()) inventory.setItem(24, MYTHIC_ENCHANTS.clone());
        if (ConfigSettings.ENABLE_MYSTIC_RECIPES.getValue()) inventory.setItem(inventory.getSize() - 7, recipesMenu);
        if (ConfigSettings.ENABLE_MYSTIC_RESOURCES.getValue()) inventory.setItem(inventory.getSize() - 8, mysticResources);
        inventory.setItem(inventory.getSize() - 1, exit);
        inventory.setItem(inventory.getSize() - 9, pluginItems);

        return inventory;
    }
    public static List<MysticRecipe> getMysticRecipes() {
        return MysticRecipeUtils.getRecipes();
    }

    public static ItemStack createViewer(Player player) {
        ItemStack item = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(MiniMessage.miniMessage().deserialize("<!i><aqua>Info"));
        meta.lore(List.of(
                Component.empty(),
                MiniMessage.miniMessage().deserialize("<!i><bold>" + Gradient.YELLOW + "Mystic Coins: </bold>" + Gradient.AQUA + MysticCoinUtils.getCoin(player) + " Coins"),
                Component.empty()
                ));
        item.setItemMeta(meta);
        ItemDataUtils.addData(item, "viewer");
        return item;
    }
    public static ItemStack createBack() {
        ItemStack exit = new ItemStack(Material.BARRIER);
        ItemMeta exitMeta = exit.getItemMeta();
        exitMeta.displayName(MiniMessage.miniMessage().deserialize("<!i><gray>Back"));
        exit.setItemMeta(exitMeta);
        ItemDataUtils.addData(exit, "back");
        return exit;
    }

    public static Inventory getSettingsGui(Player player) {
        MiniMessage mm = MiniMessage.miniMessage();
        List<Setting> settings = Helper.getSettings();
        Inventory inventory = Bukkit.createInventory(null, 18, mm.deserialize(Gradient.BLUE + "Mystic Settings"));

        for (int i = 0; i < settings.size() && i < inventory.getSize(); i++) {
            Setting setting = settings.get(i);
            ItemStack toDisplay = setting.getToDisplay().clone();
            boolean value = setting.getValue(player);
            String color = value ? "<!i><green>True" : "<!i><red>False";
            toDisplay.editMeta(itemMeta -> {
               List<Component> lore = itemMeta.lore();
               lore = lore == null ? new ArrayList<>() : lore;
               lore.add(mm.deserialize(color));
               itemMeta.lore(lore);
            });
            inventory.setItem(i, toDisplay);
        }

        inventory.setItem(inventory.getSize() - 1, createExit());
        return inventory;
    }
    public static ItemStack createExit() {
        ItemStack barrier = new ItemStack(Material.BARRIER);
        barrier.editMeta(meta -> meta.displayName(MiniMessage.miniMessage().deserialize("<red><!i>Exit")));
        ItemDataUtils.setData(barrier, "settings_exit");
        return barrier;
    }
}
