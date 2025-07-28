package com.mdt168.mysticEnchantments.custom.dataUtils;

import com.mdt168.mysticEnchantments.MysticEnchantments;
import com.mdt168.mysticEnchantments.custom.PlayerDataUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.print.DocFlavor;
import java.util.UUID;

public class EntityDataUtils {
    private static final NamespacedKey key = new NamespacedKey(MysticEnchantments.getInstance(), "entity_mystic_enchantments_key");
    private static final NamespacedKey ownerKey = new NamespacedKey(MysticEnchantments.getInstance(), "entity_owner_key");

    public static <T, C> void setData(Entity entity, PersistentDataType<T, C> type, C data) {
        entity.getPersistentDataContainer().set(key, type, data);
    }
    public static void setData(Entity entity, String data) {
        entity.getPersistentDataContainer().set(key, PersistentDataType.STRING, data);
    }

    public static void setOwner(Entity entity, Player owner) {
        entity.getPersistentDataContainer().set(ownerKey, PersistentDataType.STRING, owner.getUniqueId().toString());
    }

    public static Player getOwner(Entity entity) {
        String sUuid = entity.getPersistentDataContainer().get(ownerKey, PersistentDataType.STRING);
        if (sUuid != null) {
            UUID uuid = UUID.fromString(sUuid);
            return MysticEnchantments.getStaticServer().getPlayer(uuid);
        }
        return null;
    }

    public static boolean hasData(Entity entity, String data) {
        if (data.isEmpty()) return false;
        return entity.getPersistentDataContainer().getOrDefault(key, PersistentDataType.STRING, "").equals(data);
    }
}
