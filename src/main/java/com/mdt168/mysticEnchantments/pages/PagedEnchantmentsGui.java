package com.mdt168.mysticEnchantments.pages;

import com.mdt168.mysticEnchantments.MysticEnchantments;
import com.mdt168.mysticEnchantments.craft.builders.InventoryBuilder;
import com.mdt168.mysticEnchantments.craft.builders.ItemStackBuilder;
import com.mdt168.mysticEnchantments.custom.BookTiers;
import com.mdt168.mysticEnchantments.custom.GuiHelper;
import com.mdt168.mysticEnchantments.custom.Helper;
import com.mdt168.mysticEnchantments.custom.dataUtils.EnchantmentsUtils;
import com.mdt168.mysticEnchantments.enchants.MysticEnchantmentComponent;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class PagedEnchantmentsGui {
    private static final NamespacedKey key = new NamespacedKey(MysticEnchantments.getInstance(), "mystic_enchantments_paged_indexor");
    private final List<Inventory> pages;
    private static final Map<String, PagedEnchantmentsGui> guis = new LinkedHashMap<>();
    private final Inventory format;
    private final String name;
    private static final ItemStack next = Helper.getPagerRight();
    private static final ItemStack previous = Helper.getPagerLeft();
    private PagedEnchantmentsGui(BookTiers tier) {
        pages = new ArrayList<>();
        ItemStack glassFillers = ItemStackBuilder.of(tier.getMaterial()).setHideToolTip(true).build();
        format = InventoryBuilder.of(6)
                .fillRows(glassFillers, 1, 6)
                .fillColumns(glassFillers, 1, 9)
                .setItem(6, 8, GuiHelper.createBack())
                .build();
        List<MysticEnchantmentComponent> allEnchantments = EnchantmentsUtils.getAllEnchantments(tier);
        name = "<gradient:#B28DFF:#9C6BFF:#7A41FF>" + tier.getName() + " Enchantments";
        List<List<MysticEnchantmentComponent>> sliced = Helper.partitionList(allEnchantments, 28);
        for (List<MysticEnchantmentComponent> enchants : sliced) {
            Inventory page = Helper.cloneInventory(format, name);
            for (MysticEnchantmentComponent component : enchants) {
                ItemStack itemStack = Helper.createItemFromComponent(component);
                if (itemStack == null) continue;
                page.addItem(itemStack);
            }
            pages.add(page);
        }
        for (int current = 0; current < pages.size() && pages.size() > 1; current++) {
            Inventory page = pages.get(current);
            ItemStack previousPage = previous.clone();
            String nextData = tier.getName() + ":" + (current + 1);
            String previousData = tier.getName() + ":" + (current - 1);
            previousPage.editPersistentDataContainer(container -> container.set(key, PersistentDataType.STRING, previousData));
            ItemStack nextPage = next.clone();
            nextPage.editPersistentDataContainer(container -> container.set(key, PersistentDataType.STRING, nextData));

            if (current == 0) {
                page.setItem(page.getSize() - 1, nextPage);
                continue;
            }
            else if (current == pages.size() - 1) {
                page.setItem(page.getSize() - 9, previousPage);
                continue;
            }
            page.setItem(page.getSize() - 1, nextPage);
            page.setItem(page.getSize() - 9, previousPage);
        }
    }
    public static PagedEnchantmentsGui register(BookTiers tier) {
        PagedEnchantmentsGui gui = new PagedEnchantmentsGui(tier);
        guis.put(tier.getName(), gui);
        return gui;
    }

    public static boolean isPaginated(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().isAir()) return false;
        return itemStack.getPersistentDataContainer().has(key, PersistentDataType.STRING);
    }

    public static Inventory getPageFromItem(ItemStack itemStack, Player player) {
        if (itemStack == null || itemStack.getType().isAir()) return null;
        String data = itemStack.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        if (data == null) return null;
        String[] parts = data.split(":");
        int page;
        String tier;
        try {
            tier = parts[0];
            page = Integer.parseInt(parts[1]);
        } catch (Exception e) {
            return null;
        }
        return guis.get(tier).getPage(page + 1, player);
    }

    public Inventory getPage(int page, Player player) {
        Inventory inventory = pages.isEmpty() ? Helper.cloneInventory(format, name) : Helper.cloneInventory(pages.get(Helper.betweenBounds(page - 1, 0, pages.size() - 1)), name);
        Helper.updateViewer(player, inventory);
        return inventory;
    }
}
