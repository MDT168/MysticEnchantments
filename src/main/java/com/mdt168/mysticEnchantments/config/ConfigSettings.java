package com.mdt168.mysticEnchantments.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigSettings {
    public static final ConfigSetting<Double> BASIC_ENCHANTMENTS_PRICE = ConfigSetting.register(
            "Basic Enchantments Price",
            50.,
            "The Mystic Coins Price of Basic Enchantments Tier (Tier 1)",
            1
    );
    public static final ConfigSetting<Double> ENHANCED_ENCHANTMENTS_PRICE = ConfigSetting.register(
            "Enhanced Enchantments Price",
            120.,
            "The Mystic Coins Price of Enhanced Enchantments Tier (Tier 2)",
            1
    );
    public static final ConfigSetting<Double> MYSTIC_INSIGHT_PERK_PRICE = ConfigSetting.register(
            "Mystic Insight Perk Price",
            40.,
            "The Price of Mystic Insight Buff in Mystic Options",
            0
    );
    public static final ConfigSetting<Double> REFINED_ENCHANTMENTS_PRICE = ConfigSetting.register(
            "Refined Enchantments Price",
            215.,
            "The Mystic Coins Price of Refined Enchantments Tier (Tier 3)",
            1
    );
    public static final ConfigSetting<Double> ELITE_ENCHANTMENTS_PRICE = ConfigSetting.register(
            "Elite Enchantments Price",
            365.,
            "The Mystic Coins Price of Elite Enchantments Tier (Tier 4)",
            1
    );
    public static final ConfigSetting<Double> MYTHIC_ENCHANTMENTS_PRICE = ConfigSetting.register(
            "Mythic Enchantments Price",
            500.,
            "The Mystic Coins Price of Mythic Enchantments Tier (Tier 5)",
            1
    );
    public static final ConfigSetting<Boolean> BASIC_ENCHANTMENTS_ACCESS = ConfigSetting.register(
            "Basic Enchantments Access",
            true,
            "Basic Tier accessible through the GUI"
    );
    public static final ConfigSetting<Boolean> ENHANCED_ENCHANTMENTS_ACCESS = ConfigSetting.register(
            "Enhanced Enchantments Access",
            true,
            "Enhanced Tier accessible through the GUI"
    );
    public static final ConfigSetting<Boolean> REFINED_ENCHANTMENTS_ACCESS = ConfigSetting.register(
            "Refined Enchantments Access",
            true,
            "Refined Tier accessible through the GUI"
    );
    public static final ConfigSetting<Boolean> ELITE_ENCHANTMENTS_ACCESS = ConfigSetting.register(
            "Elite Enchantments Access",
            true,
            "Elite Tier accessible through the GUI"
    );

    public static final ConfigSetting<Boolean> MYTHIC_ENCHANTMENTS_ACCESS = ConfigSetting.register(
            "Mythic Enchantments Access",
            true,
            "Mythic Tier accessible through the GUI"
    );

    public static final ConfigSetting<Boolean> API_MODE = ConfigSetting.register(
            "Api Mode",
            false,
            "All Registrations flagged with 'Api Mode' will not be registered if this is set to true, all Mystic Enchantments content is flagged with 'Api Mode'\n# Best used if you want to make your own registrations through add-ons\n# All Mystic Enchantments Content will be removed"
    );

    public static final ConfigSetting<List<String>> BLOCKED_ENCHANTMENTS = ConfigSetting.register(
            "Blocked Enchantments",
            new ArrayList<>(),
            "The Enchantments ids or names listed will be blocked from registration"
    );

    public static final ConfigSetting<Double> PURITY_CRYSTAL_PRICE = ConfigSetting.register(
            "Purity Crystal Price",
            400.,
            "The Mystic Coins Price Of Purity Crystal In Mystic Options",
            1
    );
    public static final ConfigSetting<Double> ANVIL_PRICE = ConfigSetting.register(
            "Anvil Price",
            2.,
            "The Mystic Coins Price Of The Anvil In Mystic Options",
            1
    );
    public static final ConfigSetting<Double> NEEDED_EFFORT_POINTS = ConfigSetting.register(
            "Needed Effort Points",
            600.,
            "The amount of Effort points the player should get to level up",
            100
    );
    public static final ConfigSetting<Boolean> ENABLE_MYSTIC_RECIPES = ConfigSetting.register(
            "Enable Mystic Recipes",
            true,
            "Show Mystic Recipes in the Mystic Enchanter"
    );
    public static final ConfigSetting<Boolean> ENABLE_MYSTIC_CRATE = ConfigSetting.register(
            "Enable Mystic Crate",
            true,
            "Show Mystic Crate in the Mystic Enchanter"
    );
    public static final ConfigSetting<Boolean> ENABLE_MYSTIC_RESOURCES = ConfigSetting.register(
            "Enable Mystic Resources",
            true,
            "If true, Mystic Resources will be able to be collected and used with all Mystic Resources Features"
    );

    public static void init() {}
}
