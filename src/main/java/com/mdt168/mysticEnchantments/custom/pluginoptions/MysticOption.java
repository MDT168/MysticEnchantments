package com.mdt168.mysticEnchantments.custom.pluginoptions;

import com.mdt168.mysticEnchantments.config.ConfigSetting;
import com.mdt168.mysticEnchantments.config.ConfigSettings;
import com.mdt168.mysticEnchantments.custom.ItemDataUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.*;

public class MysticOption {
    private static final Map<String, MysticOption> options = new LinkedHashMap<>();
    private final String name, description, id;
    private final ItemStack itemStack;
    private final OnPurchase onPurchase;
    private final double coins;
    private MysticOption(String name, String description, ConfigSetting<Double> mysticCoinsPrice, OnPurchase onPurchase) {
        this.name = name;
        this.description = description;
        this.onPurchase = onPurchase;
        this.coins = mysticCoinsPrice.getValue();
        this.id = this.name.toLowerCase().replace(" ", "_");
        this.itemStack = new ItemStack(Material.ENDER_EYE);
        ItemMeta meta = this.itemStack.getItemMeta();
        MiniMessage mm = MiniMessage.miniMessage();
        meta.displayName(mm.deserialize(
                "<!i><gradient:#D066FF:#FF6EC7>" + name + "</gradient>"
        ));
        meta.lore(List.of(
                Component.empty(),
                mm.deserialize("<!i><gold>✦ " + description + " ✦</gold>"),
                Component.empty(),
                mm.deserialize("<!i><red>✧ Price: <#FFAA00>" + mysticCoinsPrice.getValue() + " Mystic Coins</#FFAA00></red>"),
                Component.empty(),
                mm.deserialize("<!i><gradient:#55FF55:#00FFAA>⟡ Click To Use</gradient>")
        ));
        this.itemStack.setItemMeta(meta);
        ItemDataUtils.addData(itemStack, id);
    }
    private MysticOption(String name, String description, ConfigSetting<Double> mysticCoinsPrice, Material material, OnPurchase onPurchase) {
        this.name = name;
        this.onPurchase = onPurchase;
        this.description = description;
        this.coins = mysticCoinsPrice.getValue();
        this.id = this.name.toLowerCase().replace(" ", "_");
        this.itemStack = new ItemStack(material);
        ItemMeta meta = this.itemStack.getItemMeta();
        MiniMessage mm = MiniMessage.miniMessage();
        meta.displayName(mm.deserialize(
                "<!i><gradient:#D066FF:#FF6EC7>" + name + "</gradient>"
        ));
        meta.lore(List.of(
                Component.empty(),
                mm.deserialize("<!i><gold>✦ " + description + " ✦</gold>"),
                Component.empty(),
                mm.deserialize("<!i><red>✧ Price: <#FFAA00>" + mysticCoinsPrice.getValue() + " Mystic Coins</#FFAA00></red>"),
                Component.empty(), // Empty line
                mm.deserialize("<!i><gradient:#55FF55:#00FFAA>⟡ Click To Use</gradient>")
        ));
        this.itemStack.setItemMeta(meta);
        PersistentDataContainer pdc = itemStack.getItemMeta().getPersistentDataContainer();
        ItemDataUtils.addData(itemStack, id);
    }
    public static MysticOption register(String name, String description, ConfigSetting<Double> price, OnPurchase onPurchase) {
        MysticOption option = new MysticOption(name, description, price, onPurchase);
        options.put(option.getId(), option);
        return option;
    }
    public static MysticOption register(String name, String description, ConfigSetting<Double> price, Material material, OnPurchase onPurchase) {
        MysticOption option = new MysticOption(name, description, price, material, onPurchase);
        options.put(option.getId(), option);
        return option;
    }
    public static MysticOption register(String name, String description, ConfigSetting<Double> price, OnPurchase onPurchase, boolean skipOnApiMode) {
        MysticOption option = new MysticOption(name, description, price, onPurchase);
        if ((!(ConfigSettings.API_MODE.getValue() && skipOnApiMode))) options.put(option.getId(), option);
        return option;
    }
    public static MysticOption register(String name, String description, ConfigSetting<Double> price, Material material, OnPurchase onPurchase, boolean skipOnApiMode) {
        MysticOption option = new MysticOption(name, description, price, material, onPurchase);
        if ((!(ConfigSettings.API_MODE.getValue() && skipOnApiMode))) options.put(option.getId(), option);
        return option;
    }

    public static MysticOption fromItem(ItemStack itemStack) {
        return options.get(ItemDataUtils.getData(itemStack));
    }

    public boolean buy(Player player) {
        return onPurchase.run(player);
    }

    public String getName() {
        return name;
    }

    public static List<MysticOption> getOptions() {
        return new ArrayList<>(options.values());
    }

    public double getMysticCoinsPrice() {
        return coins;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
