package com.mdt168.mysticEnchantments.craft.builders;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.mdt168.mysticEnchantments.enchants.HumaneEnchantment;
import com.mdt168.mysticEnchantments.custom.ItemDataUtils;
import io.papermc.paper.datacomponent.DataComponentType;
import io.papermc.paper.datacomponent.DataComponentTypes;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemStackBuilder {
    private ItemStack itemStack;
    private static MiniMessage mm = MiniMessage.miniMessage();
    public static ItemStackBuilder of(Material material, int amount) {
        return new ItemStackBuilder(material, amount);
    }
    private ItemStackBuilder(Material material, int amount) {
        this.itemStack = new ItemStack(material, amount);
    }
    private ItemStackBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }
    public static ItemStackBuilder of(Material material) {
        return new ItemStackBuilder(material);
    }

    private ItemStackBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStackBuilder copy() {
        return new ItemStackBuilder(this.itemStack.clone());
    }

    public ItemStackBuilder setDurability(int durability) {
        itemStack.setData(DataComponentTypes.MAX_DAMAGE, durability);
        itemStack.setData(DataComponentTypes.DAMAGE, 0);
        return this;
    }

    public ItemStackBuilder setLore(String lore) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.lore(List.of(MiniMessage.miniMessage().deserialize(lore)));
        itemStack.setItemMeta(meta);
        return this;
    }
    public ItemStackBuilder setLore(List<Component> lore) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.lore(lore);
        itemStack.setItemMeta(meta);
        return this;
    }
    public ItemStackBuilder setDisplayName(String name) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.displayName(MiniMessage.miniMessage().deserialize("<!i>" + name));
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder registerAsHumane(HumaneEnchantment humaneEnchantment) {
        ItemDataUtils.setData(itemStack, humaneEnchantment.getId());
        return this;
    }

    public ItemStackBuilder setDisplayName(Component displayName) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.displayName(displayName);
        itemStack.setItemMeta(meta);
        return this;
    }
    public <T> ItemStackBuilder setData(DataComponentType.@NotNull Valued<T> type, @NotNull T value) {
        itemStack.setData(type, value);
        return this;
    }
    public ItemStackBuilder setId(String id) {
        id = id.toLowerCase().replace(" ", "_");
        ItemDataUtils.setData(itemStack, id);
        return this;
    }
    public ItemStack build() {
        return this.itemStack;
    }
    public ItemStack build(boolean cloned) {
        if (cloned) return this.itemStack.clone();
        return this.itemStack;
    }

    public ItemStackBuilder addId(String id) {
        ItemDataUtils.addData(itemStack, id);
        return this;
    }
    public static void main(String[] args) {
        System.out.println(UUID.randomUUID());
    }

    public ItemStackBuilder setMaxStackSize(int maxStackSize) {
        itemStack.editMeta(meta -> meta.setMaxStackSize(maxStackSize));
        return this;
    }

    public ItemStackBuilder setSkullTexture(String texture) {
        if (itemStack.getType() == Material.PLAYER_HEAD) {
            UUID uuid = UUID.fromString("255a35dd-55ac-4fd1-9add-d554a61b3d0c");
            SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
            PlayerProfile profile = Bukkit.createProfile(uuid);
            profile.setProperty(new ProfileProperty("textures", texture));
            meta.setPlayerProfile(profile);
            itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemStackBuilder setDisplayTooltip(boolean b) {
        itemStack.editMeta(itemMeta -> {
            itemMeta.setHideTooltip(!b);
        });
        return this;
    }

    public ItemStackBuilder unsetData(@NotNull DataComponentType type) {
        itemStack.unsetData(type);
        return this;
    }

    public ItemStackBuilder setHideToolTip(boolean b) {
        this.itemStack.editMeta(meta -> meta.setHideTooltip(b));
        return this;
    }

    public ItemStackBuilder addToLore(String s) {
        this.itemStack.editMeta(meta -> {
            List<Component> lore = meta.lore();
            lore = lore == null ? new ArrayList<>() : lore;
            lore.add(mm.deserialize(s));
            meta.lore(lore);
        });
        return this;
    }
}
