package com.mdt168.mysticEnchantments.custom;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class EnchantedItem {
    private final int weight;
    private final String name, description, id;
    private final ItemStack displayedItem;
    private final PlayerAction reward;
    public EnchantedItem(String name, String description, int weight, Material material, int amount, PlayerAction action) {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.reward = action;
        this.id = this.name.toLowerCase().replace(" ", "_").replace("'", "");
        this.displayedItem = new ItemStack(material, amount);
    }
    public EnchantedItem(String name, String description, int weight, Material material, PlayerAction action) {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.reward = action;
        this.id = this.name.toLowerCase().replace(" ", "_").replace("'", "");
        this.displayedItem = new ItemStack(material);
    }
    public EnchantedItem(String name, String description, int weight, ItemStack itemStack, PlayerAction action) {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.reward = action;
        this.id = this.name.toLowerCase().replace(" ", "_").replace("'", "");
        this.displayedItem = itemStack;
    }

    public int getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public double getChance() {
        return Helper.calculateCrateItemChance(this.weight);
    }
    public boolean giveReward(Player player) {
        return reward.run(player);

    }
    public ItemStack getDisplayedItem() {
        ItemDataUtils.addData(displayedItem, id);
        ItemMeta meta = displayedItem.getItemMeta();
        MiniMessage mm = MiniMessage.miniMessage();
        meta.lore(List.of(
                mm.deserialize("<!i><gray>" + description),
                Component.empty(),
                mm.deserialize("<gold><!i>Chance: <green><!i>" + getChance() + "%")
        ));
        meta.displayName(mm.deserialize("<gradient:#D08CFF:#FF55FF><!i>" + name + "</gradient>"));
        displayedItem.setItemMeta(meta);
        return displayedItem;
    }
}
