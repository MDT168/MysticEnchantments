package com.mdt168.mysticEnchantments.custom.dataUtils;

import com.mdt168.mysticEnchantments.custom.BookTiers;
import com.mdt168.mysticEnchantments.enchants.EnchantmentStack;
import com.mdt168.mysticEnchantments.enchants.HumaneEnchantment;
import com.mdt168.mysticEnchantments.enchants.MysticEnchantmentComponent;
import com.sun.source.tree.BreakTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnchantmentsUtils {
    private static final Map<BookTiers, List<HumaneEnchantment>> enchants = new HashMap<>();
    private static final Map<BookTiers, List<EnchantmentStack>> stacks = new HashMap<>();
    private static final Map<BookTiers, List<MysticEnchantmentComponent>> all = new HashMap<>();

    public static void addEnchantment(HumaneEnchantment humaneEnchantment) {
        BookTiers tier = humaneEnchantment.getBookTier();
        if (enchants.containsKey(tier))
            enchants.get(tier).add(humaneEnchantment);
        else {
            List<HumaneEnchantment> list = new ArrayList<>();
            list.add(humaneEnchantment);
            enchants.put(tier, list);
        }
        if (all.containsKey(tier))
            all.get(tier).add(humaneEnchantment);
        else {
            List<MysticEnchantmentComponent> list = new ArrayList<>();
            list.add(humaneEnchantment);
            all.put(tier, list);
        }
    }

    public static void addEnchantment(EnchantmentStack enchantmentStack) {
        BookTiers tier = enchantmentStack.getBookTier();
        if (stacks.containsKey(tier))
            stacks.get(tier).add(enchantmentStack);
        else {
            List<EnchantmentStack> list = new ArrayList<>();
            list.add(enchantmentStack);
            stacks.put(tier, list);
        }
        if (all.containsKey(tier))
            all.get(tier).add(enchantmentStack);
        else {
            List<MysticEnchantmentComponent> list = new ArrayList<>();
            list.add(enchantmentStack);
            all.put(tier, list);
        }
    }

    public static List<HumaneEnchantment> getHumaneEnchantments(BookTiers bookTier) {
        return enchants.containsKey(bookTier) ? new ArrayList<>(enchants.get(bookTier)) : new ArrayList<>();
    }
    public static List<EnchantmentStack> getEnchantments(BookTiers bookTier) {
        return stacks.containsKey(bookTier) ? new ArrayList<>(stacks.get(bookTier)) : new ArrayList<>();
    }
    public static List<MysticEnchantmentComponent> getAllEnchantments(BookTiers tier) {
        return all.containsKey(tier) ? new ArrayList<>(all.get(tier)) : new ArrayList<>();
    }
}

