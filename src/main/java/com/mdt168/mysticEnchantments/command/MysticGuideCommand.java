package com.mdt168.mysticEnchantments.command;

import com.mdt168.mysticEnchantments.custom.dataUtils.Guis;
import com.mdt168.mysticEnchantments.custom.extras.Guide;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MysticGuideCommand implements BasicCommand {
    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] strings) {
        CommandSender commandSender = commandSourceStack.getSender();
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage("Only players can use this command!");
            return;
        }
        player.openInventory(Guide.getGuide());
    }
}
