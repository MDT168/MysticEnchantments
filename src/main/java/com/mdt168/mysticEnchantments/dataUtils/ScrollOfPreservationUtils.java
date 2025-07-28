package com.mdt168.mysticEnchantments.dataUtils;

import com.mdt168.mysticEnchantments.MysticEnchantments;
import com.mdt168.mysticEnchantments.custom.Gradient;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class ScrollOfPreservationUtils {
    private static final NamespacedKey key = new NamespacedKey(MysticEnchantments.getInstance(), "scroll_of_preservation");
    private static final Component component = MiniMessage.miniMessage().deserialize(Gradient.AQUA + "<!i><obfuscated>|</obfuscated> Preserved <obfuscated>|</obfuscated>");

    public static boolean isPreserved(ItemStack itemStack) {
        if (itemStack == null) return false;
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return false;
        return meta.getPersistentDataContainer().getOrDefault(key, PersistentDataType.BOOLEAN, false);
    }
    public static void preserve(ItemStack itemStack) {
        if (itemStack == null) return;
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return;
        meta.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);
        List<Component> lore = meta.lore();
        lore = lore == null ? new ArrayList<>() : lore;
        lore.add(component);
        meta.lore(lore);
        itemStack.setItemMeta(meta);
    }
    public static void removePreservation(ItemStack itemStack) {
        if (itemStack == null) return;
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return;
        meta.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, false);
        List<Component> lore = meta.lore();
        lore = lore == null ? new ArrayList<>() : lore;
        lore.remove(component);
        meta.lore(lore);
        itemStack.setItemMeta(meta);
    }
}
