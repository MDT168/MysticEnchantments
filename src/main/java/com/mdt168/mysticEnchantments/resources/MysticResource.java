package com.mdt168.mysticEnchantments.resources;

import com.mdt168.mysticEnchantments.MysticEnchantments;
import com.mdt168.mysticEnchantments.config.ConfigSetting;
import com.mdt168.mysticEnchantments.config.ConfigSettings;
import com.mdt168.mysticEnchantments.craft.custom.InteractableItem;
import com.mdt168.mysticEnchantments.custom.Gradient;
import com.mdt168.mysticEnchantments.custom.Helper;
import com.mdt168.mysticEnchantments.custom.MysticCoinUtils;
import com.mdt168.mysticEnchantments.utility.buffs.MysticBuffs;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class MysticResource {
    private static final Map<String, MysticResource> resources = new LinkedHashMap<>();
    private final double worth;
    private final String id, name, description;
    private final ItemStack itemStack;
    private final ResourceTiers tier;
    private static final MiniMessage mm = MiniMessage.miniMessage();
    private static final NamespacedKey key = new NamespacedKey(MysticEnchantments.getInstance(), "mystic_resource_id");
    private double dropChance;

    public static boolean isMysticResource(ItemStack itemStack) {
        return itemStack != null && itemStack.getPersistentDataContainer().has(key);
    }
    private MysticResource(String name, String description, double worth, ItemStack itemStack, double dropChance, ResourceTiers tier) {
        this.name = name;
        this.tier = tier;
        this.worth = worth;
        this.description = description;
        this.dropChance = dropChance;
        this.itemStack = itemStack.clone();
        this.id = this.name.toLowerCase().replace(" ", "_").replace("'", "");

        this.itemStack.editMeta(meta -> {
            meta.displayName(mm.deserialize(Gradient.YELLOW + "<!i>" + this.name));
            meta.lore(List.of(
                    mm.deserialize("<!i><gradient:#6a3093:#a044ff>-----------------------------------------------------</gradient>"),
                    mm.deserialize(Gradient.YELLOW + "<!i>" + description),
                    mm.deserialize(Gradient.GREEN + "<!i>Worth: " + Gradient.PINK + worth + " Mystic Coins"),
                    mm.deserialize(Gradient.GREEN + "<!i>Tier: " + this.tier.getColorCode() + tier),
                    mm.deserialize(Gradient.GREEN + "<!i>Drop Chance: " + Gradient.AQUA + dropChance + "%"),
                    mm.deserialize("<dark_gray><!i>Right-Click to claim the worth"),
                    mm.deserialize(Gradient.RED + "<!i>Requires Mystic Insight Perk to claim"),
                    mm.deserialize("<gradient:#6a3093:#a044ff>-----------------------------------------------------</gradient>")
                    ));
        });
        this.itemStack.editPersistentDataContainer(container -> container.set(key, PersistentDataType.STRING, this.id));
    }
    private MysticResource(String name, String description, String gottenFrom, double worth, ItemStack itemStack, double dropChance, ResourceTiers tier) {
        this.name = name;
        this.tier = tier;
        this.worth = worth;
        this.description = description;
        this.dropChance = dropChance;
        this.itemStack = itemStack.clone();



        this.id = this.name.toLowerCase().replace(" ", "_").replace("'", "");
        this.itemStack.editMeta(meta -> {
            meta.displayName(mm.deserialize(Gradient.YELLOW + "<!i>" + this.name));
            meta.lore(List.of(
                    mm.deserialize("<!i><gradient:#6a3093:#a044ff>-----------------------------------------------------</gradient>"),
                    mm.deserialize(Gradient.YELLOW + "<!i>" + description),
                    mm.deserialize(Gradient.YELLOW + "<!i>" + gottenFrom),
                    mm.deserialize(Gradient.GREEN + "<!i>Worth: " + Gradient.PINK + worth + " Mystic Coins"),
                    mm.deserialize(Gradient.GREEN + "<!i>Tier: " + this.tier.getColorCode() + tier),
                    mm.deserialize(Gradient.GREEN + "<!i>Drop Chance: " + Gradient.AQUA + dropChance + "%"),
                    mm.deserialize("<dark_gray><!i>Right-Click to claim the worth"),
                    mm.deserialize(Gradient.RED + "<!i>Requires Mystic Insight Perk to claim"),
                    mm.deserialize("<gradient:#6a3093:#a044ff>-----------------------------------------------------</gradient>")
                    ));
        });
        this.itemStack.editPersistentDataContainer(container -> container.set(key, PersistentDataType.STRING, this.id));
    }
    public static MysticResource register(String name, String description, double worth, ItemStack itemStack, double dropChance, ResourceTiers tier) {
        MysticResource resource = new MysticResource(name, description, worth, itemStack, dropChance, tier);
        resources.put(resource.getId(), resource);
        if (!ConfigSettings.ENABLE_MYSTIC_RESOURCES.getValue()) {
            resource.dropChance = 0;
        }
        return resource;
    }

    public ItemStack getItemStack() {
        return itemStack.clone();
    }

    public String getId() {
        return id;
    }

    public double getWorth() {
        return worth;
    }

    public static MysticResource get(ItemStack itemStack) {
        if (itemStack == null) return null;
        return resources.get(itemStack.getPersistentDataContainer().getOrDefault(key, PersistentDataType.STRING, "empty...data...null"));
    }

    public String getDescription() {
        return description;
    }

    public double getDropChance() {
        return dropChance;
    }

    public ResourceTiers getTier() {
        return tier;
    }

    public static List<MysticResource> getResources() {
        return new ArrayList<>(resources.values());
    }

    public static MysticResource get(String id) {
        return resources.get(id);
    }

    public boolean give(Player player) {
        return Helper.safeGiveItem(player, this.itemStack);
    }

    public String getName() {
        return name;
    }
}
