package com.mdt168.mysticEnchantments.custom;

import com.mdt168.mysticEnchantments.MysticEnchantments;
import com.mdt168.mysticEnchantments.config.ConfigSetting;
import com.mdt168.mysticEnchantments.enchants.EnchantmentStack;
import com.mdt168.mysticEnchantments.enchants.HumaneEnchantment;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import static com.mdt168.mysticEnchantments.config.ConfigSettings.*;
public enum BookTiers {
    BASIC("<white>", BASIC_ENCHANTMENTS_PRICE),
    ENHANCED("<#1bff0a>", ENHANCED_ENCHANTMENTS_PRICE),
    REFINED("<#0aefff>", REFINED_ENCHANTMENTS_PRICE),
    ELITE("<#e20aff>", ELITE_ENCHANTMENTS_PRICE),
    MYTHIC("<#ff0a0a>", MYTHIC_ENCHANTMENTS_PRICE),
    ELDRITCH("<gradient:#4B0082:#8A2BE2:#00CED1>");
    private final String hex;
    private final ConfigSetting<Double> configPrice;
    BookTiers(String hex) {
        this.hex = hex;
        configPrice = null;
    }
    public Material getMaterial() {
        return switch (this) {
            case MYTHIC -> Material.RED_STAINED_GLASS_PANE;
            case ENHANCED -> Material.GREEN_STAINED_GLASS_PANE;
            case REFINED -> Material.BLUE_STAINED_GLASS_PANE;
            case ELITE -> Material.PURPLE_STAINED_GLASS_PANE;
            default -> Material.GRAY_STAINED_GLASS_PANE;
        };
    }
    BookTiers(String hex, ConfigSetting<Double> configPrice) {
        this.hex = hex;
        this.configPrice = configPrice;
    }

    public static BookTiers fromName(String name) {
        return switch (name.toLowerCase().trim()) {
            case "enhanced" -> ENHANCED;
            case "refined" -> REFINED;
            case "elite" -> ELITE;
            case "mythic" -> MYTHIC;
            default -> BASIC;
        };
    }

    public List<EnchantmentStack> getEnchants() {
        if (this == BASIC) return GuiHelper.getBasicEnchantments();
        else if (this == ENHANCED) return GuiHelper.getEnhancedEnchantments();
        else if (this == REFINED) return GuiHelper.getRefinedEnchantments();
        else if (this == ELITE) return GuiHelper.getEliteEnchantments();
        else return GuiHelper.getMythicEnchantments();
    }

    public List<Object> getAllEnchantments() {
        List<Object> enchantments = new ArrayList<>();
        enchantments.addAll(getEnchants());
        enchantments.addAll(getHumaneEnchants());
        return enchantments;
    }

    public List<HumaneEnchantment> getHumaneEnchants() {
        if (this == BASIC) return GuiHelper.getHumanBasicEnchantments();
        else if (this == ENHANCED) return GuiHelper.getHumanEnhancedEnchantments();
        else if (this == REFINED) return GuiHelper.getHumanRefinedEnchantments();
        else if (this == ELITE) return GuiHelper.getHumanEliteEnchantments();
        else return GuiHelper.getHumanMythicEnchantments();
    }
    public double getPrice() {
        if (configPrice == null) return -1;
        return configPrice.getValue();
    }

    @Override
    public String toString() {
        if (this == BASIC) return hex + "Basic";
        else if (this == ENHANCED) return hex + "Enhanced";
        else if (this == REFINED) return hex + "Refined";
        else if (this == ELITE) return hex + "Elite";
        else return hex + "Mythic";
    }

    public String getName() {
        if (this == BASIC) return "Basic";
        else if (this == ENHANCED) return "Enhanced";
        else if (this == REFINED) return "Refined";
        else if (this == ELITE) return "Elite";
        else return "Mythic";
    }

    public @Nullable String getColor() {
        return hex;
    }
}