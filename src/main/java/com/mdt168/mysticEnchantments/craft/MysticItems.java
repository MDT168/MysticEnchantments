package com.mdt168.mysticEnchantments.craft;

import com.mdt168.mysticEnchantments.craft.builders.ItemStackBuilder;
import com.mdt168.mysticEnchantments.enchants.MysticEnchants;
import com.mdt168.mysticEnchantments.custom.BookTiers;
import com.mdt168.mysticEnchantments.custom.Gradient;
import io.papermc.paper.datacomponent.DataComponentTypes;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MysticItems {
    private static final MiniMessage mm = MiniMessage.miniMessage();
    public static final ItemStack ARCANE_AMPLIFIER = ItemStackBuilder.of(Material.AMETHYST_SHARD)
            .setLore(List.of(
                    mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>"),
                    mm.deserialize("<#FFACFC><!i>Increases enchantment level by +1</#FFACFC>"),
                    mm.deserialize("<gray><!i>Cannot exceed an enchant's max level</gray>"),
                    mm.deserialize(""),
                    mm.deserialize("<!i><#80D8FF>\"Infused with unstable magical resonance\"</#80D8FF>"),
                    mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>"),
                    mm.deserialize("<!i><yellow>⟶ Combine in an anvil</yellow>")
            ))
            .setDisplayName(mm.deserialize(
                    "<!i><gradient:#5e00ff:#a100f2><bold>⟡ Arcane Amplifier ⟡</bold></gradient>"
            ))
            .setId("arcane_amplifier")
            .build();
    public static final ItemStack HUMANE_VIEWER = ItemStackBuilder.of(Material.BLACK_BANNER)
            .setLore(List.of(
                    mm.deserialize("<!i><yellow>- View the humane enchantments that you are enchanted with"),
                    mm.deserialize("<!i><green>- Click to view")
            ))
            .setDisplayName(Gradient.BLUE + "<!i>View Humane Enchantments")
            .setId("humane_viewer")
            .build();
    public static final ItemStack ADDONS = ItemStackBuilder.of(Material.GREEN_BANNER)
            .setLore(List.of(
                    mm.deserialize("<!i>" + Gradient.YELLOW + "• Learn how to create Add-ons for Mystic Enchantments"),
                    mm.deserialize("<!i>" + Gradient.YELLOW + "• Make your own custom enchantments, humane enchantments, "),
                    mm.deserialize("<!i>" + Gradient.YELLOW + "  recipes, and Options!")
            ))
            .setDisplayName(Gradient.BLUE + "<!i>Add-ons")
            .setId("addons")
            .build();
    public static final ItemStack ENCHANTMENT_EXTRACTOR = ItemStackBuilder.of(Material.BOOK)
            .setDisplayName(Gradient.PINK + "Enchantment Extractor")
            .setLore(List.of(
                    mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>"),
                    mm.deserialize("<#FFACFC><!i>Extracts a random enchantment from an item</#FFACFC>"),
                    mm.deserialize(""),
                    mm.deserialize("<!i><#80D8FF>\"Infused with magic that steals out enchantments\"</#80D8FF>"),
                    mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>"),
                    mm.deserialize("<!i><yellow>⟶ Combine in an anvil</yellow>")
            ))
            .setId("enchantment_extractor")
            .build();
    public static final ItemStack MYSTICAL_ENCHANTMENTS_BAG = ItemStackBuilder.of(Material.PLAYER_HEAD)
            .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2RjY2U4NzhjMmYxMWUxOWNjYjdkODI3ZDA0MGE0OTgxNzkyZTNmMzFiNTNjNDNmZDc2ODYwNWRjY2QxMzJkNyJ9fX0=")
            .setMaxStackSize(1)
            .unsetData(DataComponentTypes.EQUIPPABLE)
            .setDisplayName(Gradient.PINK + "Mystical Enchantments Bag")
            .setLore(List.of(
                    mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>"),
                    mm.deserialize("<#FFACFC><!i>Stores enchantments</#FFACFC>"),
                    mm.deserialize(""),
                    mm.deserialize("<!i><#80D8FF>Right-Click to Open</#80D8FF>"),
                    mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>")
            ))
            .setId("mystical_enchantments_bag")
            .build();
    public static final ItemStack FARMER_SATCHEL = ItemStackBuilder.of(Material.PLAYER_HEAD)
            .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDJkMjUzZmMwYmVhYzNiYTdmM2NmNTllNmNkZWY2MTM3YmRjYjEyMTIzMzlhMjg4MGE1ODMxZDFjMGMyYmE1ZSJ9fX0=")
            .setMaxStackSize(1)
            .unsetData(DataComponentTypes.EQUIPPABLE)
            .setDisplayName(Gradient.PINK + "Farmer's Satchel")
            .setLore(List.of(
                    mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>"),
                    mm.deserialize("<#FFACFC><!i>Stores Crops</#FFACFC>"),
                    mm.deserialize(""),
                    mm.deserialize("<!i><#80D8FF>Right-Click to Open</#80D8FF>"),
                    mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>")
            ))
            .setId("farmer_satchel")
            .build();
    public static final ItemStack POTION_OF_ENLIGHTENED_EFFORT = ItemStackBuilder.of(Material.PLAYER_HEAD)
            .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWZlZTViN2VjZmUyNDUyNWRkNjMyODdiZTAwOTg5ODkzNWZjODRhYTk5ZjQyZGEzZjBkMDM2ODFiMGQ1ZTE2MCJ9fX0=")
            .setDisplayName(Gradient.AQUA + "Potion of Enlightened Effort")
            .unsetData(DataComponentTypes.EQUIPPABLE)
            .setLore(List.of(
                    mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>"),
                    mm.deserialize("<#FFACFC><!i>| Drinkable Potion</#FFACFC>"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Increases 1.5x of the obtained Effort Points for 8 minutes"),
                    Component.empty(),
                    mm.deserialize("<!i><#80D8FF>Right-Click to Drink</#80D8FF>"),
                    mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>")
            ))
            .setId("Potion of Enlightened Effort")
            .build();
    public static final ItemStack PORTABLE_REPAIRER = ItemStackBuilder.of(Material.PLAYER_HEAD)
            .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWI0MjVhYTNkOTQ2MThhODdkYWM5Yzk0ZjM3N2FmNmNhNDk4NGMwNzU3OTY3NGZhZDkxN2Y2MDJiN2JmMjM1In19fQ==")
            .setDisplayName(Gradient.AQUA + "Portable Repairer")
            .setMaxStackSize(1)
            .setDurability(10)
            .unsetData(DataComponentTypes.EQUIPPABLE)
            .setLore(List.of(
                    mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>"),
                    mm.deserialize("<#FFACFC><!i>| Interactable Item</#FFACFC>"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Hold a damaged item in the other hand to fully repair it"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Right-Click to use"),
                    mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>")
            ))
            .setId("portable_repairer")
            .build();
    public static final ItemStack TOME_OF_MYSTICAL_LEVELING = ItemStackBuilder.of(Material.PLAYER_HEAD)
            .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjA1Y2Q2NzIyMWZhYTRiOTFmNjg0NzE1NzM5ODc4N2YxYjAwMzY1YmEyYjRhN2E2MmVhMmY2MzI3MDQwMTQ4OSJ9fX0=")
            .setDisplayName(Gradient.AQUA + "Tome of Mystical Leveling")
            .unsetData(DataComponentTypes.EQUIPPABLE)
            .setLore(List.of(
                    mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>"),
                    mm.deserialize("<#FFACFC><!i>| Interactable Item</#FFACFC>"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Instantly Level up your Mystic Level"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Earn the rewards of leveling up (Booster + Mystic Coins)"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Right-Click to use"),
                    mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>")
            ))
            .setId("Tome of Mystical Leveling")
            .build();
    public static final ItemStack SCROLL_OF_PRESERVATION = ItemStackBuilder.of(Material.PAPER)
            .setDisplayName(Gradient.AQUA + "Scroll of Preservation")
            .setData(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)
            .setLore(List.of(
                    mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>"),
                    mm.deserialize("<#FFACFC><!i>| Interactable Item</#FFACFC>"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Hold another item in your hands to preserve the item from breaking"),
                    mm.deserialize(Gradient.BLUE + "<!i>• On Item Break, the item will be fully repaired"),
                    mm.deserialize(Gradient.BLUE + "<!i>• One time use on an item"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Right-Click to use"),
                    mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>")
            ))
            .setId("Scroll of Preservation")
            .build();
    public static final ItemStack LIGHTNING_WAND = ItemStackBuilder.of(Material.STICK)
            .setDisplayName(Gradient.AQUA + "Lightning Wand")
            .setData(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)
            .setLore(List.of(
                    mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>"),
                    mm.deserialize("<#FFACFC><!i>| Weapon</#FFACFC>"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Strikes targeted enemies with lightning"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Damage: <green>5 hearts"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Burn Time: <green>5 seconds"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Range: <green>20 blocks"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Uses Limit: <green>30 uses"),
                    mm.deserialize(Gradient.BLUE + "<!i>• 5 seconds cooldown"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Left-Click to Attack"),
                    mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>")
            ))
            .setMaxStackSize(1)
            .setDurability(30)
            .setId("lightning_wand")
            .build();
    public static final ItemStack HEALING_WAND = ItemStackBuilder.of(Material.STICK)
            .setDisplayName(Gradient.AQUA + "Healing Wand")
            .setData(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)
            .setLore(List.of(
                    mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>"),
                    mm.deserialize("<#FFACFC><!i>| Support Wand</#FFACFC>"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Left-Click To use on a player while looking at them"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Right-Click to use on yourself"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Benefits:"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Heals: <red>2 hearts"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Gives: <red>Regeneration II (0:10)"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Uses Limit: <green>20 uses"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Range: <green>12 Blocks"),
                    mm.deserialize(Gradient.BLUE + "<!i>• 30 seconds cooldown"),
                    mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>")
            ))
            .setMaxStackSize(1)
            .setDurability(20)
            .setId("healing_wand")
            .build();
    public static final ItemStack TIER_I_ENCHANTMENTS_BOX = ItemStackBuilder.of(Material.PLAYER_HEAD)
            .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWYxMmRkZWQ2MmU5MGZhMTZlZGQ0MDFjYmM3NjgyY2JiOGRhMzYyYmY4ZTc5OWMwNDJlNmZjZmVjNzkxOTE4ZiJ9fX0=")
            .setDisplayName(Gradient.AQUA + "Tier I Enchantments Box")
            .unsetData(DataComponentTypes.EQUIPPABLE)
            .setLore(List.of(
                    mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>"),
                    mm.deserialize("<#FFACFC><!i>| Interactable Item</#FFACFC>"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Get a random Mystic Enchantment"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Basic: " + Gradient.GREEN + "60%"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Enhanced: " + Gradient.GREEN + "35%"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Refined: " + Gradient.GREEN + "5%"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Right-Click to open"),
                    mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>")
            ))
            .setId("tier1_box")
            .build();
    public static final ItemStack TIER_II_ENCHANTMENTS_BOX = ItemStackBuilder.of(Material.PLAYER_HEAD)
            .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2JiM2M4MDJjYTkwMjg4ZTQ4YjAxMGFkZGU4OTdlOGVlYTE1MTBkYmJmZWE0NWI3MGJlMjA4OTMzYzA2MGI2ZCJ9fX0=")
            .setDisplayName(Gradient.AQUA + "Tier II Enchantments Box")
            .unsetData(DataComponentTypes.EQUIPPABLE)
            .setLore(List.of(
                    mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>"),
                    mm.deserialize("<#FFACFC><!i>| Interactable Item</#FFACFC>"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Get a random Mystic Enchantment"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Basic: " + Gradient.GREEN + "35%"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Enhanced: " + Gradient.GREEN + "40%"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Refined: " + Gradient.GREEN + "20%"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Elite: " + Gradient.GREEN + "5%"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Right-Click to open"),
                    mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>")
            ))
            .setId("tier2_box")
            .build();
    public static final ItemStack TIER_III_ENCHANTMENTS_BOX = ItemStackBuilder.of(Material.PLAYER_HEAD)
            .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTkwNWVkOGRhZjQ3ZGUxNWJiM2I1ZmQ0MjAxYzkxN2UxMzhkYjRlZDYxYmU4NGQ1OTU2YmM1ZWYwNmFjYjI0MiJ9fX0=")
            .setDisplayName(Gradient.AQUA + "Tier III Enchantments Box")
            .unsetData(DataComponentTypes.EQUIPPABLE)
            .setLore(List.of(
                    mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>"),
                    mm.deserialize("<#FFACFC><!i>| Interactable Item</#FFACFC>"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Get a random Mystic Enchantment"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Basic: " + Gradient.GREEN + "20%"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Enhanced: " + Gradient.GREEN + "35%"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Refined: " + Gradient.GREEN + "30%"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Elite: " + Gradient.GREEN + "10%"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Mythic: " + Gradient.GREEN + "5%"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Right-Click to open"),
                    mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>")
            ))
            .setId("tier3_box")
            .build();
    public static final ItemStack TIER_IV_ENCHANTMENTS_BOX = ItemStackBuilder.of(Material.PLAYER_HEAD)
            .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmYwYzc3YzE5MmVkZTJkNGRjMjQ0ZTlkZWYxYzE3MmZjY2Q3MTI3YTQ1MzFjOTk1ZDc0OGI1MWRhZTM0MDMwMCJ9fX0=")
            .setDisplayName(Gradient.AQUA + "Tier IV Enchantments Box")
            .unsetData(DataComponentTypes.EQUIPPABLE)
            .setLore(List.of(
                    mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>"),
                    mm.deserialize("<#FFACFC><!i>| Interactable Item</#FFACFC>"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Get a random Mystic Enchantment"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Basic: " + Gradient.GREEN + "5%"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Enhanced: " + Gradient.GREEN + "20%"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Refined: " + Gradient.GREEN + "25%"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Elite: " + Gradient.GREEN + "30%"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Mythic: " + Gradient.GREEN + "20%"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Right-Click to open"),
                    mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>")
            ))
            .setId("tier4_box")
            .build();
    public static final ItemStack UPGRADE_CRYSTAL = ItemStackBuilder.of(Material.PLAYER_HEAD)
            .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDNhZDQxZTEyMjcwNzcyNjFlNDViMmQyOGJkZDc3NTRkMzRlMWQyMGU0OTllMWNhN2U0ZjkwMDhmMmMzOWYzNCJ9fX0=")
            .setDisplayName(Gradient.AQUA + "Upgrade Crystal")
            .unsetData(DataComponentTypes.EQUIPPABLE)
            .setLore(List.of(
                    mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>"),
                    mm.deserialize("<#FFACFC><!i>| Interactable Item</#FFACFC>"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Upgrade an ore to the block version"),
                    mm.deserialize(Gradient.BLUE + "<!i>• Right-Click on an ore to use"),
                    mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>")
            ))
            .setId("upgrade_crystal")
            .build();
    public static final ItemStack MYSTIC_LEVEL_ITEM = ItemStackBuilder.of(Material.PLAYER_HEAD)
            .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjNmZmUyNmI1N2Y2YWZkMjBhMDcxOThlYmZmMjRhMjcyYWJlNjUxOGM2NzZhZjU0YjkzMjFiMDlkMTNkNmU2MyJ9fX0=")
            .build();
    public static final ItemStack MYSTIC_COIN_ITEM = ItemStackBuilder.of(Material.PLAYER_HEAD)
            .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzFmNTFkODAxY2UzZTY2NWM3ZDBkNWMzODFhY2IxYTQzODc1MmNmNTc2ZTVlODYxMDJhNGY3Y2EzNThhNmFhZCJ9fX0=")
            .build();
    public static final ItemStack CORPSE_SINGULARITY = ItemStackBuilder.of(Material.ENCHANTED_BOOK)
            .setDisplayName(BookTiers.ELDRITCH.getColor() + "Corpse Singularity")
            .setLore(
                    List.of(
                            mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>"),
                            mm.deserialize("<#FFACFC><!i>| Exclusive Tier Enchantment</#FFACFC>"),
                            mm.deserialize(Gradient.BLUE + "<!i>• Sharp weapons enchantment"),
                            mm.deserialize(Gradient.BLUE + "<!i>• On Enemy kill, Pull nearby entities (Higher Level, Higher Range) and:"),
                            mm.deserialize(Gradient.BLUE + "<!i>• Deal massive damage, set on fire and apply nausea"),
                            mm.deserialize(Gradient.BLUE + "<!i>• Max Level: " + Gradient.GREEN + "3"),
                            mm.deserialize(Gradient.BLUE + "<!i>• Chance Per Level: " + Gradient.GREEN + "13%"),
                            mm.deserialize(Gradient.BLUE + "<!i>• Tier: " + BookTiers.ELDRITCH.getColor() + "Eldritch"),
                            mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>")
                    )
            )
            .build();
    public static final ItemStack ECLIPSE_EDGE = ItemStackBuilder.of(Material.ENCHANTED_BOOK)
            .setDisplayName(BookTiers.ELDRITCH.getColor() + "Eclipse Edge")
            .setLore(
                    List.of(
                            mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>"),
                            mm.deserialize("<#FFACFC><!i>| Exclusive Tier Enchantment</#FFACFC>"),
                            mm.deserialize(Gradient.BLUE + "<!i>• Swords enchantment"),
                            mm.deserialize(Gradient.BLUE + "<!i>• Critical Strike damage is multiplied by 2.5x while sneaking"),
                            mm.deserialize(Gradient.BLUE + "<!i>• 3s Cooldown"),
                            mm.deserialize(Gradient.BLUE + "<!i>• Tier: " + BookTiers.ELDRITCH.getColor() + "Eldritch"),
                            mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>")
                    )
            )
            .build();
    public static final ItemStack ELDRITCH_KEEN_EYE = ItemStackBuilder.of(Material.ENCHANTED_BOOK)
            .setDisplayName(BookTiers.ELDRITCH.getColor() + "Eldritch Keen Eye")
            .registerAsHumane(MysticEnchants.ELDRITCH_KEEN_EYE)
            .setLore(
                    List.of(
                            mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>"),
                            mm.deserialize("<#FFACFC><!i>| Exclusive Tier Enchantment</#FFACFC>"),
                            mm.deserialize(Gradient.BLUE + "<!i>• Humane Enchantment"),
                            mm.deserialize(Gradient.BLUE + "<!i>• Triple World-Generated Blocks Drops"),
                            mm.deserialize(Gradient.BLUE + "<!i>• {chance}% Chance On Block Break".replace("{chance}", String.valueOf(MysticEnchants.ELDRITCH_KEEN_EYE.getChance()))),
                            mm.deserialize(Gradient.BLUE + "<!i>• Tier: " + BookTiers.ELDRITCH.getColor() + "Eldritch"),
                            mm.deserialize(Gradient.BLUE + "<!i>• Right-Click to Enchant yourself"),
                            mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>")
                    )
            )
            .build();
    public static final ItemStack FINAL_RECKONING = ItemStackBuilder.of(Material.ENCHANTED_BOOK)
            .setDisplayName(BookTiers.ELDRITCH.getColor() + "Final Reckoning")
            .registerAsHumane(MysticEnchants.FINAL_RECKONING)
            .setLore(
                    List.of(
                            mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>"),
                            mm.deserialize("<#FFACFC><!i>| Exclusive Tier Enchantment</#FFACFC>"),
                            mm.deserialize(Gradient.BLUE + "<!i>• Humane Enchantment"),
                            mm.deserialize(Gradient.BLUE + "<!i>• Drops a bomb behind you on death, dealing massive damage to surroundings"),
                            mm.deserialize(Gradient.BLUE + "<!i>• One time use"),
                            mm.deserialize(Gradient.BLUE + "<!i>• Radius: 5 Blocks"),
                            mm.deserialize(Gradient.BLUE + "<!i>• Explode Time: 1 second"),
                            mm.deserialize(Gradient.BLUE + "<!i>• Tier: " + BookTiers.ELDRITCH.getColor() + "Eldritch"),
                            mm.deserialize(Gradient.BLUE + "<!i>• Right-Click to Enchant yourself"),
                            mm.deserialize("<!i><dark_purple>|</dark_purple><gradient:#c471ed:#f7797d>━━━━━━━━━━━━━━━━</gradient><dark_purple>|</dark_purple>")
                    )
            )
            .build();
}
