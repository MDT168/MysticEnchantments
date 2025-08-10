package com.mdt168.mysticEnchantments.craft.recipes;

import com.mdt168.mysticEnchantments.MysticEnchantments;
import com.mdt168.mysticEnchantments.config.ConfigSettings;
import com.mdt168.mysticEnchantments.craft.recipes.utility.FlexEnchantment;
import com.mdt168.mysticEnchantments.craft.recipes.utility.MysticRecipeUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class EnchantedMysticRecipe extends MysticRecipe {

    private final FlexEnchantment flexEnchantment;

    public EnchantedMysticRecipe(String name, String description, int levelRequirement, Material material, ItemStack resultItem, List<ItemStack> requirements, FlexEnchantment flexEnchantment) {
        super(name, description, levelRequirement, material, resultItem, requirements);
        this.flexEnchantment = flexEnchantment;
    }

    public EnchantedMysticRecipe(String name, String description, int levelRequirement, ItemStack itemToDisplay, ItemStack resultItem, List<ItemStack> requirements, FlexEnchantment flexEnchantment) {
        super(name, description, levelRequirement, itemToDisplay, resultItem, requirements);
        this.flexEnchantment = flexEnchantment;
    }

    public EnchantedMysticRecipe(String name, String description, int levelRequirement, Material material, int amount, ItemStack resultItem, List<ItemStack> requirements, FlexEnchantment flexEnchantment) {
        super(name, description, levelRequirement, material, amount, resultItem, requirements);
        this.flexEnchantment = flexEnchantment;
    }
    public static EnchantedMysticRecipe register(String name, String description, int levelRequirement, Material material, ItemStack resultItem, List<ItemStack> requirements, FlexEnchantment flexEnchantment, boolean skipOnApiMode) {
        EnchantedMysticRecipe recipe = new EnchantedMysticRecipe(name, description, levelRequirement, material, resultItem, requirements, flexEnchantment);
        if ((!(ConfigSettings.API_MODE.getValue() && skipOnApiMode))) MysticRecipeUtils.add(recipe);
        else MysticEnchantments.blockedContentFromApiMode++;
        return recipe;
    }

    public static EnchantedMysticRecipe register(String name, String description, int levelRequirement, ItemStack itemToDisplay, ItemStack resultItem, List<ItemStack> requirements, FlexEnchantment flexEnchantment, boolean skipOnApiMode) {
        EnchantedMysticRecipe recipe = new EnchantedMysticRecipe(name, description, levelRequirement, itemToDisplay, resultItem, requirements, flexEnchantment);
        if ((!(ConfigSettings.API_MODE.getValue() && skipOnApiMode))) MysticRecipeUtils.add(recipe);
        else MysticEnchantments.blockedContentFromApiMode++;
        return recipe;
    }

    public static EnchantedMysticRecipe register(String name, String description, int levelRequirement, Material material, int amount, ItemStack resultItem, List<ItemStack> requirements, FlexEnchantment flexEnchantment, boolean skipOnApiMode) {
        EnchantedMysticRecipe recipe = new EnchantedMysticRecipe(name, description, levelRequirement, material, amount, resultItem, requirements, flexEnchantment);
        if ((!(ConfigSettings.API_MODE.getValue() && skipOnApiMode))) MysticRecipeUtils.add(recipe);
        else MysticEnchantments.blockedContentFromApiMode++;
        return recipe;
    }

    @Override
    public ItemStack getResultItem() {
        return flexEnchantment.getEnchanted();
    }
}
