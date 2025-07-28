package com.mdt168.mysticEnchantments.custom;

import com.mdt168.mysticEnchantments.config.ConfigSettings;
import com.mdt168.mysticEnchantments.craft.recipes.utility.MysticRecipeUtils;
import com.mdt168.mysticEnchantments.custom.dataUtils.EnchantmentsUtils;
import com.mdt168.mysticEnchantments.custom.pluginoptions.MysticOption;
import com.mdt168.mysticEnchantments.enchants.BlockedUtils;
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
    public static List<MysticOption> getOptions() {
        return MysticOption.getOptions();
    }
    public static Inventory getItemsGui(Player player) {
        Inventory inventory = Bukkit.createInventory(
                null,
                27,
                MiniMessage.miniMessage().deserialize("<!i><light_purple>Mystic Enchanter - <reset><green>Items")
        );
        List<MysticOption> items = getOptions();
        for (int i = 0; i < items.size() && i < inventory.getSize(); i++) {
            inventory.setItem(i, items.get(i).getItemStack());
        }
        inventory.setItem(26, createBack());
        inventory.setItem(22, createViewer(player));
        return inventory;
    }
    public static Inventory getItemsGui() {
        Inventory inventory = Bukkit.createInventory(
                null,
                27
        );
        List<MysticOption> items = getOptions();
        for (int i = 0; i < items.size() && i < inventory.getSize(); i++) {
            inventory.setItem(i, items.get(i).getItemStack());
        }
        inventory.setItem(26, createBack());
        ItemStack humaneViewer = new ItemStack(Material.BLACK_BANNER);
        MiniMessage mm = MiniMessage.miniMessage();
        humaneViewer.editMeta(meta -> {
            meta.displayName(mm.deserialize(Gradient.BLUE + "<!i>View Humane Enchantments"));
            meta.lore(List.of(
                    mm.deserialize("<!i><yellow>- View the humane enchantments that you are enchanted with"),
                    mm.deserialize("<!i><green>- Click to view")
            ));
        });
        ItemDataUtils.setData(humaneViewer, "humane_viewer");
        inventory.setItem(inventory.getSize() - 9, humaneViewer);

        return inventory;
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

    public static Inventory getBasicGui(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 45);
        List<EnchantmentStack> enchantments = getBasicEnchantments();
        List<HumaneEnchantment> humaneEnchantments = getHumanBasicEnchantments();
        ItemStack glassPane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = glassPane.getItemMeta();
        meta.setHideTooltip(true);
        glassPane.setItemMeta(meta);
        int i = 0;
        while (i < inventory.getSize() && i < enchantments.size()) {
            inventory.setItem(i, Helper.createBook(enchantments.get(i)));
            i++;
        }
        for (int j = 0; i < inventory.getSize() && j < humaneEnchantments.size(); j++) {
            inventory.setItem(i, Helper.createBook(humaneEnchantments.get(j)));
            i++;
        }
        for (int k = inventory.getSize() - 18; k < inventory.getSize() - 9; k++) inventory.setItem(k, glassPane);
        inventory.setItem(inventory.getSize() - 1, createBack());
        inventory.setItem(inventory.getSize() - 5, createViewer(player));
        return inventory;
    }
    public static Inventory getBasicGui() {
        Inventory inventory = Bukkit.createInventory(null, 45);
        List<EnchantmentStack> enchantments = getBasicEnchantments();
        List<HumaneEnchantment> humaneEnchantments = getHumanBasicEnchantments();
        ItemStack glassPane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = glassPane.getItemMeta();
        meta.setHideTooltip(true);
        glassPane.setItemMeta(meta);
        int i = 0;
        while (i < inventory.getSize() && i < enchantments.size()) {
            inventory.setItem(i, Helper.createBook(enchantments.get(i)));
            i++;
        }
        for (int j = 0; i < inventory.getSize() && j < humaneEnchantments.size(); j++) {
            inventory.setItem(i, Helper.createBook(humaneEnchantments.get(j)));
            i++;
        }
        for (int k = inventory.getSize() - 18; k < inventory.getSize() - 9; k++) inventory.setItem(k, glassPane);
        inventory.setItem(inventory.getSize() - 1, createBack());
        return inventory;
    }

    public static Inventory getFormat(Material material) {
        Inventory inventory = Bukkit.createInventory(null, 54);
        ItemStack glassPane = new ItemStack(material);
        ItemMeta meta = glassPane.getItemMeta();
        meta.setHideTooltip(true);
        glassPane.setItemMeta(meta);
        int i = 0;
        for (int k = inventory.getSize() - 18; k < inventory.getSize() - 9; k++) inventory.setItem(k, glassPane);
        inventory.setItem(inventory.getSize() - 1, createBack());
        return inventory;
    }

    public static Inventory getEnhancedGui(Player player) {
        String name = "<!i><light_purple>Mystic Enchanter - <green>Enhanced";
        Inventory inventory = Bukkit.createInventory(null, 45, MiniMessage.miniMessage().deserialize(name));
        List<EnchantmentStack> enchantments = getEnhancedEnchantments();
        List<HumaneEnchantment> humaneEnchantments = getHumanEnhancedEnchantments();
        int i = 0;
        ItemStack glassPane = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta meta = glassPane.getItemMeta();
        meta.setHideTooltip(true);
        glassPane.setItemMeta(meta);
        while (i < inventory.getSize() && i < enchantments.size()) {
            inventory.setItem(i, Helper.createBook(enchantments.get(i)));
            i++;
        }
        for (int j = 0; i < inventory.getSize() && j < humaneEnchantments.size(); j++) {
            inventory.setItem(i, Helper.createBook(humaneEnchantments.get(j)));
            i++;
        }
        for (int k = inventory.getSize() - 18; k < inventory.getSize() - 9; k++) inventory.setItem(k, glassPane);
        inventory.setItem(inventory.getSize() - 1, createBack());
        inventory.setItem(inventory.getSize() - 5, createViewer(player));
        return inventory;
    }
    public static Inventory getEnhancedGui() {
        Inventory inventory = Bukkit.createInventory(null, 45);
        List<EnchantmentStack> enchantments = getEnhancedEnchantments();
        List<HumaneEnchantment> humaneEnchantments = getHumanEnhancedEnchantments();
        int i = 0;
        ItemStack glassPane = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta meta = glassPane.getItemMeta();
        meta.setHideTooltip(true);
        glassPane.setItemMeta(meta);
        while (i < inventory.getSize() && i < enchantments.size()) {
            if (BlockedUtils.isBlocked(enchantments.get(i))) continue;
            inventory.setItem(i, Helper.createBook(enchantments.get(i)));
            i++;
        }
        for (int j = 0; i < inventory.getSize() && j < humaneEnchantments.size(); j++) {
            if (BlockedUtils.isBlocked(humaneEnchantments.get(j))) continue;
            inventory.setItem(i, Helper.createBook(humaneEnchantments.get(j)));
            i++;
        }
        for (int k = inventory.getSize() - 18; k < inventory.getSize() - 9; k++) inventory.setItem(k, glassPane);
        inventory.setItem(inventory.getSize() - 1, createBack());

        return inventory;
    }
    public static Inventory getRefinedGui(Player player) {
        String name = "<!i><light_purple>Mystic Enchanter - <green>Refined";
        Inventory inventory = Bukkit.createInventory(null, 45, MiniMessage.miniMessage().deserialize(name));
        List<EnchantmentStack> enchantments = getRefinedEnchantments();
        List<HumaneEnchantment> humaneEnchantments = getHumanRefinedEnchantments();
        ItemStack glassPane = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        ItemMeta meta = glassPane.getItemMeta();
        meta.setHideTooltip(true);
        glassPane.setItemMeta(meta);
        int i = 0;
        while (i < inventory.getSize() && i < enchantments.size()) {
            inventory.setItem(i, Helper.createBook(enchantments.get(i)));
            i++;
        }
        for (int j = 0; i < inventory.getSize() && j < humaneEnchantments.size(); j++) {
            inventory.setItem(i, Helper.createBook(humaneEnchantments.get(j)));
            i++;
        }
        for (int k = inventory.getSize() - 18; k < inventory.getSize() - 9; k++) inventory.setItem(k, glassPane);
        inventory.setItem(inventory.getSize() - 1, createBack());
        inventory.setItem(inventory.getSize() - 5, createViewer(player));
        return inventory;
    }
    public static Inventory getRefinedGui() {
        Inventory inventory = Bukkit.createInventory(null, 45);
        List<EnchantmentStack> enchantments = getRefinedEnchantments();
        List<HumaneEnchantment> humaneEnchantments = getHumanRefinedEnchantments();
        ItemStack glassPane = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        ItemMeta meta = glassPane.getItemMeta();
        meta.setHideTooltip(true);
        glassPane.setItemMeta(meta);
        int i = 0;
        while (i < inventory.getSize() && i < enchantments.size()) {
            if (BlockedUtils.isBlocked(enchantments.get(i))) continue;
            inventory.setItem(i, Helper.createBook(enchantments.get(i)));
            i++;
        }
        for (int j = 0; i < inventory.getSize() && j < humaneEnchantments.size(); j++) {
            if (BlockedUtils.isBlocked(humaneEnchantments.get(j))) continue;
            inventory.setItem(i, Helper.createBook(humaneEnchantments.get(j)));
            i++;
        }
        for (int k = inventory.getSize() - 18; k < inventory.getSize() - 9; k++) inventory.setItem(k, glassPane);
        inventory.setItem(inventory.getSize() - 1, createBack());
        return inventory;
    }
    public static Inventory getEliteGui(Player player) {
        String name = "<!i><light_purple>Mystic Enchanter - <green>Elite";
        Inventory inventory = Bukkit.createInventory(null, 45, MiniMessage.miniMessage().deserialize(name));
        List<EnchantmentStack> enchantments = getEliteEnchantments();
        List<HumaneEnchantment> humaneEnchantments = getHumanEliteEnchantments();

        int i = 0;
        while (i < inventory.getSize() && i < enchantments.size()) {
            inventory.setItem(i, Helper.createBook(enchantments.get(i)));
            i++;
        }
        for (int j = 0; i < inventory.getSize() && j < humaneEnchantments.size(); j++) {
            inventory.setItem(i, Helper.createBook(humaneEnchantments.get(j)));
            i++;
        }

        ItemStack blackGlassPane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta blackMeta = blackGlassPane.getItemMeta();
        blackMeta.setHideTooltip(true);
        blackGlassPane.setItemMeta(blackMeta);
        while (i < inventory.getSize() && i < 27) {
            inventory.setItem(i, blackGlassPane);
            i++;
        }
        ItemStack glassPane = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
        ItemMeta meta = glassPane.getItemMeta();
        meta.setHideTooltip(true);
        glassPane.setItemMeta(meta);
        for (int k = inventory.getSize() - 18; k < inventory.getSize() - 9; k++) inventory.setItem(k, glassPane);
        inventory.setItem(inventory.getSize() - 1, createBack());
        inventory.setItem(inventory.getSize() - 5, createViewer(player));
        return inventory;
    }
    public static Inventory getEliteGui() {
        Inventory inventory = Bukkit.createInventory(null, 45);
        List<EnchantmentStack> enchantments = getEliteEnchantments();
        List<HumaneEnchantment> humaneEnchantments = getHumanEliteEnchantments();

        int i = 0;
        while (i < inventory.getSize() && i < enchantments.size()) {
            if (BlockedUtils.isBlocked(enchantments.get(i))) continue;
            inventory.setItem(i, Helper.createBook(enchantments.get(i)));
            i++;
        }
        for (int j = 0; i < inventory.getSize() && j < humaneEnchantments.size(); j++) {
            if (BlockedUtils.isBlocked(humaneEnchantments.get(j))) continue;
            inventory.setItem(i, Helper.createBook(humaneEnchantments.get(j)));
            i++;
        }

        ItemStack blackGlassPane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta blackMeta = blackGlassPane.getItemMeta();
        blackMeta.setHideTooltip(true);
        blackGlassPane.setItemMeta(blackMeta);
        while (i < inventory.getSize() && i < 27) {
            inventory.setItem(i, blackGlassPane);
            i++;
        }
        ItemStack glassPane = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
        ItemMeta meta = glassPane.getItemMeta();
        meta.setHideTooltip(true);
        glassPane.setItemMeta(meta);
        for (int k = inventory.getSize() - 18; k < inventory.getSize() - 9; k++) inventory.setItem(k, glassPane);
        inventory.setItem(inventory.getSize() - 1, createBack());
        return inventory;
    }
    public static Inventory getMythicGui(Player player) {
        String name = "<!i><light_purple>Mystic Enchanter - <green>Mythic";
        Inventory inventory = Bukkit.createInventory(null, 45, MiniMessage.miniMessage().deserialize(name));
        List<EnchantmentStack> enchantments = getMythicEnchantments();
        List<HumaneEnchantment> humaneEnchantments = getHumanMythicEnchantments();
        int i = 0;
        while (i < inventory.getSize() && i < enchantments.size()) {
            inventory.setItem(i, Helper.createBook(enchantments.get(i)));
            i++;
        }
        for (int j = 0; i < inventory.getSize() && j < humaneEnchantments.size(); j++) {
            inventory.setItem(i, Helper.createBook(humaneEnchantments.get(j)));
            i++;
        }
        ItemStack blackGlassPane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta blackMeta = blackGlassPane.getItemMeta();
        blackMeta.setHideTooltip(true);
        blackGlassPane.setItemMeta(blackMeta);
        while (i < inventory.getSize() && i < 27) {
            inventory.setItem(i, blackGlassPane);
            i++;
        }
        ItemStack glassPane = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta meta = glassPane.getItemMeta();
        meta.setHideTooltip(true);
        glassPane.setItemMeta(meta);
        for (int k = inventory.getSize() - 18; k < inventory.getSize() - 9; k++) inventory.setItem(k, glassPane);
        inventory.setItem(inventory.getSize() - 1, createBack());
        inventory.setItem(inventory.getSize() - 5, createViewer(player));
        return inventory;
    }
    public static Inventory getMythicGui() {
        Inventory inventory = Bukkit.createInventory(null, 45);
        List<EnchantmentStack> enchantments = getMythicEnchantments();
        List<HumaneEnchantment> humaneEnchantments = getHumanMythicEnchantments();
        int i = 0;
        while (i < inventory.getSize() && i < enchantments.size()) {
            if (BlockedUtils.isBlocked(enchantments.get(i))) continue;
            inventory.setItem(i, Helper.createBook(enchantments.get(i)));
            i++;
        }
        for (int j = 0; i < inventory.getSize() && j < humaneEnchantments.size(); j++) {
            if (BlockedUtils.isBlocked(humaneEnchantments.get(j))) continue;
            inventory.setItem(i, Helper.createBook(humaneEnchantments.get(j)));
            i++;
        }
        ItemStack blackGlassPane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta blackMeta = blackGlassPane.getItemMeta();
        blackMeta.setHideTooltip(true);
        blackGlassPane.setItemMeta(blackMeta);
        while (i < inventory.getSize() && i < 27) {
            inventory.setItem(i, blackGlassPane);
            i++;
        }
        ItemStack glassPane = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta meta = glassPane.getItemMeta();
        meta.setHideTooltip(true);
        glassPane.setItemMeta(meta);
        for (int k = inventory.getSize() - 18; k < inventory.getSize() - 9; k++) inventory.setItem(k, glassPane);
        inventory.setItem(inventory.getSize() - 1, createBack());
        return inventory;
    }

    public static Inventory getMainGui(Player player) {
        String name = "<!i><light_purple>Mystic Enchanter";
        Inventory inventory = Bukkit.createInventory(null, 54, MiniMessage.miniMessage().deserialize(name));

        ItemStack exit = new ItemStack(Material.BARRIER);
        ItemStack resourcesMenu = new ItemStack(Material.AMETHYST_BLOCK);
        ItemStack basicEnchants = new ItemStack(Material.BOOK);
        ItemStack enhancedEnchants = new ItemStack(Material.BOOK);
        ItemStack refinedEnchants = new ItemStack(Material.BOOK);
        ItemStack eliteEnchants = new ItemStack(Material.BOOK);
        ItemStack mythicEnchants = new ItemStack(Material.BOOK);
        ItemStack pluginItems = new ItemStack(Material.BEACON);
        ItemStack effortsMenu = new ItemStack(Material.EMERALD);
        ItemStack enchantedCrate = new ItemStack(Material.PINK_SHULKER_BOX);
        ItemStack recipesMenu = new ItemStack(Material.CRAFTING_TABLE);

        ItemMeta exitMeta = exit.getItemMeta();
        ItemMeta resourcesTabMeta = resourcesMenu.getItemMeta();
        ItemMeta basicEnchantsMeta = basicEnchants.getItemMeta();
        ItemMeta enhancedEnchantsMeta = enhancedEnchants.getItemMeta();
        ItemMeta refinedEnchantsMeta = refinedEnchants.getItemMeta();
        ItemMeta eliteEnchantsMeta = eliteEnchants.getItemMeta();
        ItemMeta mythicEnchantsMeta = mythicEnchants.getItemMeta();
        ItemMeta pluginItemsMeta = pluginItems.getItemMeta();
        ItemMeta enchantedCrateMeta = enchantedCrate.getItemMeta();
        ItemMeta effortsMenuMeta = effortsMenu.getItemMeta();
        ItemMeta recipesMenuMeta = recipesMenu.getItemMeta();

        basicEnchantsMeta.displayName(MiniMessage.miniMessage().deserialize("<reset><!i>Basic Enchantments"));
        basicEnchantsMeta.lore(List.of(
                Component.empty(),
                MiniMessage.miniMessage().deserialize("<!i><yellow>Left Click To Inspect"),
                MiniMessage.miniMessage().deserialize("<!i><green>Right Click To Roll"),
                Component.empty(),
                MiniMessage.miniMessage().deserialize("<!i>" + Gradient.RED + BookTiers.BASIC.getPrice() + " Mystic Coins")
        ));
        basicEnchants.setItemMeta(basicEnchantsMeta);

        resourcesTabMeta.displayName(MiniMessage.miniMessage().deserialize("<!i><gold>Mystic Resources Menu"));
        resourcesTabMeta.lore(List.of(
                Component.empty(),
                MiniMessage.miniMessage().deserialize("<!i><dark_purple>Opens the Resources Menu")
        ));
        resourcesMenu.setItemMeta(resourcesTabMeta);

        enhancedEnchantsMeta.displayName(MiniMessage.miniMessage().deserialize("<!i><green>Enhanced Enchantments"));
        enhancedEnchantsMeta.lore(List.of(
                Component.empty(),
                MiniMessage.miniMessage().deserialize("<!i><yellow>Left Click To Inspect"),
                MiniMessage.miniMessage().deserialize("<!i><green>Right Click To Roll"),
                Component.empty(),
                MiniMessage.miniMessage().deserialize("<!i>" + Gradient.RED + BookTiers.ENHANCED.getPrice() + " Mystic Coins")
        ));
        enhancedEnchants.setItemMeta(enhancedEnchantsMeta);

        refinedEnchantsMeta.displayName(MiniMessage.miniMessage().deserialize("<!i><aqua>Refined Enchantments"));
        refinedEnchantsMeta.lore(List.of(
                Component.empty(),
                MiniMessage.miniMessage().deserialize("<!i><yellow>Left Click To Inspect"),
                MiniMessage.miniMessage().deserialize("<!i><green>Right Click To Roll"),
                Component.empty(),
                MiniMessage.miniMessage().deserialize("<!i>" + Gradient.RED + BookTiers.REFINED.getPrice() + " Mystic Coins")
        ));
        refinedEnchants.setItemMeta(refinedEnchantsMeta);

        eliteEnchantsMeta.displayName(MiniMessage.miniMessage().deserialize("<!i><#FF55FF>Elite Enchantments"));
        eliteEnchantsMeta.lore(List.of(
                Component.empty(),
                MiniMessage.miniMessage().deserialize("<!i><yellow>Left Click To Inspect"),
                MiniMessage.miniMessage().deserialize("<!i><green>Right Click To Roll"),
                Component.empty(),
                MiniMessage.miniMessage().deserialize("<!i>" + Gradient.RED + BookTiers.ELITE.getPrice() + " Mystic Coins")
        ));
        eliteEnchants.setItemMeta(eliteEnchantsMeta);

        MiniMessage mm = MiniMessage.miniMessage();
        enchantedCrateMeta.displayName(mm.deserialize(
                "<gradient:#D08CFF:#FF55FF><!i>✧ Enchanted Crate ✧</gradient>"
        ));
        enchantedCrateMeta.lore(List.of(
                mm.deserialize("<#B388FF><!i>Mystic Enchantments Crate</#B388FF>"),
                Component.empty(),
                mm.deserialize("<#FFACFC><!i>⟡ Left Click</#FFACFC> <#FFFFFF><!i>to inspect crate contents</#FFFFFF>"),
                mm.deserialize("<#FFACFC><!i>⟡ Right Click</#FFACFC> <#FFFFFF><!i>to open with a key</#FFFFFF>"),
                Component.empty(),
                mm.deserialize("<#80D8FF><!i>You have <#FFEE58>" + EnchantedCrateUtils.getKey(player) + "</#FFEE58><!i> Enchanted Crate Keys</#80D8FF>")
        ));
        enchantedCrate.setItemMeta(enchantedCrateMeta);

        mythicEnchantsMeta.displayName(MiniMessage.miniMessage().deserialize("<!i><#FF3030>Mythic Enchantments"));
        mythicEnchantsMeta.lore(List.of(
                Component.empty(),
                MiniMessage.miniMessage().deserialize("<!i><#FFD700>Left Click To Inspect"),
                MiniMessage.miniMessage().deserialize("<!i><#00FF7F>Right Click To Roll"),
                Component.empty(),
                MiniMessage.miniMessage().deserialize("<!i>" + Gradient.RED + BookTiers.MYTHIC.getPrice() + " Mystic Coins")
        ));
        mythicEnchants.setItemMeta(mythicEnchantsMeta);

        effortsMenuMeta.displayName(MiniMessage.miniMessage().deserialize("<!i>" + Gradient.BLUE + "Mystic Coins Giver"));
        effortsMenuMeta.lore(List.of(
                Component.empty(),
                MiniMessage.miniMessage().deserialize("<!i>" + Gradient.YELLOW + "♦ Mystic Coins Menu"),
                MiniMessage.miniMessage().deserialize("<!i>" + Gradient.YELLOW + "♦ Get Mystic Coins With Effort Points")
        ));
        effortsMenu.setItemMeta(effortsMenuMeta);

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

        resourcesTabMeta.displayName(MiniMessage.miniMessage().deserialize("<!i><bold><gold>Mystic Resources"));
        resourcesTabMeta.lore(List.of(
                Component.empty(),
                MiniMessage.miniMessage().deserialize("<!i><gray>Mystic Enchantments Resources"),
                MiniMessage.miniMessage().deserialize("<!i><green>Click To Open")
        ));
        resourcesMenu.setItemMeta(resourcesTabMeta);

        exitMeta.displayName(MiniMessage.miniMessage().deserialize("<!i><gray>Exit"));
        exit.setItemMeta(exitMeta);

        ItemDataUtils.addData(exit, "exit");
        ItemDataUtils.addData(basicEnchants, "basic_tab");
        ItemDataUtils.addData(enhancedEnchants, "enhanced_tab");
        ItemDataUtils.addData(refinedEnchants, "refined_tab");
        ItemDataUtils.addData(eliteEnchants, "elite_tab");
        ItemDataUtils.addData(mythicEnchants, "mythic_tab");
        ItemDataUtils.addData(pluginItems, "options_tab");
        ItemDataUtils.addData(resourcesMenu, "resources_tab");
        ItemDataUtils.addData(enchantedCrate, "enchanted_crate");
        ItemDataUtils.addData(effortsMenu, "efforts_tab");
        ItemDataUtils.addData(recipesMenu, "recipes_menu");

        inventory.setItem(20, basicEnchants);
        inventory.setItem(21, enhancedEnchants);
        inventory.setItem(22, refinedEnchants);
        inventory.setItem(31, enchantedCrate);
        inventory.setItem(13, effortsMenu);
        inventory.setItem(23, eliteEnchants);
        inventory.setItem(24, mythicEnchants);
        inventory.setItem(inventory.getSize() - 7, recipesMenu);
        inventory.setItem(inventory.getSize() - 5, createViewer(player));
        inventory.setItem(inventory.getSize() - 1, exit);
        inventory.setItem(inventory.getSize() - 9, pluginItems);
        inventory.setItem(inventory.getSize() - 8, resourcesMenu);

        return inventory;
    }
    public static Inventory getMainGui() {
        String name = "<!i><light_purple>Mystic Enchanter";
        Inventory inventory = Bukkit.createInventory(null, 54);

        ItemStack exit = new ItemStack(Material.BARRIER);
        ItemStack resourcesMenu = new ItemStack(Material.AMETHYST_BLOCK);
        ItemStack basicEnchants = new ItemStack(Material.BOOK);
        ItemStack enhancedEnchants = new ItemStack(Material.BOOK);
        ItemStack refinedEnchants = new ItemStack(Material.BOOK);
        ItemStack eliteEnchants = new ItemStack(Material.BOOK);
        ItemStack mythicEnchants = new ItemStack(Material.BOOK);
        ItemStack pluginItems = new ItemStack(Material.BEACON);
        ItemStack effortsMenu = new ItemStack(Material.EMERALD);
        ItemStack recipesMenu = new ItemStack(Material.CRAFTING_TABLE);

        ItemMeta exitMeta = exit.getItemMeta();
        ItemMeta resourcesTabMeta = resourcesMenu.getItemMeta();
        ItemMeta basicEnchantsMeta = basicEnchants.getItemMeta();
        ItemMeta enhancedEnchantsMeta = enhancedEnchants.getItemMeta();
        ItemMeta refinedEnchantsMeta = refinedEnchants.getItemMeta();
        ItemMeta eliteEnchantsMeta = eliteEnchants.getItemMeta();
        ItemMeta mythicEnchantsMeta = mythicEnchants.getItemMeta();
        ItemMeta pluginItemsMeta = pluginItems.getItemMeta();
        ItemMeta effortsMenuMeta = effortsMenu.getItemMeta();
        ItemMeta recipesMenuMeta = recipesMenu.getItemMeta();

        basicEnchantsMeta.displayName(MiniMessage.miniMessage().deserialize("<reset><!i>Basic Enchantments"));
        basicEnchantsMeta.lore(List.of(
                Component.empty(),
                MiniMessage.miniMessage().deserialize("<!i><yellow>Left Click To Inspect"),
                MiniMessage.miniMessage().deserialize("<!i><green>Right Click To Roll"),
                Component.empty(),
                MiniMessage.miniMessage().deserialize("<!i>" + Gradient.RED + BookTiers.BASIC.getPrice() + " Mystic Coins")
        ));
        basicEnchants.setItemMeta(basicEnchantsMeta);

        resourcesTabMeta.displayName(MiniMessage.miniMessage().deserialize("<!i><gold>Mystic Resources Menu"));
        resourcesTabMeta.lore(List.of(
                Component.empty(),
                MiniMessage.miniMessage().deserialize("<!i><dark_purple>Opens the Resources Menu")
        ));
        resourcesMenu.setItemMeta(resourcesTabMeta);

        enhancedEnchantsMeta.displayName(MiniMessage.miniMessage().deserialize("<!i><green>Enhanced Enchantments"));
        enhancedEnchantsMeta.lore(List.of(
                Component.empty(),
                MiniMessage.miniMessage().deserialize("<!i><yellow>Left Click To Inspect"),
                MiniMessage.miniMessage().deserialize("<!i><green>Right Click To Roll"),
                Component.empty(),
                MiniMessage.miniMessage().deserialize("<!i>" + Gradient.RED + BookTiers.ENHANCED.getPrice() + " Mystic Coins")
        ));
        enhancedEnchants.setItemMeta(enhancedEnchantsMeta);

        refinedEnchantsMeta.displayName(MiniMessage.miniMessage().deserialize("<!i><aqua>Refined Enchantments"));
        refinedEnchantsMeta.lore(List.of(
                Component.empty(),
                MiniMessage.miniMessage().deserialize("<!i><yellow>Left Click To Inspect"),
                MiniMessage.miniMessage().deserialize("<!i><green>Right Click To Roll"),
                Component.empty(),
                MiniMessage.miniMessage().deserialize("<!i>" + Gradient.RED + BookTiers.REFINED.getPrice() + " Mystic Coins")
        ));
        refinedEnchants.setItemMeta(refinedEnchantsMeta);

        eliteEnchantsMeta.displayName(MiniMessage.miniMessage().deserialize("<!i><#FF55FF>Elite Enchantments"));
        eliteEnchantsMeta.lore(List.of(
                Component.empty(),
                MiniMessage.miniMessage().deserialize("<!i><yellow>Left Click To Inspect"),
                MiniMessage.miniMessage().deserialize("<!i><green>Right Click To Roll"),
                Component.empty(),
                MiniMessage.miniMessage().deserialize("<!i>" + Gradient.RED + BookTiers.ELITE.getPrice() + " Mystic Coins")
        ));
        eliteEnchants.setItemMeta(eliteEnchantsMeta);

        MiniMessage mm = MiniMessage.miniMessage();

        mythicEnchantsMeta.displayName(MiniMessage.miniMessage().deserialize("<!i><#FF3030>Mythic Enchantments"));
        mythicEnchantsMeta.lore(List.of(
                Component.empty(),
                MiniMessage.miniMessage().deserialize("<!i><#FFD700>Left Click To Inspect"),
                MiniMessage.miniMessage().deserialize("<!i><#00FF7F>Right Click To Roll"),
                Component.empty(),
                MiniMessage.miniMessage().deserialize("<!i>" + Gradient.RED + BookTiers.MYTHIC.getPrice() + " Mystic Coins")
        ));
        mythicEnchants.setItemMeta(mythicEnchantsMeta);

        effortsMenuMeta.displayName(MiniMessage.miniMessage().deserialize("<!i>" + Gradient.BLUE + "Mystic Coins Giver"));
        effortsMenuMeta.lore(List.of(
                Component.empty(),
                MiniMessage.miniMessage().deserialize("<!i>" + Gradient.YELLOW + "♦ Mystic Coins Menu"),
                MiniMessage.miniMessage().deserialize("<!i>" + Gradient.YELLOW + "♦ Get Mystic Coins With Effort Points")
        ));
        effortsMenu.setItemMeta(effortsMenuMeta);

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

        resourcesTabMeta.displayName(MiniMessage.miniMessage().deserialize("<!i><bold><gold>Mystic Resources"));
        resourcesTabMeta.lore(List.of(
                Component.empty(),
                MiniMessage.miniMessage().deserialize("<!i><gray>Mystic Enchantments Resources"),
                MiniMessage.miniMessage().deserialize("<!i><green>Click To Open")
        ));
        resourcesMenu.setItemMeta(resourcesTabMeta);

        exitMeta.displayName(MiniMessage.miniMessage().deserialize("<!i><gray>Exit"));
        exit.setItemMeta(exitMeta);

        ItemDataUtils.addData(exit, "exit");
        ItemDataUtils.addData(basicEnchants, "basic_tab");
        ItemDataUtils.addData(enhancedEnchants, "enhanced_tab");
        ItemDataUtils.addData(refinedEnchants, "refined_tab");
        ItemDataUtils.addData(eliteEnchants, "elite_tab");
        ItemDataUtils.addData(mythicEnchants, "mythic_tab");
        ItemDataUtils.addData(pluginItems, "options_tab");
        ItemDataUtils.addData(resourcesMenu, "resources_tab");
        ItemDataUtils.addData(effortsMenu, "efforts_tab");
        ItemDataUtils.addData(recipesMenu, "recipes_menu");

        if (ConfigSettings.BASIC_ENCHANTMENTS_ACCESS.getValue()) inventory.setItem(20, basicEnchants);
        if (ConfigSettings.ENHANCED_ENCHANTMENTS_ACCESS.getValue()) inventory.setItem(21, enhancedEnchants);
        if (ConfigSettings.REFINED_ENCHANTMENTS_ACCESS.getValue()) inventory.setItem(22, refinedEnchants);
        if (ConfigSettings.ELITE_ENCHANTMENTS_ACCESS.getValue()) inventory.setItem(23, eliteEnchants);
        if (ConfigSettings.MYTHIC_ENCHANTMENTS_ACCESS.getValue()) inventory.setItem(24, mythicEnchants);

        inventory.setItem(13, effortsMenu);
        if (ConfigSettings.ENABLE_MYSTIC_RECIPES.getValue()) inventory.setItem(inventory.getSize() - 7, recipesMenu);

        inventory.setItem(inventory.getSize() - 1, exit);
        inventory.setItem(inventory.getSize() - 9, pluginItems);
        inventory.setItem(inventory.getSize() - 8, resourcesMenu);

        return inventory;
    }
    public static List<MysticRecipe> getMysticRecipes() {
        return MysticRecipeUtils.getRecipes();
    }

    public static Inventory getRecipesInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 36, MiniMessage.miniMessage().deserialize(Gradient.AQUA + "Mystic Recipes"));
        List<MysticRecipe> mysticRecipes = getMysticRecipes();
        for (int i = 0; i < inventory.getSize() && i < mysticRecipes.size(); i++) {
            inventory.setItem(i, mysticRecipes.get(i).getDisplayItem());
        }
        inventory.setItem(inventory.getSize() - 1, createBack());
        inventory.setItem(inventory.getSize() - 5, createViewer(player));
        return inventory;
    }
    public static Inventory getRecipesInventory() {
        Inventory inventory = Bukkit.createInventory(null, 36);
        List<MysticRecipe> mysticRecipes = getMysticRecipes();
        for (int i = 0; i < inventory.getSize() && i < mysticRecipes.size(); i++) {
            inventory.setItem(i, mysticRecipes.get(i).getDisplayItem());
        }
        inventory.setItem(inventory.getSize() - 1, createBack());

        return inventory;
    }

    public static Inventory getEnchantedCrateGui(Player player) {
        String guiTitle = "<gradient:#BA55D3:#FF6EC7><bold>\uD83C\uDF20    Enchanted Crate     \uD83C\uDF20</bold></gradient>";
        Inventory inventory = Bukkit.createInventory(null, 36, MiniMessage.miniMessage().deserialize(guiTitle));
        List<EnchantedItem> enchantedItems = Helper.getEnchantedItems();

        for (int i = 0; i < enchantedItems.size() && i < inventory.getSize(); i++) inventory.setItem(i, enchantedItems.get(i).getDisplayedItem());

        inventory.setItem(inventory.getSize() - 1, createBack());
        inventory.setItem(inventory.getSize() - 5, createViewer(player));

        return inventory;
    }
    public static Inventory getEnchantedCrateGui() {
        Inventory inventory = Bukkit.createInventory(null, 36);
        List<EnchantedItem> enchantedItems = Helper.getEnchantedItems();

        for (int i = 0; i < enchantedItems.size() && i < inventory.getSize(); i++) inventory.setItem(i, enchantedItems.get(i).getDisplayedItem());

        inventory.setItem(inventory.getSize() - 1, createBack());


        return inventory;
    }
    public static Inventory getResourcesGui(Player player) {
        String name = "<!i><light_purple>Mystic Enchanter - <reset><aqua>Resources";
        Inventory inventory = Bukkit.createInventory(null, 36, MiniMessage.miniMessage().deserialize(name));
        ItemStack soulsItem = new ItemStack(Material.ZOMBIE_HEAD);
        ItemStack fragmentsItem = new ItemStack(Material.DIAMOND_ORE);
        ItemStack sapsItem = new ItemStack(Material.OAK_LOG);

        ItemMeta soulsItemMeta = soulsItem.getItemMeta();
        ItemMeta fragmentsItemMeta = fragmentsItem.getItemMeta();
        ItemMeta sapsItemMeta = sapsItem.getItemMeta();

        soulsItemMeta.displayName(MiniMessage.miniMessage().deserialize("<!i><#ff6464>Mystic Souls"));
        soulsItemMeta.lore(List.of(
                MiniMessage.miniMessage().deserialize("<!i><#6ef11e>Mystic Souls are obtained from killing monsters"),
                MiniMessage.miniMessage().deserialize("<!i><#1ef1b7>5% Base chance of obtaining from mobs, can be boosted with enchantments"),
                Component.empty(),
                MiniMessage.miniMessage().deserialize("<!i><#ffd88f>Souls: " + MysticCurrencyUtils.getSouls(player)),
                MiniMessage.miniMessage().deserialize("<!i><#f1a71e>Right-Click to convert the souls to <#a2ffa3>" + MysticCurrencyUtils.calculateTotalSoulsXpWorth(player) + " Mystic Coins")
        ));
        soulsItem.setItemMeta(soulsItemMeta);

        fragmentsItemMeta.displayName(MiniMessage.miniMessage().deserialize("<!i><#ff6464>Mystic Fragments"));
        fragmentsItemMeta.lore(List.of(
                MiniMessage.miniMessage().deserialize("<!i><#6ef11e>Mystic Fragments are obtained from mining ores"),
                MiniMessage.miniMessage().deserialize("<!i><#1ef1b7>1.5% Base chance of obtaining from ores, can be boosted with enchantments"),
                Component.empty(),
                MiniMessage.miniMessage().deserialize("<!i><#ffd88f>Fragments: " + MysticCurrencyUtils.getFragments(player)),
                MiniMessage.miniMessage().deserialize("<!i><#f1a71e>Right-Click to convert the fragments to <#a2ffa3>" + MysticCurrencyUtils.calculateTotalFragmentsXpWorth(player) + " Mystic Coins")
        ));
        fragmentsItem.setItemMeta(fragmentsItemMeta);

        sapsItemMeta.displayName(MiniMessage.miniMessage().deserialize("<!i><#ff6464>Mystic Sap"));
        sapsItemMeta.lore(List.of(
                MiniMessage.miniMessage().deserialize("<!i><#6ef11e>Mystic Sap are obtained from Chopping Logs"),
                MiniMessage.miniMessage().deserialize("<!i><#1ef1b7>0.5% Base chance of obtaining from logs, can be boosted with enchantments"),
                Component.empty(),
                MiniMessage.miniMessage().deserialize("<!i><#ffd88f>Sap: " + MysticCurrencyUtils.getSap(player)),
                MiniMessage.miniMessage().deserialize("<!i><#f1a71e>Right-Click to convert the saps to <#a2ffa3>" + MysticCurrencyUtils.calculateTotalSapXpWorth(player) + " Mystic Coins")
        ));
        sapsItem.setItemMeta(sapsItemMeta);

        ItemDataUtils.addData(soulsItem, "souls");
        ItemDataUtils.addData(sapsItem, "saps");
        ItemDataUtils.addData(fragmentsItem, "fragments");

        inventory.setItem(12, soulsItem);
        inventory.setItem(13, fragmentsItem);
        inventory.setItem(14, sapsItem);
        inventory.setItem(inventory.getSize() - 1, createBack());
        inventory.setItem(inventory.getSize() - 5, createViewer(player));
        return inventory;
    }
    public static ItemStack createViewer(Player player) {
        ItemStack item = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(MiniMessage.miniMessage().deserialize("<!i><aqua>Info"));
        meta.lore(List.of(
                Component.empty(),
                MiniMessage.miniMessage().deserialize("<!i><bold>" + Gradient.YELLOW + "Mystic Coins: </bold>" + Gradient.AQUA + MysticCoinUtils.getCoin(player) + " Coins"),
                Component.empty(),
                MiniMessage.miniMessage().deserialize("<!i><bold>" + Gradient.YELLOW + "Mystic Souls: </bold>" + Gradient.AQUA + MysticCurrencyUtils.getSouls(player) + " Souls"),
                MiniMessage.miniMessage().deserialize("<!i><bold>" + Gradient.YELLOW + "Mystic Fragments: </bold>" + Gradient.AQUA + MysticCurrencyUtils.getFragments(player) + " Fragments"),
                MiniMessage.miniMessage().deserialize("<!i><bold>" + Gradient.YELLOW + "Mystic Sap: </bold>" + Gradient.AQUA + MysticCurrencyUtils.getSap(player) + " Sap")
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

    public static Inventory getEffortsGui(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 36, MiniMessage.miniMessage().deserialize(Gradient.BLUE + "☼   Effort Points   ☼"));

        ItemStack viewer = new ItemStack(Material.EMERALD);

        ItemMeta meta = viewer.getItemMeta();

        MiniMessage mm = MiniMessage.miniMessage();

        meta.displayName(mm.deserialize(
                "<gradient:#BA55D3:#FF6EC7><bold><!i>✧ Mystic Effort Points ✧</bold></gradient>"
        ));

        meta.lore(List.of(
                // Instructions
                mm.deserialize("<gray><!i>• Get Mystic Effort Points by"),
                mm.deserialize("<gradient:#FFACFC:#FF77A9><!i>  Damaging and Breaking Blocks (Hardness > 1)</gradient>"),
                Component.empty(),

                // Progress Section
                mm.deserialize("<!i>" + Gradient.GREEN + "• Progress: " +
                        MysticCoinHandler.getEffortPoints(player) + "/" +
                        MysticCoinHandler.getNeededEfforts()),
                Component.empty(),

                // New Level & Booster Section
                mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>"),
                mm.deserialize("<!i>" + Gradient.PINK + "• Level: " + MysticCoinHandler.getLevel(player)),
                mm.deserialize("<!i>" + Gradient.AQUA + "• Booster: " + MysticCoinHandler.getBooster(player) + "%"),
                mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>"),
                Component.empty(),

                // Reward Section
                mm.deserialize("<!i>" + Gradient.AQUA + "• Earn " +
                        MysticCoinHandler.getMinMysticCoinsReward(player) + "-" +
                        MysticCoinHandler.getMaxMysticCoinsReward(player) + " Mystic Coins"),
                mm.deserialize("<!i>" + Gradient.YELLOW + "when you reach the goal!")
        ));
        viewer.setItemMeta(meta);

        inventory.setItem(inventory.getSize() - 1, createBack());
        inventory.setItem(inventory.getSize() - 5, createViewer(player));
        inventory.setItem(13, viewer);
        return inventory;
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
