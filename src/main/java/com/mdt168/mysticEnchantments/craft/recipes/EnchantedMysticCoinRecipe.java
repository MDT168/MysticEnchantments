package com.mdt168.mysticEnchantments.craft.recipes;

import com.mdt168.mysticEnchantments.craft.recipes.utility.FlexEnchantment;
import com.mdt168.mysticEnchantments.craft.recipes.utility.MysticRecipeUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class EnchantedMysticCoinRecipe extends MysticCoinRecipe {

    private final FlexEnchantment flexEnchantment;

    protected EnchantedMysticCoinRecipe(String name, String description, int levelRequirement, Material material, ItemStack resultItem, List<ItemStack> requirements, double mysticCoinsPrice, FlexEnchantment flexEnchantment) {
        super(name, description, levelRequirement, material, resultItem, requirements, mysticCoinsPrice);
        this.flexEnchantment = flexEnchantment;
    }

    protected EnchantedMysticCoinRecipe(String name, String description, int levelRequirement, ItemStack itemToDisplay, ItemStack resultItem, List<ItemStack> requirements, double mysticCoinsPrice, FlexEnchantment flexEnchantment) {
        super(name, description, levelRequirement, itemToDisplay, resultItem, requirements, mysticCoinsPrice);
        this.flexEnchantment = flexEnchantment;
    }

    protected EnchantedMysticCoinRecipe(String name, String description, int levelRequirement, Material material, int amount, ItemStack resultItem, List<ItemStack> requirements, double mysticCoinsPrice, FlexEnchantment flexEnchantment) {
        super(name, description, levelRequirement, material, amount, resultItem, requirements, mysticCoinsPrice);
        this.flexEnchantment = flexEnchantment;
    }
    public static EnchantedMysticCoinRecipe register(String name, String description, int levelRequirement, Material material, ItemStack resultItem, List<ItemStack> requirements, double mysticCoinsPrice, FlexEnchantment flexEnchantment) {
        EnchantedMysticCoinRecipe recipe = new EnchantedMysticCoinRecipe(name, description, levelRequirement, material, resultItem, requirements, mysticCoinsPrice, flexEnchantment);
        MysticRecipeUtils.add(recipe);
        return recipe;
    }

    public static EnchantedMysticCoinRecipe register(String name, String description, int levelRequirement, ItemStack itemToDisplay, ItemStack resultItem, List<ItemStack> requirements, double mysticCoinsPrice, FlexEnchantment flexEnchantment) {
        EnchantedMysticCoinRecipe recipe = new EnchantedMysticCoinRecipe(name, description, levelRequirement, itemToDisplay, resultItem, requirements, mysticCoinsPrice, flexEnchantment);
        MysticRecipeUtils.add(recipe);
        return recipe;
    }

    public static EnchantedMysticCoinRecipe register(String name, String description, int levelRequirement, Material material, int amount, ItemStack resultItem, List<ItemStack> requirements, double mysticCoinsPrice, FlexEnchantment flexEnchantment) {
        EnchantedMysticCoinRecipe recipe = new EnchantedMysticCoinRecipe(name, description, levelRequirement, material, amount, resultItem, requirements, mysticCoinsPrice, flexEnchantment);
        MysticRecipeUtils.add(recipe);
        return recipe;
    }

    @Override
    public ItemStack getResultItem() {
        return flexEnchantment.getEnchanted();
    }
}
