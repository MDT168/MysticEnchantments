package com.mdt168.mysticEnchantments.craft.recipes;

import com.mdt168.mysticEnchantments.MysticEnchantments;
import com.mdt168.mysticEnchantments.config.ConfigSettings;
import com.mdt168.mysticEnchantments.craft.recipes.utility.MysticRecipeUtils;
import com.mdt168.mysticEnchantments.custom.Helper;
import com.mdt168.mysticEnchantments.custom.InventoryUtils;
import com.mdt168.mysticEnchantments.custom.MysticCoinHandler;
import com.mdt168.mysticEnchantments.custom.MysticCoinUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MysticCoinRecipe extends MysticRecipe {

    private final double mysticCoinsPrice;

    protected MysticCoinRecipe(String name, String description, int levelRequirement, Material material, ItemStack resultItem, List<ItemStack> requirements, double mysticCoinsPrice) {
        super(name, description, levelRequirement, material, resultItem, requirements);
        this.mysticCoinsPrice = Helper.round(mysticCoinsPrice, 2);
        buildLore();
    }

    protected MysticCoinRecipe(String name, String description, int levelRequirement, ItemStack itemToDisplay, ItemStack resultItem, List<ItemStack> requirements, double mysticCoinsPrice) {
        super(name, description, levelRequirement, itemToDisplay, resultItem, requirements);
        this.mysticCoinsPrice = Helper.round(mysticCoinsPrice, 2);
        buildLore();
    }

    protected MysticCoinRecipe(String name, String description, int levelRequirement, Material material, int amount, ItemStack resultItem, List<ItemStack> requirements, double mysticCoinsPrice) {
        super(name, description, levelRequirement, material, amount, resultItem, requirements);
        this.mysticCoinsPrice = Helper.round(mysticCoinsPrice, 2);
        buildLore();
    }

    public static MysticCoinRecipe register(String name, String description, int levelRequirement, Material material, ItemStack resultItem, List<ItemStack> requirements, double mysticCoinsPrice) {
        MysticCoinRecipe recipe = new MysticCoinRecipe(name, description, levelRequirement, material, resultItem, requirements, mysticCoinsPrice);
        MysticRecipeUtils.add(recipe);
        return recipe;
    }

    public static MysticCoinRecipe register(String name, String description, int levelRequirement, ItemStack itemToDisplay, ItemStack resultItem, List<ItemStack> requirements, double mysticCoinsPrice) {
        MysticCoinRecipe recipe = new MysticCoinRecipe(name, description, levelRequirement, itemToDisplay, resultItem, requirements, mysticCoinsPrice);
        MysticRecipeUtils.add(recipe);
        return recipe;
    }

    public static MysticCoinRecipe register(String name, String description, int levelRequirement, Material material, int amount, ItemStack resultItem, List<ItemStack> requirements, double mysticCoinsPrice) {
        MysticCoinRecipe recipe = new MysticCoinRecipe(name, description, levelRequirement, material, amount, resultItem, requirements, mysticCoinsPrice);
        MysticRecipeUtils.add(recipe);
        return recipe;
    }
    public static MysticCoinRecipe register(String name, String description, int levelRequirement, Material material, ItemStack resultItem, List<ItemStack> requirements, double mysticCoinsPrice, boolean skipOnApiMode) {
        MysticCoinRecipe recipe = new MysticCoinRecipe(name, description, levelRequirement, material, resultItem, requirements, mysticCoinsPrice);
        if ((!(ConfigSettings.API_MODE.getValue() && skipOnApiMode))) MysticRecipeUtils.add(recipe);
        else MysticEnchantments.blockedContentFromApiMode++;
        return recipe;
    }

    public static MysticCoinRecipe register(String name, String description, int levelRequirement, ItemStack itemToDisplay, ItemStack resultItem, List<ItemStack> requirements, double mysticCoinsPrice, boolean skipOnApiMode) {
        MysticCoinRecipe recipe = new MysticCoinRecipe(name, description, levelRequirement, itemToDisplay, resultItem, requirements, mysticCoinsPrice);
        if ((!(ConfigSettings.API_MODE.getValue() && skipOnApiMode))) MysticRecipeUtils.add(recipe);
        else MysticEnchantments.blockedContentFromApiMode++;
        return recipe;
    }

    public static MysticCoinRecipe register(String name, String description, int levelRequirement, Material material, int amount, ItemStack resultItem, List<ItemStack> requirements, double mysticCoinsPrice, boolean skipOnApiMode) {
        MysticCoinRecipe recipe = new MysticCoinRecipe(name, description, levelRequirement, material, amount, resultItem, requirements, mysticCoinsPrice);
        if ((!(ConfigSettings.API_MODE.getValue() && skipOnApiMode))) MysticRecipeUtils.add(recipe);
        else MysticEnchantments.blockedContentFromApiMode++;
        return recipe;
    }

    public double getMysticCoinsPrice() {
        return mysticCoinsPrice;
    }

    @Override
    public boolean craft(Player player) {
        for (ItemStack itemStack : getRequirements()) {
            System.out.println(itemStack);
        }
        int level = MysticCoinHandler.getLevel(player);
        if (level >= getLevelRequirement()) {
            if (MysticCoinUtils.has(player, getMysticCoinsPrice())) {
                if (InventoryUtils.hasItems(player, getRequirements())) {
                    if (Helper.safeGiveItem(player, getResultItem())) {
                        InventoryUtils.removeItems(player, getRequirements());
                        MysticCoinUtils.removeCoin(player, getMysticCoinsPrice());
                    } else return false;
                } else {
                    Helper.sendWarningMessage(player, "You don't have enough items to craft this!");
                    return false;
                }
            } else {
                Helper.sendWarningMessage(player, "You don't have enough Mystic Coins!");
                return false;
            }
        } else {
            Helper.sendWarningMessage(player, "You don't have enough Mystic Levels!");
            return false;
        }
        return true;
    }

    @Override
    public boolean craft(Player player, boolean crafted) {
        int level = MysticCoinHandler.getLevel(player);
        if (level >= getLevelRequirement()) {
            if (MysticCoinUtils.has(player, getMysticCoinsPrice())) {
                if (InventoryUtils.hasItems(player, getRequirements())) {
                    if (Helper.safeGiveItem(player, getResultItem())) {
                        InventoryUtils.removeItems(player, getRequirements());
                        MysticCoinUtils.removeCoin(player, getMysticCoinsPrice());
                    } else return false;
                } else {
                    if (!crafted) {
                        Helper.sendWarningMessage(player, "You don't have enough items to craft this!");
                    }
                    return false;
                }
            } else {
                Helper.sendWarningMessage(player, "You don't have enough Mystic Coins!");
                return false;
            }
        } else {
            Helper.sendWarningMessage(player, "You don't have enough Mystic Levels!");
            return false;
        }
        return true;
    }
}
