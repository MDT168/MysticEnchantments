package com.mdt168.mysticEnchantments.custom.pluginoptions;

import com.mdt168.mysticEnchantments.config.ConfigSettings;
import com.mdt168.mysticEnchantments.custom.Helper;
import com.mdt168.mysticEnchantments.custom.PlayerDataUtils;
import com.mdt168.mysticEnchantments.enchants.HumaneEnchantment;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MysticOptions {
    public static final MysticOption PURITY_CRYSTAL = MysticOption.register(
            "Purity Crystal",
            "Makes The Soul Clean From Humane Enchantments",
            ConfigSettings.PURITY_CRYSTAL_PRICE,
            player -> {
                List<HumaneEnchantment> enchantments = PlayerDataUtils.getEnchantmentsOnPlayer(player);
                if (enchantments.isEmpty()) {
                    Helper.sendWarningMessage(player, "You don't have active Humane Enchantments");
                    return false;
                }
                int removed = 0;
                for (HumaneEnchantment enchantment : enchantments) {
                    if (Helper.giveBook(enchantment, player, true)) {
                        removed++;
                        PlayerDataUtils.removeData(player, enchantment.getId());
                    } else {
                        Helper.sendWarningMessage(player, removed == 0 ? "No Humane enchantments got remove due to full inventory" : "Some Humane Enchantments didn't get removed due to full inventory");
                        break;
                    }
                }
                return removed != 0;
            }
    );
    public static final MysticOption ANVIL_GUI = MysticOption.register(
            "Anvil",
            "Easier way to get an anvil for enchantments",
            ConfigSettings.ANVIL_PRICE,
            Material.ANVIL,
            player -> {
                return Helper.safeGiveItem(player, new ItemStack(Material.ANVIL));
            }
    );

    public static void init() {}
}
