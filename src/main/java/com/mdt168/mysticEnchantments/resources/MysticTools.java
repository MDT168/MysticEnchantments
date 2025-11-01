package com.mdt168.mysticEnchantments.resources;

import com.mdt168.mysticEnchantments.resources.item.MysticSword;
import com.mdt168.mysticEnchantments.resources.item.MysticTool;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.keys.BlockTypeKeys;
import io.papermc.paper.registry.set.RegistrySet;
import org.bukkit.Material;

import java.util.Map;

public class MysticTools {
    public static final MysticTool SPLINTERED_PICKAXE = new MysticTool(
            Material.WOODEN_PICKAXE, // Displayed Material
            30, // Durability
            "Splintered Pickaxe", // Name
            "A weak common pickaxe that mines stone and coal for Mystic Resources", // Description
            1.5f, // Default Mining Speed
            Map.of( // Blocks Supported
                    RegistrySet.keySet(RegistryKey.BLOCK, BlockTypeKeys.STONE), 2.9f,
                    RegistrySet.keySet(RegistryKey.BLOCK, BlockTypeKeys.COAL_ORE), 2.5f,
                    RegistrySet.keySet(RegistryKey.BLOCK, BlockTypeKeys.COPPER_ORE), 2.6f
            )
    );
    public static final MysticSword SOME_SWORD = new MysticSword(
            Material.WOODEN_SWORD, // mat
            15, // durability
            "Some Sword", // Name
            "Some Description", // Description
            6, // Damage
            2 // Attack Speed
    );
}
