package com.mdt168.mysticEnchantments.settings;

import com.mdt168.mysticEnchantments.MysticEnchantments;
import com.mdt168.mysticEnchantments.custom.ItemDataUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class Setting {

    private final NamespacedKey key;
    private final ItemStack toDisplay;
    private final String description, name, id;
    public Setting(String name, String description, ItemStack toDisplay) {
        this.name = name;
        this.id = name.toLowerCase().replace(" ", "_");
        this.description = description;
        this.key = new NamespacedKey(MysticEnchantments.getInstance(), id);
        this.toDisplay = toDisplay.clone();


        MiniMessage mm = MiniMessage.miniMessage();
        this.toDisplay.editMeta(meta -> {
            meta.lore(List.of(
                    mm.deserialize("<!i><gray>" + description),
                    Component.empty()
            ));
            meta.displayName(mm.deserialize("<!i><yellow>" + this.name));
        });

        ItemDataUtils.addData(this.toDisplay, id);
    }
    public Setting(String name, String description, Material toDisplay) {
        this.name = name;
        this.id = name.toLowerCase().replace(" ", "_");
        this.description = description;
        this.key = new NamespacedKey(MysticEnchantments.getInstance(), id);
        this.toDisplay = new ItemStack(toDisplay);


        MiniMessage mm = MiniMessage.miniMessage();
        this.toDisplay.editMeta(meta -> {
            meta.lore(List.of(
                    mm.deserialize("<!i><gray>" + description),
                    Component.empty()
            ));
            meta.displayName(mm.deserialize("<!i><yellow>" + this.name));
        });
        ItemDataUtils.addData(this.toDisplay, id);
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public ItemStack getToDisplay() {
        return toDisplay;
    }

    public NamespacedKey getKey() {
        return key;
    }

    public void setValue(Player player, boolean value) {
        if (getKey() == null) illegalUse();
        player.getPersistentDataContainer().set(getKey(), PersistentDataType.BOOLEAN, value);
    }

    public void invertValue(Player player) {
        setValue(player, !getValue(player));
    }

    private void illegalUse() {
        String errorMessage = "Needs to create getKey() method and return the Namespaced Key Of the Class";
        throw new IllegalAccessError(errorMessage);
    }

    public boolean getValue(Player player) {
        if (getKey() == null) illegalUse();
        return player.getPersistentDataContainer().getOrDefault(getKey(), PersistentDataType.BOOLEAN, true);
    }
}
