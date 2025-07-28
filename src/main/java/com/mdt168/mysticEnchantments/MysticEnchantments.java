package com.mdt168.mysticEnchantments;

import com.mdt168.mysticEnchantments.command.HumaneEnchantCommand;
import com.mdt168.mysticEnchantments.command.MysticEnchanterCommand;
import com.mdt168.mysticEnchantments.command.MysticGuideCommand;
import com.mdt168.mysticEnchantments.command.MysticSettingsCommand;
import com.mdt168.mysticEnchantments.config.Config;
import com.mdt168.mysticEnchantments.config.ConfigSettings;
import com.mdt168.mysticEnchantments.craft.MysticRecipes;
import com.mdt168.mysticEnchantments.custom.*;
import com.mdt168.mysticEnchantments.custom.pluginoptions.MysticOptions;
import com.mdt168.mysticEnchantments.enchants.MysticEnchants;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;
import java.util.logging.Logger;

public final class MysticEnchantments extends JavaPlugin {
    public static MysticEnchantments getInstance() {
        return instance;
    }
    private static MysticEnchantments instance;
    private static Server server;

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public void onEnable() {
        server = getServer();

        BasicCommand basicMysticEnchanter = new MysticEnchanterCommand();
        BasicCommand basicMysticSettings = new MysticSettingsCommand();


        LiteralCommandNode<CommandSourceStack> resourceGiverCommand = Helper.getMysticResourcesGiverCommand();
        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            Commands registrar = event.registrar();
            registrar.register(resourceGiverCommand);
            registrar.register(Helper.getEnchantedCrateCommand());

            registrar.register("mysticenchanter", basicMysticEnchanter);
            registrar.register("myse", basicMysticEnchanter);
            registrar.register("mysticguide", new MysticGuideCommand());
            registrar.register("mysenchanter", basicMysticEnchanter);
            registrar.register("menchanter", basicMysticEnchanter);
            registrar.register("mysticsettings", basicMysticSettings);
            registrar.register("mysesettings", basicMysticSettings);
            registrar.register("msettings", basicMysticSettings);
            registrar.register(HumaneEnchantCommand.build());
        });

        getServer().getPluginManager().registerEvents(new MysticEvents(), this);

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (Helper.enchantmentExists(player, MysticEnchants.RESTORATION)) {
                    int enchantmentLevel = Helper.getEnchantmentLevel(player, MysticEnchants.RESTORATION);
                    int chance = -50 + (50 * enchantmentLevel);
                    int repairAmount = Helper.rollChance(chance) ? 2 : 1;
                    Helper.repairTool(player, repairAmount);
                }
            }
        }, 20, 20);
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                Helper.tickEvent(player);
            }
        }, 1, 1);

        try {
            DB.createTable();
        } catch (SQLException e) {
            getLogger().severe("Failed to create database table: " + e.getMessage());
            getLogger().severe("Plugin got disabled due to database creation failed state");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onLoad() {
        instance = this;
        ConfigSettings.init();
        Config.init(getDataFolder(), "config.yml");
        MysticRecipes.init();
        MysticOptions.init();
    }

    public static Server getStaticServer() {
        return server;
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Inventory topInventory = player.getOpenInventory().getTopInventory();
            Helper.handleSaving(player, topInventory);
        }
        Config.save();
    }

    public static Logger getLoggerStatic() {
        return instance.getLogger();
    }









    public static File getDataFol() {
        return instance.getDataFolder();
    }
}
