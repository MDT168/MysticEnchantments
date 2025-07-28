package com.mdt168.mysticEnchantments.enchants;

import com.mdt168.mysticEnchantments.custom.BookTiers;
import com.mdt168.mysticEnchantments.custom.dataUtils.EnchantmentsUtils;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import net.kyori.adventure.key.Key;
import org.bukkit.enchantments.Enchantment;

public class EnchantmentStack implements MysticEnchantmentComponent {
    private final Enchantment enchantment;
    private final double chancePerLevel;
    private final String name, typeOfEnchantment, description, id;
    private final BookTiers bookTier;
    private EnchantmentStack(String name, String typeOfEnchantment, String description,double chancePerLevel, BookTiers bookTier) {
        this.id = name.toLowerCase().replace(" ", "_").replace("'", "");
        enchantment = RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT).get(Key.key("mysticenchantments:" + id));
        this.name = name;
        this.description = description;
        this.typeOfEnchantment = typeOfEnchantment;
        this.bookTier = bookTier;
        this.chancePerLevel = chancePerLevel;
    }
    public static EnchantmentStack register(String name, String typeOfEnchantment, String description,double chancePerLevel, BookTiers bookTier) {
        EnchantmentStack stack = new EnchantmentStack(name, typeOfEnchantment, description, chancePerLevel, bookTier);
        if (!BlockedUtils.isBlocked(stack)) EnchantmentsUtils.addEnchantment(stack);
        return stack;
    }

    public String getId() {
        return id;
    }

    public double getChancePerLevel() {
        return chancePerLevel;
    }

    public double getRollChance() {
        return Math.round((100.0 / (bookTier.getEnchants().size() + bookTier.getHumaneEnchants().size())) * 100.0) / 100.0;
    }

    public BookTiers getBookTier() {
        return bookTier;
    }

    public Enchantment getEnchantment() {
        return enchantment;
    }

    public String getName() {
        return name;
    }

    public String getTypeOfEnchantment() {
        return typeOfEnchantment;
    }

    public String getDescription() {
        return description;
    }
}
