package com.mdt168.mysticEnchantments.craft.recipes.utility;

import com.mdt168.mysticEnchantments.enchants.EnchantmentStack;
import com.mdt168.mysticEnchantments.custom.Helper;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemEnchantments;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class FlexEnchantment {
    private final ItemStack itemStack;
    private final Enchantment enchantment;
    public FlexEnchantment(Enchantment enchantment, ItemStack itemStack) {
        this.enchantment = enchantment;
        this.itemStack = itemStack;
    }
    public FlexEnchantment(EnchantmentStack enchantmentStack, ItemStack itemStack) {
        this.enchantment = enchantmentStack.getEnchantment();
        this.itemStack = itemStack;
    }
    public ItemStack getEnchanted() {
        ItemStack clone = itemStack.clone();
        int randomLevel = Helper.randomInt(1, enchantment.getMaxLevel());
        clone.setData(DataComponentTypes.STORED_ENCHANTMENTS, ItemEnchantments.itemEnchantments().add(enchantment, randomLevel).build());
        return clone;
    }
}
