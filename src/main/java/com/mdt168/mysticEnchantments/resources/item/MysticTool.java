package com.mdt168.mysticEnchantments.resources.item;

import com.mdt168.mysticEnchantments.custom.Gradient;
import com.mdt168.mysticEnchantments.custom.ItemDataUtils;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Tool;
import io.papermc.paper.registry.set.RegistryKeySet;
import net.kyori.adventure.util.TriState;
import org.bukkit.Material;
import org.bukkit.block.BlockType;

import java.util.List;
import java.util.Map;

public class MysticTool extends MysticItem {

    public MysticTool(Material material, String name, String description, float defaultMiningSpeed) {
        super(material, name, description);

        setLore(List.of(
                Gradient.BLUE + "<!i>---------------------",
                Gradient.GREEN + "<!i>" + description,
                "<!i>Mining Speed: " + defaultMiningSpeed,
                Gradient.BLUE + "---------------------"
        ));

        Tool.Builder builder = Tool.tool().defaultMiningSpeed(defaultMiningSpeed);
        itemStack.setData(DataComponentTypes.TOOL, builder.build());
    }

    public MysticTool(Material material, int durability, String name, String description, float defaultMiningSpeed) {
        super(material, name, description);

        this.itemStack.setData(DataComponentTypes.MAX_DAMAGE, durability);
        this.itemStack.setData(DataComponentTypes.DAMAGE, 0);

        setLore(List.of(
                Gradient.BLUE + "<!i>---------------------",
                Gradient.GREEN + "<!i>" + description,
                "<!i>Mining Speed: " + defaultMiningSpeed,
                "<!i>Durability: " + durability,
                Gradient.BLUE + "---------------------"
        ));

        Tool.Builder builder = Tool.tool().defaultMiningSpeed(defaultMiningSpeed);
        itemStack.setData(DataComponentTypes.TOOL, builder.build());
    }

    public MysticTool(Material material, String name, String description, float defaultMiningSpeed,
                      Map<RegistryKeySet<BlockType>, Float> mineables) {
        super(material, name, description);

        setLore(List.of(
                Gradient.BLUE + "<!i>---------------------",
                Gradient.GREEN + "<!i>" + description,
                "<!i>Mining Speed: " + defaultMiningSpeed,
                Gradient.BLUE + "---------------------"
        ));

        Tool.Builder builder = Tool.tool().defaultMiningSpeed(defaultMiningSpeed);
        for (Map.Entry<RegistryKeySet<BlockType>, Float> entry : mineables.entrySet()) {
            builder.addRule(Tool.rule(entry.getKey(), entry.getValue(), TriState.TRUE));
        }
        itemStack.setData(DataComponentTypes.TOOL, builder.build());
    }

    public MysticTool(Material material, int durability, String name, String description, float defaultMiningSpeed,
                      Map<RegistryKeySet<BlockType>, Float> mineables) {
        super(material, name, description);

        this.itemStack.setData(DataComponentTypes.MAX_DAMAGE, durability);
        this.itemStack.setData(DataComponentTypes.DAMAGE, 0);

        setLore(List.of(
                Gradient.BLUE + "<!i>---------------------",
                Gradient.GREEN + "<!i>" + description,
                "<!i>Mining Speed: " + defaultMiningSpeed,
                "<!i>Durability: " + durability,
                Gradient.BLUE + "---------------------"
        ));

        Tool.Builder builder = Tool.tool().defaultMiningSpeed(defaultMiningSpeed);
        for (Map.Entry<RegistryKeySet<BlockType>, Float> entry : mineables.entrySet()) {
            builder.addRule(Tool.rule(entry.getKey(), entry.getValue(), TriState.TRUE));
        }
        itemStack.setData(DataComponentTypes.TOOL, builder.build());
    }
}
