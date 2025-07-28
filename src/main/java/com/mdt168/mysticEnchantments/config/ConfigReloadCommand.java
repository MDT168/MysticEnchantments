package com.mdt168.mysticEnchantments.config;

import com.mdt168.mysticEnchantments.custom.Gradient;
import com.mdt168.mysticEnchantments.custom.Helper;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.jspecify.annotations.Nullable;

public class ConfigReloadCommand implements BasicCommand {

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] strings) {
        CommandSender sender = commandSourceStack.getSender();
        Config.reload();
        Helper.sendMessage(sender, Gradient.YELLOW + "Config Reloaded From File");
    }

    @Override
    public @Nullable String permission() {
        return "mysticenchantments.config.reload";
    }
}
