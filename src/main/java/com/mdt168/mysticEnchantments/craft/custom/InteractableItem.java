package com.mdt168.mysticEnchantments.craft.custom;

import com.mdt168.mysticEnchantments.custom.ItemDataUtils;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class InteractableItem {
    private static final Map<String, InteractableItem> items = new LinkedHashMap<>();
    private static int index = 0;

    private final OnInteract onInteract;
    private final ItemStack itemStack;
    private final String data;
    private InteractableItem(ItemStack itemStack, OnInteract onInteract) {
        this.itemStack = itemStack;
        this.onInteract = onInteract;
        ItemDataUtils.setIfAbsent(this.itemStack, "mystic_interactable_item_no_" + index);
        index++;
        this.data = ItemDataUtils.getData(this.itemStack);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public static List<InteractableItem> getItems() {
        return new ArrayList<>(items.values());
    }
    public static InteractableItem fromItem(ItemStack itemStack) {
        return items.get(ItemDataUtils.getData(itemStack));
    }
    public static InteractableItem register(ItemStack itemStack, OnInteract onInteract) {
        InteractableItem item = new InteractableItem(itemStack, onInteract);
        items.put(item.data, item);
        return item;
    }
    public void run(PlayerInteractEvent event) {
        onInteract.run(event);
    }
}
