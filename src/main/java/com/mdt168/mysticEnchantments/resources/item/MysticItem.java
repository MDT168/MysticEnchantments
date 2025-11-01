package com.mdt168.mysticEnchantments.resources.item;

import com.mdt168.mysticEnchantments.custom.Gradient;
import com.mdt168.mysticEnchantments.custom.Helper;
import com.mdt168.mysticEnchantments.custom.ItemDataUtils;
import io.papermc.paper.datacomponent.DataComponentTypes;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class MysticItem {
    protected final String name;
    protected final String description;
    protected final String id;
    protected final ItemStack itemStack;

    protected static final MiniMessage mm = MiniMessage.miniMessage();

    public MysticItem(Material material, String name, String description) {
        this.name = name;
        this.description = description;
        this.id = Helper.toId(this.name);
        this.itemStack = new ItemStack(material);
        ItemDataUtils.setData(this.itemStack, this.id);
    }

    protected void setDurability(int durability) {
        this.itemStack.setData(DataComponentTypes.MAX_DAMAGE, durability);
        this.itemStack.setData(DataComponentTypes.DAMAGE, 0);
    }

    protected void setLore(List<String> loreLines) {
        this.itemStack.editMeta(meta -> {
            meta.displayName(mm.deserialize(Gradient.YELLOW + "<!i>♦ " + name + " ♦"));
            meta.lore(loreLines.stream().map(mm::deserialize).toList());
        });
    }

    public boolean isItem(ItemStack itemStack) {
        return id.equals(ItemDataUtils.getData(itemStack));
    }

    public String getDescription() {
        return description;
    }

    public ItemStack asItemStack() {
        return itemStack.clone();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public boolean give(Player player) {
        return Helper.safeGiveItem(player, this.itemStack.clone());
    }
}
