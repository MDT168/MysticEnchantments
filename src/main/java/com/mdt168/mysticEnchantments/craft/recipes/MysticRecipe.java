package com.mdt168.mysticEnchantments.craft.recipes;

import com.mdt168.mysticEnchantments.craft.MysticItems;
import com.mdt168.mysticEnchantments.craft.recipes.utility.MysticRecipeUtils;
import com.mdt168.mysticEnchantments.custom.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MysticRecipe {
    private final String name, description, id;
    private final int levelRequirement;
    private final ItemStack displayItem, resultItem;
    private final List<ItemStack> requirements;
    protected MysticRecipe(String name, String description, int levelRequirement, Material material, ItemStack resultItem, List<ItemStack> requirements) {
        this.name = name;
        this.description = description;
        this.id = this.name.toLowerCase().replace(" ", "_").replace("'", "") + "_recipe";
        this.levelRequirement = levelRequirement;
        this.displayItem = new ItemStack(material);
        this.requirements = requirements;
        this.resultItem = resultItem;

        MiniMessage mm = MiniMessage.miniMessage();
        ItemMeta meta = displayItem.getItemMeta();
        meta.displayName(mm.deserialize(
                "<gradient:#8A2BE2:#9400D3><bold><!i>✧ " + name + " ✧</bold></gradient>"
        ));
        displayItem.setItemMeta(meta);
        ItemDataUtils.addData(displayItem, id);
        buildLore();
    }
    protected MysticRecipe(String name, String description, int levelRequirement, ItemStack itemToDisplay, ItemStack resultItem, List<ItemStack> requirements) {
        this.name = name;
        this.description = description;
        this.id = this.name.toLowerCase().replace(" ", "_").replace("'", "") + "_recipe";
        this.levelRequirement = levelRequirement;
        this.displayItem = itemToDisplay.clone();
        this.requirements = requirements;
        this.resultItem = resultItem;

        MiniMessage mm = MiniMessage.miniMessage();
        ItemMeta meta = this.displayItem.getItemMeta();
        meta.displayName(mm.deserialize(
                "<gradient:#8A2BE2:#9400D3><bold><!i>✧ " + name + " ✧</bold></gradient>"
        ));
        this.displayItem.setItemMeta(meta);
        ItemDataUtils.addData(displayItem, id);
        buildLore();
    }
    protected MysticRecipe(String name, String description, int levelRequirement, Material material, int amount, ItemStack resultItem, List<ItemStack> requirements) {
        this.name = name;
        this.id = this.name.toLowerCase().replace(" ", "_").replace("'", "");
        this.displayItem = new ItemStack(material, amount);
        this.requirements = requirements;
        this.levelRequirement = levelRequirement;
        this.resultItem = resultItem;
        this.description = description;

        MiniMessage mm = MiniMessage.miniMessage();
        ItemMeta meta = displayItem.getItemMeta();
        meta.displayName(mm.deserialize(
                "<!i><gradient:#8A2BE2:#9400D3><bold>✧ " + name + " ✧</bold></gradient>"
        ));
        this.displayItem.setItemMeta(meta);
        ItemDataUtils.addData(this.displayItem, id);
        buildLore();
    }

    public static MysticRecipe register(String name, String description, int levelRequirement, Material material, ItemStack resultItem, List<ItemStack> requirements) {
        MysticRecipe recipe = new MysticRecipe(name, description, levelRequirement, material, resultItem, requirements);
        MysticRecipeUtils.add(recipe);
        return recipe;
    }
    public static MysticRecipe register(String name, String description, int levelRequirement, ItemStack itemToDisplay, ItemStack resultItem, List<ItemStack> requirements) {
        MysticRecipe recipe = new MysticRecipe(name, description, levelRequirement, itemToDisplay, resultItem, requirements);
        MysticRecipeUtils.add(recipe);
        return recipe;
    }
    public static MysticRecipe register(String name, String description, int levelRequirement, Material material, int amount, ItemStack resultItem, List<ItemStack> requirements) {
        MysticRecipe recipe = new MysticRecipe(name, description, levelRequirement, material, amount, resultItem, requirements);
        MysticRecipeUtils.add(recipe);
        return recipe;
    }



    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public int getLevelRequirement() {
        return levelRequirement;
    }

    public ItemStack getDisplayItem() {
        return displayItem;
    }

    public ItemStack getResultItem() {
        return resultItem;
    }

    public List<ItemStack> getRequirements() {
        return this.requirements;
    }

    /**
     * Crafts the Recipe, takes the required items and gives the result to the player
     * @param player Target Player
     * @return {@code true} Player had enough items and Mystic Level and the crafting process was successful
     *         {@code false} Player didn't have enough items, or didn't have enough Mystic Levels and the crafting process wasn't successful
     */
    public boolean craft(Player player) {
        for (ItemStack itemStack : getRequirements()) {
            System.out.println(itemStack);
        }
        int level = MysticCoinHandler.getLevel(player);
        if (level >= getLevelRequirement()) {
            if (InventoryUtils.hasItems(player, getRequirements())) {
                if (Helper.safeGiveItem(player, getResultItem())) {
                    InventoryUtils.removeItems(player, getRequirements());
                } else return false;
            } else {
                Helper.sendWarningMessage(player, "You don't have enough items to craft this!");
                return false;
            }
        } else {
            Helper.sendWarningMessage(player, "You don't have enough Mystic Levels!");
            return false;
        }
        return true;
    }
    /**
     * Crafts the Recipe, takes the required items and gives the result to the player
     * @param player Target Player
     * @param crafted this is used with craft all mechanics to prevent sending a warning message if the item was already crafted
     * @return {@code true} Player had enough items and Mystic Level and the crafting process was successful
     *         {@code false} Player didn't have enough items, or didn't have enough Mystic Levels and the crafting process wasn't successful
     */
    public boolean craft(Player player, boolean crafted) {
        int level = MysticCoinHandler.getLevel(player);
        if (level >= getLevelRequirement()) {
            if (InventoryUtils.hasItems(player, getRequirements())) {
                if (Helper.safeGiveItem(player, getResultItem())) {
                    InventoryUtils.removeItems(player, getRequirements());
                } else return false;
            } else {
                if (!crafted) {
                    Helper.sendWarningMessage(player, "You don't have enough items to craft this!");
                }
                return false;
            }
        } else {
            Helper.sendWarningMessage(player, "You don't have enough Mystic Levels!");
            return false;
        }
        return true;
    }
    public Inventory getRequirementsInventory(Player player) {
        MiniMessage mm = MiniMessage.miniMessage();
        List<ItemStack> requiredItems = getRequirements();
        Inventory inventory = Bukkit.createInventory(null, 36, MiniMessage.miniMessage().deserialize(Gradient.GREEN + "Required Items"));
        int j = 0;
        for (int i = 0; i < inventory.getSize() && j < requiredItems.size(); i++) {
            if (i == 0 && getLevelRequirement() > 0) {
                ItemStack level = MysticItems.MYSTIC_LEVEL_ITEM.clone();
                level.editMeta(meta -> meta.displayName(mm.deserialize(Gradient.AQUA + "<!i>" + getLevelRequirement() + " Mystic Level")));
                inventory.setItem(i, level);
                continue;
            } else if (i == 1 && this instanceof MysticCoinRecipe mysticCoinRecipe) {
                ItemStack coins = MysticItems.MYSTIC_COIN_ITEM.clone();
                coins.editMeta(meta -> meta.displayName(mm.deserialize(Gradient.AQUA.getGradient() + "<!i>" + mysticCoinRecipe.getMysticCoinsPrice() + " Mystic Coins")));
                inventory.setItem(i, coins);
                continue;
            }
            ItemStack item = requiredItems.get(j);
            j++;
            int amount = item.getAmount();
            ItemStack clone = item.clone();
            clone.setAmount(1);
            ItemMeta meta = clone.getItemMeta();
            if (meta == null) {
                inventory.setItem(i, requiredItems.get(i));
                continue;
            }
            meta.displayName(mm.deserialize("<!i>" + Gradient.AQUA + amount + "x " + Helper.getRawName(clone)));
            clone.setItemMeta(meta);
            inventory.setItem(i, clone);
        }
        inventory.setItem(inventory.getSize() - 5, GuiHelper.createViewer(player));
        inventory.setItem(inventory.getSize() - 1, GuiHelper.createBack());
        return inventory;
    }
    public Inventory getResultInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 36, MiniMessage.miniMessage().deserialize(Gradient.GREEN + "Result Item"));
        inventory.setItem(13, this.resultItem.clone());
        inventory.setItem(inventory.getSize() - 5, GuiHelper.createViewer(player));
        inventory.setItem(inventory.getSize() - 1, GuiHelper.createBack());
        return inventory;
    }
    protected void buildLore() {
        displayItem.editMeta(meta -> {
            MiniMessage mm = MiniMessage.miniMessage();
            List<Component> lore = new ArrayList<>(List.of(
                    mm.deserialize("<#FFFFFF><!i>" + description + "</#FFFFFF>"),
                    mm.deserialize(Gradient.RED + "<!i>Mystic Level Requirement: " + levelRequirement)
            ));

            if (this instanceof MysticCoinRecipe mysticCoinRecipe) {
                lore.add(mm.deserialize(Gradient.RED + "<!i>Mystic Coins Requirement: " + mysticCoinRecipe.getMysticCoinsPrice()));
            }

            lore.add(Component.empty());
            lore.add(mm.deserialize("<#FFD700><!i>┃ <gradient:#FFAA00:#FFDD00>Requirements</gradient>"));
            for (ItemStack itemStack : requirements) {
                PlainTextComponentSerializer serializer = PlainTextComponentSerializer.plainText();
                String displayNameString = serializer.serialize(itemStack.effectiveName());
                int amount = itemStack.getAmount();
                lore.add(mm.deserialize("<!i><gray>• " + Gradient.PINK + amount + "x " +  displayNameString));
            }
            lore.add(mm.deserialize("<!i><gradient:#8A2BE2:#9400D3>━━━━━━━━━━</gradient>"));
            lore.add(mm.deserialize("<!i><yellow>⟶ Left-Click To Craft</yellow>"));
            lore.add(mm.deserialize("<!i><yellow>⟶ Shift-Left Click To Craft All</yellow>"));
            lore.add(mm.deserialize("<!i><yellow>⟶ Shift-Right Click To Inspect Result</yellow>"));
            lore.add(mm.deserialize("<!i><yellow>⟶ Right-Click To Inspect Requirements</yellow>"));
            meta.lore(lore);
        });
    }
}
