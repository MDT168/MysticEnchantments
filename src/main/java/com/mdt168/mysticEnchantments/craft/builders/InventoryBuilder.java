package com.mdt168.mysticEnchantments.craft.builders;

import com.mdt168.mysticEnchantments.custom.Helper;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryBuilder {

    private final Inventory inventory;
    private static final MiniMessage mm = MiniMessage.miniMessage();
    private InventoryBuilder(String name, int rows) {
        inventory = Bukkit.createInventory(null, Math.max(1, Math.min(6, rows)) * 9, mm.deserialize(name));
    }
    public static InventoryBuilder of(String name, int rows) {
        return new InventoryBuilder(name, rows);
    }
    private InventoryBuilder(int rows) {
        inventory = Bukkit.createInventory(null, Math.max(1, Math.min(6, rows)) * 9);
    }
    public static InventoryBuilder of(int rows) {
        return new InventoryBuilder(rows);
    }
    public InventoryBuilder setItem(int index, ItemStack itemStack) {
        inventory.setItem(Helper.betweenBounds(index, 0, inventory.getSize() - 1), itemStack);
        return this;
    }
    public InventoryBuilder setItem(int row, int column, ItemStack itemStack) {
        int slot = (Helper.betweenBounds(row, 1, 6) - 1) * 9 + (Helper.betweenBounds(column, 1, 9) - 1);
        inventory.setItem(slot, itemStack);
        return this;
    }
    public Inventory build() {
        return inventory;
    }
    public InventoryBuilder fillRow(int row, ItemStack item) {
        int slot = (Helper.betweenBounds(row, 1, 6) - 1) * 9;
        int size = inventory.getSize();
        for (int i = 0; i < 9 && slot < size; i++, slot++) {
            this.inventory.setItem(slot, item);
        }
        return this;
    }
    public InventoryBuilder fillColumn(int column, ItemStack item) {
        int col = Helper.betweenBounds(column, 1, 9) - 1;
        int size = inventory.getSize();
        for (int slot = col; slot < size; slot += 9)
            this.inventory.setItem(slot, item);

        return this;
    }
    public InventoryBuilder fillColumns(ItemStack item, int... columns) {
        for (int column : columns) {
            int col = Helper.betweenBounds(column, 1, 9) - 1;
            int size = inventory.getSize();
            for (int slot = col; slot < size; slot += 9)
                this.inventory.setItem(slot, item);
        }
        return this;
    }

    public InventoryBuilder fillRows(ItemStack item, int... rows) {
        int size = inventory.getSize();
        for (int row : rows) {
            int slot = (Helper.betweenBounds(row, 1, 6) - 1) * 9;
            for (int i = 0; i < 9 && slot < size; i++, slot++) {
                this.inventory.setItem(slot, item);
            }
        }
        return this;
    }

    public InventoryBuilder setLastItem(ItemStack itemStack) {
        inventory.setItem(inventory.getSize() - 1, itemStack);
        return this;
    }
}
