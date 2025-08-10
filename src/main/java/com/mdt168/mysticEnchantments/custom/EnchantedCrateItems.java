package com.mdt168.mysticEnchantments.custom;

import com.mdt168.mysticEnchantments.craft.MysticItems;
import com.mdt168.mysticEnchantments.enchants.EnchantmentStack;
import com.mdt168.mysticEnchantments.enchants.HumaneEnchantment;
import io.papermc.paper.datacomponent.DataComponentTypes;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class EnchantedCrateItems {
    public static final EnchantedItem ARCANE_AMPLIFIER = EnchantedItem.register(
            "Arcane Amplifier",
            "Increases the level of an enchant on an item by 1 (Cannot go above max) - Use in an anvil",
            2,
            Material.AMETHYST_SHARD,
            player -> {
                ItemStack arcaneItem = MysticItems.ARCANE_AMPLIFIER;
                arcaneItem.setData(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);
                Helper.sendMessage(player, "Legendary! " + Gradient.RED + "You just got " + Gradient.AQUA + "Arcane Amplifier!");
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_DEATH, 1, 1);
                Helper.sendWorldMessage(Gradient.RED + player.getName() + " got a Legendary Drop from Enchanted Crate! " + Gradient.AQUA + "Arcane Amplifier");
                return Helper.safeGiveItem(player, arcaneItem);
            },
            true
    );
    public static final EnchantedItem DIAMONDS_BLOCKS = EnchantedItem.register(
            "Diamond Blocks",
            "Randomly get 6-8 diamond blocks",
            10,
            Material.DIAMOND_BLOCK,
            player -> {
                int amount = Helper.randomInt(6, 8);
                ItemStack gift = new ItemStack(Material.DIAMOND_BLOCK, amount);
                Helper.sendMessage(player, "You rolled Enchanted Crate and got <gradient:#54F494:#619859>" + amount + " Blocks Of Diamond");
                return Helper.safeGiveItem(player, gift);
            },
            true
    );
    public static final EnchantedItem EMERALD_BLOCKS = EnchantedItem.register(
            "Emerald Blocks",
            "Randomly get 10-12 emerald blocks",
            13,
            Material.EMERALD_BLOCK,
            player -> {
                int amount = Helper.randomInt(10, 12);
                ItemStack gift = new ItemStack(Material.EMERALD_BLOCK, amount);
                Helper.sendMessage(player, "You rolled Enchanted Crate and got <gradient:#54F494:#619859>" + amount + " Blocks Of Emerald");
                return Helper.safeGiveItem(player, gift);
            },
            true
    );
    public static final EnchantedItem GOLD_BLOCKS = EnchantedItem.register(
            "Gold Blocks",
            "Randomly get 12-15 diamond blocks",
            14,
            Material.GOLD_BLOCK,
            player -> {
                int amount = Helper.randomInt(12, 15);
                ItemStack gift = new ItemStack(Material.GOLD_BLOCK, amount);
                Helper.sendMessage(player, "You rolled Enchanted Crate and got <gradient:#54F494:#619859>" + amount + " Blocks Of Gold");
                return Helper.safeGiveItem(player, gift);
            },
            true
    );
    public static final EnchantedItem IRON_BLOCKS = EnchantedItem.register(
            "Iron Blocks",
            "Randomly get 13-18 iron blocks",
            10,
            Material.IRON_BLOCK,
            player -> {
                int amount = Helper.randomInt(13, 18);
                ItemStack gift = new ItemStack(Material.IRON_BLOCK, amount);
                Helper.sendMessage(player, "You rolled Enchanted Crate and got <gradient:#54F494:#619859>" + amount + " Blocks Of Iron");
                return Helper.safeGiveItem(player, gift);
            },
            true
    );
    public static final EnchantedItem GOLDEN_CARROTS = EnchantedItem.register(
            "Golden Carrots",
            "Get 32 Golden Carrots",
            15,
            Material.GOLDEN_CARROT,
            player -> {
                int amount = 32;
                ItemStack gift = new ItemStack(Material.GOLDEN_CARROT, amount);
                Helper.sendMessage(player, "You rolled Enchanted Crate and got <gradient:#54F494:#619859>" + amount + " Golden Carrots");
                return Helper.safeGiveItem(player, gift);
            },
            true
    );
    public static final EnchantedItem BASIC_ENCHANT = EnchantedItem.register(
            "Basic Enchants",
            "Get Random Max Level Basic Enchantment",
            30,
            Material.BOOK,
            player -> {
                Object chosen = Helper.rollBasicEnchantment();
                if (chosen instanceof EnchantmentStack enchantmentStack) {
                    String name = enchantmentStack.getName();
                    if (Helper.giveMaxBook(enchantmentStack, player)) {
                        Helper.sendMessage(player, "You got a basic enchantment " + Gradient.RED + name + "</gradient> as max level!");
                        return true;
                    }
                } else if (chosen instanceof HumaneEnchantment humaneEnchantment) {
                    if (Helper.giveBook(humaneEnchantment, player)) {
                        String name = humaneEnchantment.getName();
                        Helper.sendMessage(player, "You got a basic humane enchantment " + Gradient.RED + name + "</gradient>!");
                        return true;
                    }
                }
                return false;
            },
            true
    );
    public static final EnchantedItem ENHANCED_ENCHANT = EnchantedItem.register(
            "Enhanced Enchants",
            "Get Random Max Level Enhanced Enchantment",
            25,
            Material.BOOK,
            player -> {
                Object chosen = Helper.rollEnhancedEnchantment();
                if (chosen instanceof EnchantmentStack enchantmentStack) {
                    String name = enchantmentStack.getName();
                    if (Helper.giveMaxBook(enchantmentStack, player)) {
                        Helper.sendMessage(player, "You got an enhanced enchantment " + Gradient.RED + name + "</gradient> as max level!");
                        return true;
                    }
                } else if (chosen instanceof HumaneEnchantment humaneEnchantment) {
                    if (Helper.giveBook(humaneEnchantment, player)) {
                        String name = humaneEnchantment.getName();
                        Helper.sendMessage(player, "You got an enhanced humane enchantment " + Gradient.RED + name + "</gradient>!");
                        return true;
                    }
                }
                return false;
            },
            true
    );
    public static final EnchantedItem REFINED_ENCHANT = EnchantedItem.register(
            "Refined Enchants",
            "Get Random Max Level Refined Enchantment",
            20,
            Material.BOOK,
            player -> {
                Object chosen = Helper.rollRefinedEnchantment();
                if (chosen instanceof EnchantmentStack enchantmentStack) {
                    String name = enchantmentStack.getName();
                    if (Helper.giveMaxBook(enchantmentStack, player)) {
                        Helper.sendMessage(player, "You got an refined enchantment " + Gradient.RED + name + "</gradient> as max level!");
                        return true;
                    }
                } else if (chosen instanceof HumaneEnchantment humaneEnchantment) {
                    if (Helper.giveBook(humaneEnchantment, player)) {
                        String name = humaneEnchantment.getName();
                        Helper.sendMessage(player, "You got an refined humane enchantment " + Gradient.RED + name + "</gradient>!");
                        return true;
                    }
                }
                return false;
            },
            true
    );
    public static final EnchantedItem ELITE_ENCHANT = EnchantedItem.register(
            "Elite Enchants",
            "Get Random Max Level Elite Enchantment",
            15,
            Material.BOOK,
            player -> {
                Object chosen = Helper.rollEliteEnchantment();
                if (chosen instanceof EnchantmentStack enchantmentStack) {
                    String name = enchantmentStack.getName();
                    if (Helper.giveMaxBook(enchantmentStack, player)) {
                        Helper.sendMessage(player, "You got an elite enchantment " + Gradient.RED + name + "</gradient> as max level!");
                        return true;
                    }
                } else if (chosen instanceof HumaneEnchantment humaneEnchantment) {
                    if (Helper.giveBook(humaneEnchantment, player)) {
                        String name = humaneEnchantment.getName();
                        Helper.sendMessage(player, "You got an elite humane enchantment " + Gradient.RED + name + "</gradient>!");
                        return true;
                    }
                }
                return false;
            },
            true
    );
    public static final EnchantedItem MYTHIC_ENCHANT = EnchantedItem.register(
            "Mythic Enchants",
            "Get Random Max Level Mythic",
            10,
            Material.BOOK,
            player -> {
                Object chosen = Helper.rollMythicEnchantment();
                if (chosen instanceof EnchantmentStack enchantmentStack) {
                    String name = enchantmentStack.getName();
                    if (Helper.giveMaxBook(enchantmentStack, player)) {
                        Helper.sendMessage(player, "You got an mythic enchantment " + Gradient.RED + name + "</gradient> as max level!");
                        return true;
                    }
                } else if (chosen instanceof HumaneEnchantment humaneEnchantment) {
                    if (Helper.giveBook(humaneEnchantment, player)) {
                        String name = humaneEnchantment.getName();
                        Helper.sendMessage(player, "You got an mythic humane enchantment " + Gradient.RED + name + "</gradient>!");
                        return true;
                    }
                }
                return false;
            },
            true
    );

    public static final EnchantedItem NETHERITE_INGOTS = EnchantedItem.register(
            "Netherite Ingots",
            "Obtain 4 netherite ingots",
            4,
            Material.NETHERITE_INGOT,
            player -> {
                return Helper.safeGiveItem(player, new ItemStack(Material.NETHERITE_INGOT, 4));
            },
            true
    );
    public static final EnchantedItem SHARPNESS_VI = EnchantedItem.register(
            "Sharpness VI (6)",
            "Obtain Sharpness VI (6) Book",
            7,
            Material.ENCHANTED_BOOK,
            player -> {
                Helper.sendMessage(player, "You rolled the Enchanted Crate and got " + Gradient.RED + "Sharpness VI (6) Book");
                return Helper.giveBook(Enchantment.SHARPNESS, 6, player);
            },
            true
    );
    public static final EnchantedItem FORTUNE_IV = EnchantedItem.register(
            "Fortune IV (4)",
            "Obtain Fortune IV (4) Book",
            7,
            Material.ENCHANTED_BOOK,
            player -> {
                Helper.sendMessage(player, "You rolled the Enchanted Crate and got " + Gradient.RED + "Fortune IV (4) Book");
                return Helper.giveBook(Enchantment.FORTUNE, 4, player);
            },
            true
    );
    public static final EnchantedItem EFFICIENCY_VI = EnchantedItem.register(
            "Efficiency VI (6)",
            "Obtain Efficiency (6) Book",
            7,
            Material.ENCHANTED_BOOK,
            player -> {
                Helper.sendMessage(player, "You rolled the Enchanted Crate and got " + Gradient.RED + "Efficiency VI (6) Book");
                return Helper.giveBook(Enchantment.EFFICIENCY, 6, player);
            },
            true
    );
    public static final EnchantedItem UNBREAKING_IV = EnchantedItem.register(
            "Unbreaking IV (4)",
            "Obtain Unbreaking IV (4) Book",
            7,
            Material.ENCHANTED_BOOK,
            player -> {
                Helper.sendMessage(player, "You rolled the Enchanted Crate and got " + Gradient.RED + "Unbreaking IV (4) Book");
                return Helper.giveBook(Enchantment.UNBREAKING, 6, player);
            },
            true
    );
    public static final EnchantedItem MENDING = EnchantedItem.register(
            "Mending",
            "Obtain Mending I Book",
            7,
            Material.ENCHANTED_BOOK,
            player -> {
                Helper.sendMessage(player, "You rolled the Enchanted Crate and got " + Gradient.RED + "Mending I Book");
                return Helper.giveBook(Enchantment.MENDING, 1, player);
            },
            true
    );
    public static final EnchantedItem LOOTING_IV = EnchantedItem.register(
            "Looting IV (4)",
            "Obtain Looting IV (4) Book",
            7,
            Material.ENCHANTED_BOOK,
            player -> {
                Helper.sendMessage(player, "You rolled the Enchanted Crate and got " + Gradient.RED + "Looting IV (4) Book");
                return Helper.giveBook(Enchantment.LOOTING, 4, player);
            },
            true
    );
    public static final EnchantedItem XP_2500 = EnchantedItem.register(
            "2500 XP",
            "Obtain 2500 XP",
            10,
            Material.EXPERIENCE_BOTTLE,
            player -> {
                Helper.sendMessage(player, "You rolled the Enchanted Crate and got " + Gradient.RED + "2500 XP");
                Helper.giveXp(player, 2500);
                return true;
            },
            true
    );
    public static final EnchantedItem XP_5000 = EnchantedItem.register(
            "5000 XP",
            "Obtain 5000 XP",
            8,
            Material.EXPERIENCE_BOTTLE,
            player -> {
                Helper.sendMessage(player, "You rolled the Enchanted Crate and got " + Gradient.RED + "5000 XP");
                Helper.giveXp(player, 5000);
                return true;
            },
            true
    );
    public static final EnchantedItem XP_7500 = EnchantedItem.register(
            "7500 XP",
            "Obtain 7500 XP",
            5,
            Material.EXPERIENCE_BOTTLE,
            player -> {
                Helper.sendMessage(player, "You rolled the Enchanted Crate and got " + Gradient.RED + "7500 XP");
                Helper.giveXp(player, 7500);
                return true;
            },
            true
    );
    public static final EnchantedItem ENCHANTED_GOLDEN_APPLE = EnchantedItem.register(
            "Enchanted Golden Apples",
            "Obtain 1-2 Enchanted Golden Apples",
            3,
            Material.ENCHANTED_GOLDEN_APPLE,
            player -> {
                int amount = Helper.randomInt(1, 2);
                ItemStack apples = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, amount);
                String apple = amount == 1 ? "Apple" : "Apples";
                Helper.sendMessage(player, "Legendary! " + Gradient.RED + "You got " + Gradient.AQUA + amount + " Enchanted Golden " + apple + "!");
                Helper.sendWorldMessage(Gradient.RED + player.getName() + " got a Legendary Drop from Enchanted Crate! " + Gradient.AQUA + amount + " Enchanted Golden " + apple);
                return Helper.safeGiveItem(player, apples);
            },
            true
    );
    public static void init() {}
}
