package com.mdt168.mysticEnchantments.custom.dataUtils;

import com.mdt168.mysticEnchantments.MysticEnchantments;
import com.mdt168.mysticEnchantments.custom.Gradient;
import com.mdt168.mysticEnchantments.custom.Helper;
import com.mdt168.mysticEnchantments.custom.MysticCoinHandler;
import com.mdt168.mysticEnchantments.settings.Settings;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MysticBossBarUtils {
    private static final Map<UUID, BossBar> bossBars = new HashMap<>();

    public static void showBossBar(Player player, double addedEfforts) {
        if (!Settings.BOSS_BAR_SHOW.getValue(player)) return;
        if (bossBars.containsKey(player.getUniqueId())) {
            BossBar bar = bossBars.get(player.getUniqueId());
            bar.name(MiniMessage.miniMessage().deserialize(
                    Gradient.RED + "<b>Mystic Level</b> <green>" + MysticCoinHandler.getLevel(player) +
                            " <yellow>(<green>" + MysticCoinHandler.getEffortPoints(player) +
                            "</green>/<green>" + MysticCoinHandler.getNeededEfforts() +
                            "</green>) " + Gradient.PINK + "+" + addedEfforts
            ));
            bar.progress(MysticCoinHandler.getProgress(player, true));
            bossBars.put(player.getUniqueId(), bar);
            startRemoveTimer(player, bar);
        } else {
            BossBar bossBar = BossBar.bossBar(MiniMessage.miniMessage().deserialize(
                    Gradient.RED + "<b>Mystic Level</b> <green>" + MysticCoinHandler.getLevel(player) +
                            " <yellow>(<green>" + MysticCoinHandler.getEffortPoints(player) +
                            "</green>/<green>" + MysticCoinHandler.getNeededEfforts() +
                            "</green>) " + Gradient.PINK + "+" + addedEfforts
            ), MysticCoinHandler.getProgress(player, true), BossBar.Color.RED, BossBar.Overlay.PROGRESS);
            bossBars.put(player.getUniqueId(), bossBar);
            bossBar.addViewer(player);
            startRemoveTimer(player, bossBar);
        }
    }

    public static void startRemoveTimer(Player player, BossBar bossBar) {
        if (Helper.bossBarShowers.containsKey(player.getUniqueId())) Helper.bossBarShowers.get(player.getUniqueId()).cancel();
        Helper.bossBarShowers.put(player.getUniqueId(), Bukkit.getScheduler().runTaskLater(MysticEnchantments.getInstance(), () -> {
            bossBar.removeViewer(player);
            bossBars.remove(player.getUniqueId());
            Helper.bossBarShowers.remove(player.getUniqueId());
        }, Helper.secondsToTicks(3)));
    }
}
