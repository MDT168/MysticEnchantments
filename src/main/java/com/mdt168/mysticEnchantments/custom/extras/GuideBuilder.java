package com.mdt168.mysticEnchantments.custom.extras;

import com.mdt168.mysticEnchantments.custom.Gradient;
import com.mdt168.mysticEnchantments.custom.ItemDataUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GuideBuilder {

    public static GuideInventory start() {
        return new GuideInventory();
    }
    public static class GuideInventory {
        private final List<ItemStack> guiders;
        private final Inventory guideInventory;
        private static final MiniMessage mm = MiniMessage.miniMessage();
        public GuideInventory() {
            guiders = new ArrayList<>();
            guideInventory = Bukkit.createInventory(null, 18, MiniMessage.miniMessage().deserialize(Gradient.BLUE + "|  Mystic Guide  |"));
        }
        public GuideInventory add(String name, String... description) {
            ItemStack toAdd = new ItemStack(Material.BOOK);
            toAdd.editMeta(meta -> {
                meta.displayName(mm.deserialize("<!i>" + name));
                List<Component> lore = meta.lore();
                lore = lore == null ? new ArrayList<>() : lore;
                for (String string : description) {
                    lore.add(mm.deserialize("<!i>" + string));
                }
                meta.lore(lore);
            });
            guiders.add(toAdd);
            return this;
        }
        public Inventory buildGuide() {
            for (ItemStack itemStack : guiders) {
                guideInventory.addItem(itemStack);
            }
            ItemDataUtils.addData(guideInventory.getItem(0), "guider");
            return guideInventory;
        }
    }
}
