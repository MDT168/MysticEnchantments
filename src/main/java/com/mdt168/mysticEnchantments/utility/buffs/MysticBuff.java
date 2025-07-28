package com.mdt168.mysticEnchantments.utility.buffs;

import com.mdt168.mysticEnchantments.MysticEnchantments;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class MysticBuff {
    private final NamespacedKey key;
    private final String id;
    public MysticBuff(String id) {
        this.id = id.toLowerCase().replace(" ", "_").replace("'", "");
        this.key = new NamespacedKey(MysticEnchantments.getInstance(), this.id);
    }

    public boolean hasBuff(Player player) {
        return player.getPersistentDataContainer().getOrDefault(key, PersistentDataType.BOOLEAN, false);
    }
    public void giveBuff(Player player) {
        player.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);
    }
    public void revokeBuff(Player player) {
        player.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, false);
    }
}
