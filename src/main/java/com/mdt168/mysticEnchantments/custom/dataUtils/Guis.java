package com.mdt168.mysticEnchantments.custom.dataUtils;

import com.mdt168.mysticEnchantments.craft.builders.ItemStackBuilder;
import com.mdt168.mysticEnchantments.custom.*;
import com.mdt168.mysticEnchantments.enchants.HumaneEnchantment;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Guis {
    private static final Inventory main = GuiHelper.getMainGui();
    private static final Inventory basic = GuiHelper.getBasicGui();
    private static final Inventory enhanced = GuiHelper.getEnhancedGui();
    private static final Inventory refined = GuiHelper.getRefinedGui();
    private static final Inventory elite = GuiHelper.getEliteGui();
    private static final Inventory mythic = GuiHelper.getMythicGui();
    private static final Inventory enchanted = GuiHelper.getEnchantedCrateGui();
    private static final Inventory items = GuiHelper.getItemsGui();
    private static Inventory recipes = GuiHelper.getRecipesInventory();

    public static Inventory getMainGui(Player player) {
        String name = "<!i><light_purple>Mystic Enchanter";
        Inventory inventory = Helper.cloneInventory(main, name);

        ItemStack enchantedCrate = Helper.getCrate(player);

        inventory.setItem(inventory.getSize() - 5, GuiHelper.createViewer(player));
        inventory.setItem(31, enchantedCrate);

        return inventory;
    }

    public static Inventory getBasicGui(Player player) {
        String name = "<!i><light_purple>Mystic Enchanter - <green>Basic";
        Inventory inventory = Helper.cloneInventory(basic, name);
        Helper.updateViewer(player, inventory);
        return inventory;
    }
    public static Inventory getEnhancedGui(Player player) {
        String name = "<!i><light_purple>Mystic Enchanter - <green>Enhanced";
        Inventory inventory = Helper.cloneInventory(enhanced, name);
        Helper.updateViewer(player, inventory);
        return inventory;
    }
    public static Inventory getRefinedGui(Player player) {
        String name = "<!i><light_purple>Mystic Enchanter - <green>Refined";
        Inventory inventory = Helper.cloneInventory(refined, name);
        Helper.updateViewer(player, inventory);
        return inventory;
    }
    public static Inventory getEliteGui(Player player) {
        String name = "<!i><light_purple>Mystic Enchanter - <green>Elite";
        Inventory inventory = Helper.cloneInventory(elite, name);
        Helper.updateViewer(player, inventory);
        return inventory;
    }
    public static Inventory getMythicGui(Player player) {
        String name = "<!i><light_purple>Mystic Enchanter - <green>Mythic";
        Inventory inventory = Helper.cloneInventory(mythic, name);
        Helper.updateViewer(player, inventory);
        return inventory;
    }
    public static Inventory getEnchantedCrateGui(Player player) {
        String guiTitle = "<gradient:#BA55D3:#FF6EC7><bold>\uD83C\uDF20    Enchanted Crate     \uD83C\uDF20</bold></gradient>";
        Inventory inventory = Helper.cloneInventory(enchanted, guiTitle);
        Helper.updateViewer(player, inventory);
        return inventory;
    }
    public static Inventory getItemsGui(Player player) {
        String name = "<!i><light_purple>Mystic Enchanter - <reset><green>Items";
        Inventory cloned = Helper.cloneInventory(items, name);
        Helper.updateViewer(player, cloned);
        return cloned;
    }
    public static Inventory getRecipesGui(Player player) {
        Inventory cloned = Helper.cloneInventory(recipes, Gradient.AQUA + "Mystic Recipes");
        Helper.updateViewer(player, cloned);
        return cloned;
    }

    public static Inventory getHumaneEnchantsGui(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 36, MiniMessage.miniMessage().deserialize(Gradient.PINK + "Active Humane Enchantments"));
        List<HumaneEnchantment> enchants = PlayerDataUtils.getEnchantmentsOnPlayer(player);
        List<ItemStack> books = new ArrayList<>();
        for (HumaneEnchantment enchantment : enchants) {
            books.add(Helper.createBook(enchantment));
        }
        for (ItemStack book : books) {
            inventory.addItem(book);
        }
        if (inventory.isEmpty()) {
            inventory.setItem(13, ItemStackBuilder.of(Material.BARRIER).setDisplayName("<red><!i>You don't have any active Humane Enchantments!").build());
        }
        inventory.setItem(inventory.getSize() - 5, GuiHelper.createViewer(player));
        inventory.setItem(inventory.getSize() - 1, GuiHelper.createBack());
        return inventory;
    }

    public static void updateRecipes() {
        recipes = GuiHelper.getRecipesInventory();
    }
}
