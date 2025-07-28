package com.mdt168.mysticEnchantments.command;

import com.mdt168.mysticEnchantments.custom.Gradient;
import com.mdt168.mysticEnchantments.custom.Helper;
import com.mdt168.mysticEnchantments.custom.PlayerDataUtils;
import com.mdt168.mysticEnchantments.enchants.HumaneEnchantment;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
@SuppressWarnings("UnstableApiUsage")
public class HumaneEnchantCommand {
    public static LiteralCommandNode<CommandSourceStack> build() {
        return Commands.literal("humanenchant")
                .requires(source -> source.getSender().hasPermission("mysticenchantments.command.humanenchant"))
                .then(Commands.argument("player", ArgumentTypes.player())
                        .then(Commands.argument("id", StringArgumentType.string())
                                .suggests(((context, builder) -> {
                                    for (HumaneEnchantment enchantment : HumaneEnchantment.getRegistries())
                                        builder.suggest(enchantment.getId());

                                    return builder.buildFuture();
                                }))
                                .executes(ctx -> {
                                    CommandSender sender = ctx.getSource().getSender();
                                    Player player = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                                    String id = ctx.getArgument("id", String.class);
                                    HumaneEnchantment enchantment = HumaneEnchantment.fromId(id);
                                    if (enchantment == null) {
                                        Helper.sendWarningMessage(ctx.getSource().getSender(), "No Enchantment found with the id: <yellow>" + id);
                                        return 0;
                                    }
                                    if (Helper.enchantmentExists(player, enchantment)) {
                                        Helper.sendWarningMessage(sender, "Failed! " + Gradient.YELLOW + player.getName() + "</gradient> already has " + Gradient.YELLOW + enchantment.getName());
                                    } else {
                                        PlayerDataUtils.addDataToPlayer(player, enchantment.getId());
                                        Helper.sendMessage(sender, Gradient.YELLOW + player.getName() + "</gradient> has been enchanted with " + Gradient.YELLOW + enchantment.getName() + "</gradient>!");
                                        Helper.sendMessage(player, "<green>you have been enchanted with <yellow>" + enchantment.getName() + "<green>!");
                                    }

                                    return 0;
                                }))).build();
    }
}
