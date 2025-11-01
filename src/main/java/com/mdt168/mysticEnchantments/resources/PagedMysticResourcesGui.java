package com.mdt168.mysticEnchantments.resources;

import com.mdt168.mysticEnchantments.MysticEnchantments;
import com.mdt168.mysticEnchantments.craft.MysticItems;
import com.mdt168.mysticEnchantments.craft.builders.InventoryBuilder;
import com.mdt168.mysticEnchantments.craft.builders.ItemStackBuilder;
import com.mdt168.mysticEnchantments.custom.GuiHelper;
import com.mdt168.mysticEnchantments.custom.Helper;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class PagedMysticResourcesGui {
    private static final NamespacedKey key = new NamespacedKey(MysticEnchantments.getInstance(), "mystic_enchantments_resources_indexor");
    private static final List<Inventory> pages = new ArrayList<>();
    private final Inventory format;
    private final String name;
    private static final ItemStack next = Helper.getPagerRight();
    private static final ItemStack previous = Helper.getPagerLeft();
    public PagedMysticResourcesGui() {
        ItemStack glassFillers = ItemStackBuilder.of(Material.MAGENTA_STAINED_GLASS_PANE).setHideToolTip(true).build();
        format = InventoryBuilder.of(6)
                .fillRows(glassFillers, 1, 6)
                .fillColumns(glassFillers, 1, 9)
                .setItem(6, 8, GuiHelper.createBack())
                .build();
        List<MysticResource> allResources = MysticResource.getResources();
        name = "<gradient:#B28DFF:#9C6BFF:#7A41FF>Mystic Resources";
        List<List<MysticResource>> sliced = Helper.partitionList(allResources, 28);
        for (List<MysticResource> resources : sliced) {
            Inventory page = Helper.cloneInventory(format, name);
            for (MysticResource resource : resources) {
                page.addItem(resource.getItemStack());
            }
            pages.add(page);
        }
        for (int current = 0; current < pages.size() && pages.size() > 1; current++) {
            Inventory page = pages.get(current);
            ItemStack previousPage = previous.clone();
            int finalCurrent = current;
            previousPage.editPersistentDataContainer(container -> container.set(key, PersistentDataType.INTEGER, finalCurrent - 1));
            ItemStack nextPage = next.clone();
            nextPage.editPersistentDataContainer(container -> container.set(key, PersistentDataType.INTEGER, finalCurrent + 1));

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

    public static boolean isPaginated(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().isAir()) return false;
        return itemStack.getPersistentDataContainer().has(key, PersistentDataType.INTEGER);
    }

    public static Inventory getPageFromItem(ItemStack itemStack, Player player) {
        if (itemStack == null || itemStack.getType().isAir()) return null;
        Integer page = itemStack.getPersistentDataContainer().get(key, PersistentDataType.INTEGER);
        if (page == null) return null;
        return pages.get(page);
    }

    public Inventory getPage(int page, Player player) {
        Inventory inventory = pages.isEmpty() ? Helper.cloneInventory(format, name) : Helper.cloneInventory(pages.get(Helper.betweenBounds(page - 1, 0, pages.size() - 1)), name);
        Helper.updateViewer(player, inventory);
        return inventory;
    }
}
