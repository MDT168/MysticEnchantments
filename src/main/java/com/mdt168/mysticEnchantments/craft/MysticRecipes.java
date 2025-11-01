package com.mdt168.mysticEnchantments.craft;

import com.mdt168.mysticEnchantments.craft.recipes.MysticRecipe;
import com.mdt168.mysticEnchantments.enchants.MysticEnchants;
import com.mdt168.mysticEnchantments.craft.recipes.EnchantedMysticCoinRecipe;
import com.mdt168.mysticEnchantments.craft.recipes.MysticCoinRecipe;
import com.mdt168.mysticEnchantments.craft.recipes.utility.FlexEnchantment;
import com.mdt168.mysticEnchantments.custom.BookTiers;
import com.mdt168.mysticEnchantments.resources.MysticTools;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@SuppressWarnings("unused")
public class MysticRecipes {
    public static final MysticRecipe ARCANE_AMPLIFIER_RECIPE = MysticRecipe.register(
            "Arcane Amplifier", // Name In The Gui
            "Increases the enchantment level by +1 on an item", // Recipe Lore In The Gui
            10, // Mystic Levels Requirement
            Material.AMETHYST_SHARD, // The Display Material In the Gui
            MysticItems.ARCANE_AMPLIFIER, // The Result after crafting
            List.of( // Required Items
                    new ItemStack(Material.DIAMOND, 32),
                    new ItemStack(Material.LAPIS_BLOCK, 16),
                    new ItemStack(Material.BOOK, 1)
            ),
            true
    );
    public static final MysticRecipe ENCHANTMENT_EXTRACTOR_RECIPE = MysticRecipe.register(
            "Enchantment Extractor",
            "Extracts a random enchantment from an item",
            12,
            Material.BOOK,
            MysticItems.ENCHANTMENT_EXTRACTOR,
            List.of(
                    new ItemStack(Material.GOLD_BLOCK, 8),
                    new ItemStack(Material.LAPIS_BLOCK, 10),
                    new ItemStack(Material.ECHO_SHARD, 2)
            ),
            true
    );
    public static final MysticRecipe MYSTICAL_ENCHANTMENTS_BAG_RECIPE = MysticRecipe.register(
            "Mystical Enchantments Bag",
            "Stores Enchantments inside it",
            3,
            MysticItems.MYSTICAL_ENCHANTMENTS_BAG,
            MysticItems.MYSTICAL_ENCHANTMENTS_BAG,
            List.of(
                    new ItemStack(Material.LEATHER, 32),
                    new ItemStack(Material.BOOK, 8),
                    new ItemStack(Material.CHEST, 2)
            ),
            true
    );
    public static final MysticRecipe FARMER_SATCHEL_RECIPE = MysticRecipe.register(
            "Farmer's Satchel",
            "Stores Crops inside it",
            3,
            MysticItems.FARMER_SATCHEL,
            MysticItems.FARMER_SATCHEL,
            List.of(
                    new ItemStack(Material.LEATHER, 32),
                    new ItemStack(Material.WHEAT, 32),
                    new ItemStack(Material.CHEST, 2)
            ),
            true
    );
    public static final MysticRecipe POTION_OF_ENLIGHTENED_EFFORT_RECIPE = MysticRecipe.register(
            "Potion of Enlightened Effort",
            "1.5x Effort Points Potion (8:00 - can be stacked)",
            2,
            MysticItems.POTION_OF_ENLIGHTENED_EFFORT,
            MysticItems.POTION_OF_ENLIGHTENED_EFFORT,
            List.of(
                    new ItemStack(Material.GLASS_BOTTLE, 2),
                    new ItemStack(Material.OAK_LOG, 96),
                    new ItemStack(Material.COBBLESTONE, 64),
                    new ItemStack(Material.RAW_IRON, 10)
            ),
            true
    );
    public static final MysticRecipe PORTABLE_REPAIRER_RECIPE = MysticRecipe.register(
            "Portable Repairer",
            "Repairs the other held item in your hands By Right-Clicking (10 Uses)",
            8,
            MysticItems.PORTABLE_REPAIRER,
            MysticItems.PORTABLE_REPAIRER,
            List.of(
                    new ItemStack(Material.IRON_INGOT, 20),
                    new ItemStack(Material.DIAMOND, 3),
                    new ItemStack(Material.OAK_LOG, 32),
                    new ItemStack(Material.AMETHYST_SHARD, 7)
            ),
            true
    );
    public static final MysticRecipe TOME_OF_MYSTICAL_LEVELING = MysticRecipe.register(
            "Tome Of Mystical Leveling",
            "Instantly Level up your Mystic Level (And Earn the rewards)",
            9,
            MysticItems.TOME_OF_MYSTICAL_LEVELING,
            MysticItems.TOME_OF_MYSTICAL_LEVELING,
            List.of(
                    new ItemStack(Material.WRITABLE_BOOK, 1),
                    new ItemStack(Material.AMETHYST_SHARD, 8),
                    new ItemStack(Material.DIRT, 16),
                    new ItemStack(Material.NETHERRACK, 16),
                    new ItemStack(Material.END_STONE, 16),
                    new ItemStack(Material.ECHO_SHARD, 1)
            ),
            true
    );
    public static final MysticRecipe SCROLL_OF_PRESERVATION_RECIPE = MysticRecipe.register(
            "Scroll of Preservation",
            "Preserve an item from breaking",
            11,
            MysticItems.SCROLL_OF_PRESERVATION,
            MysticItems.SCROLL_OF_PRESERVATION,
            List.of(
                    new ItemStack(Material.PAPER, 32),
                    new ItemStack(Material.DIAMOND, 8),
                    new ItemStack(Material.AMETHYST_SHARD, 8),
                    new ItemStack(Material.IRON_INGOT, 24)
            ),
            true
    );
    public static final MysticRecipe TIER_I_ENCHANTMENTS_BOX_RECIPE = MysticRecipe.register(
            "Tier I Enchantments Box",
            "Open to earn a random Mystic Enchantment",
            0,
            MysticItems.TIER_I_ENCHANTMENTS_BOX,
            MysticItems.TIER_I_ENCHANTMENTS_BOX,
            List.of(
                    new ItemStack(Material.ENCHANTED_BOOK, 2),
                    new ItemStack(Material.COPPER_INGOT, 32),
                    new ItemStack(Material.AMETHYST_SHARD, 3),
                    new ItemStack(Material.DIRT, 24)
            ),
            true
    );
    public static final MysticRecipe TIER_II_ENCHANTMENTS_BOX_RECIPE = MysticRecipe.register(
            "Tier II Enchantments Box",
            "Open to earn a random Mystic Enchantment",
            11,
            MysticItems.TIER_II_ENCHANTMENTS_BOX,
            MysticItems.TIER_II_ENCHANTMENTS_BOX,
            List.of(
                    new ItemStack(Material.ENCHANTED_BOOK, 3),
                    new ItemStack(Material.GOLD_INGOT, 30),
                    new ItemStack(Material.AMETHYST_SHARD, 6),
                    new ItemStack(Material.STONE, 36)
            ),
            true
    );
    public static final MysticRecipe TIER_III_ENCHANTMENTS_BOX_RECIPE = MysticRecipe.register(
            "Tier III Enchantments Box",
            "Open to earn a random Mystic Enchantment",
            17,
            MysticItems.TIER_III_ENCHANTMENTS_BOX,
            MysticItems.TIER_III_ENCHANTMENTS_BOX,
            List.of(
                    new ItemStack(Material.ENCHANTED_BOOK, 4),
                    new ItemStack(Material.DIAMOND, 24),
                    new ItemStack(Material.AMETHYST_SHARD, 10),
                    new ItemStack(Material.BLACKSTONE, 32)
            ),
            true
    );
    public static final MysticRecipe TIER_IV_ENCHANTMENTS_BOX_RECIPE = MysticRecipe.register(
            "Tier IV Enchantments Box",
            "Open to earn a random Mystic Enchantment",
            25,
            MysticItems.TIER_IV_ENCHANTMENTS_BOX,
            MysticItems.TIER_IV_ENCHANTMENTS_BOX,
            List.of(
                    new ItemStack(Material.ENCHANTED_BOOK, 6),
                    new ItemStack(Material.NETHERITE_INGOT, 12),
                    new ItemStack(Material.AMETHYST_SHARD, 16),
                    new ItemStack(Material.END_STONE, 48)
            ),
            true
    );
    public static final MysticRecipe CORPSE_SINGULARITY_RECIPE = EnchantedMysticCoinRecipe.register(
            BookTiers.ELDRITCH.getColor() + "Eldritch Tier - Corpse Singularity</gradient>", // Name in the Gui
            "Shift-Right click for enchantment details", // Description
            60, // Level requirement
            MysticItems.CORPSE_SINGULARITY, // Display
            MysticItems.CORPSE_SINGULARITY, // Result
            List.of( // Requirements
                    new ItemStack(Material.ENCHANTED_BOOK, 6),
                    new ItemStack(Material.NETHERITE_INGOT, 16),
                    new ItemStack(Material.DIAMOND, 48),
                    new ItemStack(Material.SCULK_CATALYST, 2)
            ),
            500, // Mystic Coins Price
            new FlexEnchantment(MysticEnchants.CORPSE_SINGULARITY, MysticItems.CORPSE_SINGULARITY), // FlexEnchantment instance for the random leveling,
            true
    );
    public static final MysticRecipe ECLIPSE_EDGE_RECIPE = EnchantedMysticCoinRecipe.register(
            BookTiers.ELDRITCH.getColor() + "Eldritch Tier - Eclipse Edge</gradient>",
            "Shift-Right click for enchantment details",
            70,
            MysticItems.ECLIPSE_EDGE,
            MysticItems.ECLIPSE_EDGE,
            List.of(
                    new ItemStack(Material.ENCHANTED_BOOK, 6),
                    new ItemStack(Material.NETHERITE_INGOT, 20),
                    new ItemStack(Material.DIAMOND, 52),
                    new ItemStack(Material.SCULK_CATALYST, 2)
            ),
            600,
            new FlexEnchantment(MysticEnchants.ECLIPSE_EDGE, MysticItems.ECLIPSE_EDGE),
            true
    );
    public static final MysticRecipe ELDRITCH_KEEN_EYE_RECIPE = MysticCoinRecipe.register(
            BookTiers.ELDRITCH.getColor() + "Eldritch Tier - Eldritch Keen Eye</gradient>",
            "Shift-Right click for enchantment details",
            80,
            MysticItems.ELDRITCH_KEEN_EYE,
            MysticItems.ELDRITCH_KEEN_EYE,
            List.of(
                    new ItemStack(Material.ENCHANTED_BOOK, 7),
                    new ItemStack(Material.NETHERITE_INGOT, 24),
                    new ItemStack(Material.DIAMOND, 64),
                    new ItemStack(Material.SCULK_CATALYST, 3)
            ),
            800,
            true
    );
    public static final MysticRecipe FINAL_RECKONING_RECIPE = MysticCoinRecipe.register(
            BookTiers.ELDRITCH.getColor() + "Eldritch Tier - Final Reckoning</gradient>",
            "Shift-Right click for enchantment details",
            60,
            MysticItems.FINAL_RECKONING,
            MysticItems.FINAL_RECKONING,
            List.of(
                    new ItemStack(Material.ENCHANTED_BOOK, 3),
                    new ItemStack(Material.NETHERITE_INGOT, 4),
                    new ItemStack(Material.DIAMOND, 24),
                    new ItemStack(Material.TNT, 16)
            ),
            600,
            true
    );
    public static final MysticRecipe UPGRADE_CRYSTAL_RECIPE = MysticCoinRecipe.register(
            "Upgrade Crystal",
            "Right-click an ore to upgrade it to the block version",
            12,
            MysticItems.UPGRADE_CRYSTAL,
            MysticItems.UPGRADE_CRYSTAL,
            List.of(
                    new ItemStack(Material.AMETHYST_SHARD, 8),
                    new ItemStack(Material.BOOK, 8),
                    new ItemStack(Material.STONE_PICKAXE, 2)
            ),
            20,
            true
    );

