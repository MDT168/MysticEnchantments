package com.mdt168.mysticEnchantments.enchants;

import com.mdt168.mysticEnchantments.custom.BookTiers;
import com.mdt168.mysticEnchantments.custom.ItemDataUtils;
import com.mdt168.mysticEnchantments.custom.dataUtils.EnchantmentsUtils;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HumaneEnchantment implements MysticEnchantmentComponent {
    private final String name, description, id;
    private final BookTiers bookTier;
    private HumaneEnchantment[] conflictsWith;
    private final double chance;

    private static final Map<String, HumaneEnchantment> enchants = new HashMap<>();

    private HumaneEnchantment(String name, String description, BookTiers bookTier) {
        this.name = name;
        this.description = description;
        this.bookTier = bookTier;
        this.id = this.name.toLowerCase().replace(" ", "_").replace("'", "");
        this.chance = 100;
        this.conflictsWith = new HumaneEnchantment[]{};
    }

    private HumaneEnchantment(String name, String description, double chance, BookTiers bookTier) {
        this.name = name;
        this.description = description;
        this.bookTier = bookTier;
        this.chance = chance;
        this.id = this.name.toLowerCase().replace(" ", "_").replace("'", "");
        this.conflictsWith = new HumaneEnchantment[]{};
    }

    private HumaneEnchantment(String name, String description, BookTiers bookTier, HumaneEnchantment[] conflictsWith) {
        this.name = name;
        this.description = description;
        this.bookTier = bookTier;
        this.id = this.name.toLowerCase().replace(" ", "_").replace("'", "");
        this.chance = 100;
        this.conflictsWith = conflictsWith;
    }

    private HumaneEnchantment(String name, String description, double chance, BookTiers bookTier, HumaneEnchantment[] conflictsWith) {
        this.name = name;
        this.description = description;
        this.bookTier = bookTier;
        this.chance = chance;
        this.id = this.name.toLowerCase().replace(" ", "_").replace("'", "");
        this.conflictsWith = conflictsWith;
    }

    public static HumaneEnchantment fromItem(ItemStack itemStack) {
        return enchants.get(ItemDataUtils.getData(itemStack));
    }

    public static HumaneEnchantment register(String name, String description, BookTiers bookTier) {
        HumaneEnchantment enchant = new HumaneEnchantment(name, description, bookTier);
        if (!BlockedUtils.isBlocked(enchant)) {
            enchants.put(enchant.getId(), enchant);
            EnchantmentsUtils.addEnchantment(enchant);
        }
        return enchant;
    }

    public static HumaneEnchantment register(String name, String description, double chance, BookTiers bookTier) {
        HumaneEnchantment enchant = new HumaneEnchantment(name, description, chance, bookTier);
        if (!BlockedUtils.isBlocked(enchant)) {
            enchants.put(enchant.getId(), enchant);
            EnchantmentsUtils.addEnchantment(enchant);
        }
        return enchant;
    }

    public static HumaneEnchantment register(String name, String description, BookTiers bookTier, HumaneEnchantment[] conflictsWith) {
        HumaneEnchantment enchant = new HumaneEnchantment(name, description, bookTier, conflictsWith);
        if (!BlockedUtils.isBlocked(enchant)) {
            enchants.put(enchant.getId(), enchant);
            EnchantmentsUtils.addEnchantment(enchant);
        }
        return enchant;
    }

    public static HumaneEnchantment register(String name, String description, double chance, BookTiers bookTier, HumaneEnchantment[] conflictsWith) {
        HumaneEnchantment enchant = new HumaneEnchantment(name, description, chance, bookTier, conflictsWith);
        if (!BlockedUtils.isBlocked(enchant)) {
            enchants.put(enchant.getId(), enchant);
            EnchantmentsUtils.addEnchantment(enchant);
        }
        return enchant;
    }

    public static List<HumaneEnchantment> getRegistries() {
        return new ArrayList<>(enchants.values());
    }

    public static HumaneEnchantment fromId(String id) {
        return enchants.get(id);
    }


    public void setConflictsWith(HumaneEnchantment[] conflictsWith) {
        this.conflictsWith = conflictsWith;
    }

    public boolean conflictsWith(HumaneEnchantment humaneEnchantment) {
        if (humaneEnchantment.getId().equals(this.getId())) return false;
        for (HumaneEnchantment enchantment : conflictsWith) {
            if (enchantment.getId().equals(humaneEnchantment.getId())) return true;
        }
        return false;
    }

    public double getChance() {
        return chance;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getRollChance() {
        return Math.round((100.0 / (bookTier.getEnchants().size() + bookTier.getHumaneEnchants().size())) * 100.0) / 100.0;
    }

    public String getDescription() {
        return description;
    }

    public BookTiers getBookTier() {
        return bookTier;
    }
}
