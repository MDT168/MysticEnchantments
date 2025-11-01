package com.mdt168.mysticEnchantments.custom;

import com.mdt168.mysticEnchantments.craft.builders.ItemStackBuilder;
import com.mdt168.mysticEnchantments.enchants.HumaneEnchantment;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ItemDataUtils {

    // A new key is created for items to avoid any potential conflicts with player data.
    public static final NamespacedKey key = new NamespacedKey(JavaPlugin.getProvidingPlugin(ItemDataUtils.class), "item_data_key");
    public static final NamespacedKey mysticalBagKey = new NamespacedKey(JavaPlugin.getProvidingPlugin(ItemDataUtils.class), "mystical_bag_key");
    public static final NamespacedKey bagKeyUuid = new NamespacedKey(JavaPlugin.getProvidingPlugin(ItemDataUtils.class), "mystical_bag_key_uuid");

    /**
     * Adds a string to the item's PDC list-style storage.
     * This method modifies the passed ItemStack.
     *
     * @param itemStack The item stack.
     * @param data      The string to add.
     */
    public static void addData(ItemStack itemStack, String data) {
        if (itemStack == null || !itemStack.hasItemMeta() || data == null || data.isEmpty()) {
            return;
        }
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        String current = pdc.getOrDefault(key, PersistentDataType.STRING, "");
        String[] entries = current.isEmpty() ? new String[0] : current.split(",");
        for (String entry : entries) {
            if (entry.equals(data)) {
                return;
            }
        }
        String updated = current.isEmpty() ? data : current + "," + data;
        pdc.set(key, PersistentDataType.STRING, updated);
        itemStack.setItemMeta(meta);
    }

    public static boolean hasKey(ItemStack itemStack) {
        return itemStack.getItemMeta().getPersistentDataContainer().has(key);
    }

    /**
     * Removes a specific string from the item's PDC.
     * This method modifies the passed ItemStack.
     *
     * @param itemStack The item stack.
     * @param data      The string to remove.
     * @return true if the data was found and removed, false otherwise.
     */
    public static boolean removeData(ItemStack itemStack, String data) {
        if (itemStack == null || !itemStack.hasItemMeta() || data == null) {
            return false;
        }

        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        String current = pdc.getOrDefault(key, PersistentDataType.STRING, "");
        if (current.isEmpty()) {
            return false; // Nothing to remove.
        }

        List<String> entries = new ArrayList<>(Arrays.asList(current.split(",")));
        boolean removed = entries.remove(data);

        // If an entry was successfully removed, update the PDC.
        if (removed) {
            String updated = String.join(",", entries);
            pdc.set(key, PersistentDataType.STRING, updated);
            itemStack.setItemMeta(meta);
        }

        return removed;
    }

    /**
     * Retrieves all data strings from the item's PDC and then removes them all.
     * This method modifies the passed ItemStack.
     *
     * @param itemStack The item stack.
     * @return An array of strings that were stored, or an empty array if none were found.
     */
    public static String[] removeAndGetAllData(ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta()) {
            return new String[0];
        }

        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        String current = pdc.getOrDefault(key, PersistentDataType.STRING, "");

        if (current.isEmpty()) {
            return new String[0];
        }

        String[] data = current.split(",");
        pdc.remove(key); // Clear the data from the item.
        itemStack.setItemMeta(meta);

        return data;
    }
    public static void removeData(ItemStack itemStack) {
        if (itemStack == null) return;
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return;
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.remove(key);
        itemStack.setItemMeta(meta);
    }

    /**
     * Checks if a given string is stored in the item's PDC.
     *
     * @param itemStack The item stack.
     * @param data      The string to check.
     * @return true if the data exists, false otherwise.
     */
    public static boolean hasData(ItemStack itemStack, String data) {
        if (itemStack == null || !itemStack.hasItemMeta() || data == null) {
            return false;
        }
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        String current = pdc.get(key, PersistentDataType.STRING);
        if (current == null || current.isEmpty()) {
            return false;
        }
        return Arrays.asList(current.split(",")).contains(data);
    }
    public static boolean hasData(ItemStack itemStack) {
        return itemStack != null && itemStack.getPersistentDataContainer().has(key);
    }
    public static boolean hasEnchantmentData(ItemStack itemStack, String data) {
        if (itemStack == null || !itemStack.hasItemMeta() || data == null) {
            return false;
        }

        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        String current = pdc.get(key, PersistentDataType.STRING);

        if (current == null || current.isEmpty()) {
            return false;
        }

        // Efficiently check for the exact string match.
        for (String string : current.split(",")) {
            String enchantName = string.split(" ")[0];
            if (enchantName.equalsIgnoreCase(data)) return true;
        }
        return false;
    }

    /**
     * Gets all data stored on the item without modifying it.
     *
     * @param itemStack The item stack.
     * @return An array of strings stored on the item, or an empty array if none.
     */
    public static String[] getAllData(ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta()) {
            return new String[0];
        }
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        String current = pdc.getOrDefault(key, PersistentDataType.STRING, "");

        return current.isEmpty() ? new String[0] : current.split(",");
    }
    public static String getData(ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta()) {
            return "";
        }
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        return pdc.getOrDefault(key, PersistentDataType.STRING, "");
    }

    public static boolean hasHumaneEnchantmentOnItem(ItemStack rightClickedItem, HumaneEnchantment unKnow) {
        return hasData(rightClickedItem, unKnow.getId());
    }

    public static void setData(ItemStack itemStack, String id) {
        if (itemStack == null) return;
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return;

        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(key, PersistentDataType.STRING, id);
        itemStack.setItemMeta(meta);
    }

    public static void saveInventoryToItem(ItemStack item, Inventory inventory) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        meta.getPersistentDataContainer().set(
                mysticalBagKey,
                PersistentDataType.BYTE_ARRAY,
                ItemStack.serializeItemsAsBytes(inventory.getContents())
        );

        item.setItemMeta(meta);
    }

    public static Inventory loadInventoryFromItem(ItemStack item, String id) {
        int size = 36;
        Component title = MiniMessage.miniMessage().deserialize(Gradient.RED + "Mystic Enchantments Bag");
        Inventory inventory = Bukkit.createInventory(null, size, title);
        inventory.setItem(inventory.getSize() - 5, ItemStackBuilder.of(Material.COAL_BLOCK).setDisplayTooltip(false).setId(id).build());
        if (!item.hasItemMeta()) {
            return inventory;
        }
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        if (!pdc.has(mysticalBagKey, PersistentDataType.BYTE_ARRAY)) {
            return inventory;
        }
        ItemStack[] contents = ItemStack.deserializeItemsFromBytes(Objects.requireNonNull(pdc.get(mysticalBagKey, PersistentDataType.BYTE_ARRAY)));
        inventory.setContents(contents);
        return inventory;
    }


    public static void setIfAbsent(ItemStack itemStack, String data) {
        if (itemStack == null || itemStack.getType().isAir()) return;
        itemStack.editPersistentDataContainer(container -> {
            if (!container.has(key, PersistentDataType.STRING)) setData(itemStack, data);
        });
    }
}