    public static final MysticRecipe LIGHTNING_WAND_RECIPE = MysticCoinRecipe.register(
            "Lightning Wand",
            "Strikes targets with lightning",
            50,
            MysticItems.LIGHTNING_WAND,
            MysticItems.LIGHTNING_WAND,
            List.of(
                    new ItemStack(Material.STICK, 32),
                    new ItemStack(Material.GOLDEN_APPLE, 6),
                    new ItemStack(Material.ENCHANTED_BOOK, 3),
                    new ItemStack(Material.SKULL_POTTERY_SHERD, 1),
                    new ItemStack(Material.LIGHTNING_ROD, 32)
                    ),
            50,
            true
    );
    public static final MysticRecipe HEALING_WAND_WAND_RECIPE = MysticCoinRecipe.register(
            "Healing Wand", // Name in The Gui
            "Heals you or other players", // Description
            50, // Level Requirement
            MysticItems.HEALING_WAND, // Display Item
            MysticItems.HEALING_WAND, // Result Item
            List.of( // Requirements
                    new ItemStack(Material.STICK, 32),
                    new ItemStack(Material.HEART_POTTERY_SHERD, 1),
                    new ItemStack(Material.ENCHANTED_BOOK, 3),
                    new ItemStack(Material.COOKED_BEEF, 32)
            ),
            40, // Mystic Coins Price,
            true
    );
    public static final MysticRecipe SPLINTERED_PICKAXE_RECIPE = MysticRecipe.register(
            "Splintered Pickaxe", // Name in The Gui
            "Able to mine Coal and Stone for Mystic Resources", // Description
            50, // Level Requirement
            MysticTools.SPLINTERED_PICKAXE.asItemStack(), // Display Item
            MysticTools.SPLINTERED_PICKAXE.asItemStack(), // Result Item
            List.of( // Requirements
                    new ItemStack(Material.STICK, 6),
                    new ItemStack(Material.COBBLESTONE, 2),
                    new ItemStack(Material.OAK_PLANKS, 6)
            ),
            true
    );

    public static void init() {}

}
