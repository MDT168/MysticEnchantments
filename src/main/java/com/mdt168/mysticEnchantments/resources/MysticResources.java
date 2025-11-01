package com.mdt168.mysticEnchantments.resources;

import com.mdt168.mysticEnchantments.craft.builders.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MysticResources {
    public static final MysticResource CARBONITE = MysticResource.register(
            "Carbonite",
            "A Common Mineral found in coal using Splintered Pickaxe",
            0.2,
            ItemStackBuilder.of(Material.PLAYER_HEAD).setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmNlZTQ2NTQwZjhhOTczMmJmY2Q2MjY1ZGFjNzNiNTA5YmZlNGYwMDk1Zjk1NWQ0ODNmZGEwOTNhZmY3MWQzNSJ9fX0=").build(),
            10,
            ResourceTiers.COMMON
    );
    public static final MysticResource PHANTOMS_TEAR = MysticResource.register(
            "Phantom's Tear",
            "A tear-shaped crystal dropped by Phantoms killed by an Iron Sword",
            50,
            new ItemStack(Material.GHAST_TEAR),
            4,
            ResourceTiers.RARE
    );

    public static final MysticResource DRAGONS_HEART = MysticResource.register(
            "Dragon's Heart",
            "The pulsating core of a defeated dragon.",
            300,
            ItemStackBuilder.of(Material.PLAYER_HEAD).setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkZTZkMGQ5ZDAwNmRiZTQ0NmFiMWRkNWY4OGMzMmFhNmU0YTA1OTllMjgwNzE2OGJkYTBhODI1ZGMwYzQ5OCJ9fX0=").build(),
            10,
            ResourceTiers.MYTHIC
    );

    public static final MysticResource COPPER_SHARD = MysticResource.register(
            "Copper Shard",
            "Found while mining copper ore with a Splintered Pickaxe.",
            0.5,
            ItemStackBuilder.of(Material.RAW_COPPER).build(),
            100,
            ResourceTiers.COMMON
    );
    public static final MysticResource FROST_CRYSTAL = MysticResource.register(
            "Frost Crystal",
            "Dropped from Strays using a stone sword.",
            0.7,
            ItemStackBuilder.of(Material.PLAYER_HEAD).setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzhlMmZhZjI1ZmI0MDAxYjRlZDQyYzhiNDUwOGJiODA2ZWUzY2Y3YTRiMTNmMzgyMGVlMTM0ZWJmODJjMTI4YyJ9fX0=").build(),
            8,
            ResourceTiers.COMMON
    );




    public static void init() {}
}
