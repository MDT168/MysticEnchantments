package com.mdt168.mysticEnchantments.command;

import com.mdt168.mysticEnchantments.custom.Gradient;
import com.mdt168.mysticEnchantments.custom.Helper;
import com.mdt168.mysticEnchantments.enchants.MysticTicket;
import com.mdt168.mysticEnchantments.enchants.MysticTickets;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import org.bukkit.entity.Player;

import static io.papermc.paper.command.brigadier.Commands.*;

public class MysticTicketsCommand {
    public static LiteralCommandNode<CommandSourceStack> build() {
        return literal("mysticticket")
                .requires(source -> source.getSender().hasPermission("mysticenchantments.command.mysticticket"))
                .then(literal("add")
                        .then(literal("basic")
                                .then(argument("player", ArgumentTypes.player())
                                        .then(argument("amount", IntegerArgumentType.integer(1))
                                                .executes(ctx -> {
                                                    Player player = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                                                    int amount = ctx.getArgument("amount", Integer.class);
                                                    MysticTickets.BASIC_TICKETS.addTickets(player, amount);
                                                    Helper.sendMessage(player, Gradient.YELLOW.getGradient() + amount + " Basic Tickets</gradient> has been added to your tickets balance");
                                                    Helper.sendMessage(ctx.getSource().getSender(), Gradient.YELLOW.getGradient() + amount + " Basic Tickets</gradient> has been added to " + Gradient.YELLOW + player.getName() + "</gradient>'s tickets balance");
                                                    return 0;
                                                }))))
                        .then(literal("enhanced")
                                .then(argument("player", ArgumentTypes.player())
                                        .then(argument("amount", IntegerArgumentType.integer(1))
                                                .executes(ctx -> {
                                                    Player player = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                                                    int amount = ctx.getArgument("amount", Integer.class);
                                                    MysticTickets.ENHANCED_TICKETS.addTickets(player, amount);
                                                    Helper.sendMessage(player, Gradient.YELLOW.getGradient() + amount + " Enhanced Tickets</gradient> has been added to your tickets balance");
                                                    Helper.sendMessage(ctx.getSource().getSender(), Gradient.YELLOW.getGradient() + amount + " Enhanced Tickets</gradient> has been added to " + Gradient.YELLOW + player.getName() + "</gradient>'s tickets balance");
                                                    return 0;
                                                }))))
                        .then(literal("refined")
                                .then(argument("player", ArgumentTypes.player())
                                        .then(argument("amount", IntegerArgumentType.integer(1))
                                                .executes(ctx -> {
                                                    Player player = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                                                    int amount = ctx.getArgument("amount", Integer.class);
                                                    MysticTickets.REFINED_TICKETS.addTickets(player, amount);
                                                    Helper.sendMessage(player, Gradient.YELLOW.getGradient() + amount + " Refined Tickets</gradient> has been added to your tickets balance");
                                                    Helper.sendMessage(ctx.getSource().getSender(), Gradient.YELLOW.getGradient() + amount + " Refined Tickets</gradient> has been added to " + Gradient.YELLOW + player.getName() + "</gradient>'s tickets balance");
                                                    return 0;
                                                }))))
                        .then(literal("elite")
                                .then(argument("player", ArgumentTypes.player())
                                        .then(argument("amount", IntegerArgumentType.integer(1))
                                                .executes(ctx -> {
                                                    Player player = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                                                    int amount = ctx.getArgument("amount", Integer.class);
                                                    MysticTickets.ELITE_TICKETS.addTickets(player, amount);
                                                    Helper.sendMessage(player, Gradient.YELLOW.getGradient() + amount + " Elite Tickets</gradient> has been added to your tickets balance");
                                                    Helper.sendMessage(ctx.getSource().getSender(), Gradient.YELLOW.getGradient() + amount + " Elite Tickets</gradient> has been added to " + Gradient.YELLOW + player.getName() + "</gradient>'s tickets balance");
                                                    return 0;
                                                }))))
                        .then(literal("mythic")
                                .then(argument("player", ArgumentTypes.player())
                                        .then(argument("amount", IntegerArgumentType.integer(1))
                                                .executes(ctx -> {
                                                    Player player = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                                                    int amount = ctx.getArgument("amount", Integer.class);
                                                    MysticTickets.MYTHIC_TICKETS.addTickets(player, amount);
                                                    Helper.sendMessage(player, Gradient.YELLOW.getGradient() + amount + " Mythic Tickets</gradient> has been added to your tickets balance");
                                                    Helper.sendMessage(ctx.getSource().getSender(), Gradient.YELLOW.getGradient() + amount + " Mythic Tickets</gradient> has been added to " + Gradient.YELLOW + player.getName() + "</gradient>'s tickets balance");
                                                    return 0;
                                                })))))
                .then(literal("remove")
                        .then(literal("basic")
                                .then(argument("player", ArgumentTypes.player())
                                        .then(argument("amount", IntegerArgumentType.integer(1))
                                                .executes(ctx -> {
                                                    Player player = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                                                    int amount = Math.min(MysticTickets.BASIC_TICKETS.getTickets(player), ctx.getArgument("amount", Integer.class));
                                                    MysticTickets.BASIC_TICKETS.removeTickets(player, amount);
                                                    Helper.sendMessage(player, Gradient.YELLOW.getGradient() + amount + " Basic Tickets</gradient> has been removed from your tickets balance");
                                                    Helper.sendMessage(ctx.getSource().getSender(), Gradient.YELLOW.getGradient() + amount + " Basic Tickets</gradient> has been removed from " + Gradient.YELLOW + player.getName() + "</gradient>'s tickets balance");
                                                    return 0;
                                                }))))
                        .then(literal("enhanced")
                                .then(argument("player", ArgumentTypes.player())
                                        .then(argument("amount", IntegerArgumentType.integer(1))
                                                .executes(ctx -> {
                                                    Player player = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                                                    int amount = Math.min(MysticTickets.BASIC_TICKETS.getTickets(player), ctx.getArgument("amount", Integer.class));
                                                    MysticTickets.ENHANCED_TICKETS.removeTickets(player, amount);
                                                    Helper.sendMessage(player, Gradient.YELLOW.getGradient() + amount + " Enhanced Tickets</gradient> has been removed from your tickets balance");
                                                    Helper.sendMessage(ctx.getSource().getSender(), Gradient.YELLOW.getGradient() + amount + " Enhanced Tickets</gradient> has been removed from " + Gradient.YELLOW + player.getName() + "</gradient>'s tickets balance");
                                                    return 0;
                                                }))))
                        .then(literal("refined")
                                .then(argument("player", ArgumentTypes.player())
                                        .then(argument("amount", IntegerArgumentType.integer(1))
                                                .executes(ctx -> {
                                                    Player player = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                                                    int amount = Math.min(MysticTickets.REFINED_TICKETS.getTickets(player), ctx.getArgument("amount", Integer.class));
                                                    MysticTickets.REFINED_TICKETS.removeTickets(player, amount);
                                                    Helper.sendMessage(player, Gradient.YELLOW.getGradient() + amount + " Refined Tickets</gradient> has been removed from your tickets balance");
                                                    Helper.sendMessage(ctx.getSource().getSender(), Gradient.YELLOW.getGradient() + amount + " Refined Tickets</gradient> has been removed from " + Gradient.YELLOW + player.getName() + "</gradient>'s tickets balance");
                                                    return 0;
                                                }))))
                        .then(literal("elite")
                                .then(argument("player", ArgumentTypes.player())
                                        .then(argument("amount", IntegerArgumentType.integer(1))
                                                .executes(ctx -> {
                                                    Player player = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                                                    int amount = Math.min(MysticTickets.ELITE_TICKETS.getTickets(player), ctx.getArgument("amount", Integer.class));
                                                    MysticTickets.ELITE_TICKETS.removeTickets(player, amount);
                                                    Helper.sendMessage(player, Gradient.YELLOW.getGradient() + amount + " Elite Tickets</gradient> has been removed from your tickets balance");
                                                    Helper.sendMessage(ctx.getSource().getSender(), Gradient.YELLOW.getGradient() + amount + " Elite Tickets</gradient> has been removed from " + Gradient.YELLOW + player.getName() + "</gradient>'s tickets balance");
                                                    return 0;
                                                }))))
                        .then(literal("mythic")
                                .then(argument("player", ArgumentTypes.player())
                                        .then(argument("amount", IntegerArgumentType.integer(1))
                                                .executes(ctx -> {
                                                    Player player = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                                                    int amount = Math.min(MysticTickets.MYTHIC_TICKETS.getTickets(player), ctx.getArgument("amount", Integer.class));
                                                    MysticTickets.MYTHIC_TICKETS.removeTickets(player, amount);
                                                    Helper.sendMessage(player, Gradient.YELLOW.getGradient() + amount + " Mythic Tickets</gradient> has been removed from your tickets balance");
                                                    Helper.sendMessage(ctx.getSource().getSender(), Gradient.YELLOW.getGradient() + amount + " Mythic Tickets</gradient> has been removed from " + Gradient.YELLOW + player.getName() + "</gradient>'s tickets balance");
                                                    return 0;
                                                })))))
                .then(literal("set")
                        .then(literal("basic")
                                .then(argument("player", ArgumentTypes.player())
                                        .then(argument("amount", IntegerArgumentType.integer(0))
                                                .executes(ctx -> {
                                                    Player player = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                                                    int amount = ctx.getArgument("amount", Integer.class);
                                                    MysticTickets.BASIC_TICKETS.setTickets(player, amount);
                                                    Helper.sendMessage(player, Gradient.YELLOW + "Your Basic Tickets</gradient> balance has been set to " + Gradient.YELLOW + amount + "</gradient>");
                                                    Helper.sendMessage(ctx.getSource().getSender(), Gradient.YELLOW + player.getName() + "'s Basic Tickets</gradient> balance has been set to " + Gradient.YELLOW + amount);

                                                    return 0;
                                                }))))
                        .then(literal("enhanced")
                                .then(argument("player", ArgumentTypes.player())
                                        .then(argument("amount", IntegerArgumentType.integer(0))
                                                .executes(ctx -> {
                                                    Player player = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                                                    int amount = ctx.getArgument("amount", Integer.class);
                                                    MysticTickets.ENHANCED_TICKETS.setTickets(player, amount);
                                                    Helper.sendMessage(player, Gradient.YELLOW + "Your Enhanced Tickets</gradient> balance has been set to " + Gradient.YELLOW + amount + "</gradient>");
                                                    Helper.sendMessage(ctx.getSource().getSender(), Gradient.YELLOW + player.getName() + "'s Enhanced Tickets</gradient> balance has been set to " + Gradient.YELLOW + amount);
                                                    return 0;
                                                }))))
                        .then(literal("refined")
                                .then(argument("player", ArgumentTypes.player())
                                        .then(argument("amount", IntegerArgumentType.integer(0))
                                                .executes(ctx -> {
                                                    Player player = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                                                    int amount = ctx.getArgument("amount", Integer.class);
                                                    MysticTickets.REFINED_TICKETS.setTickets(player, amount);
                                                    Helper.sendMessage(player, Gradient.YELLOW + "Your Refined Tickets</gradient> balance has been set to " + Gradient.YELLOW + amount + "</gradient>");
                                                    Helper.sendMessage(ctx.getSource().getSender(), Gradient.YELLOW + player.getName() + "'s Refined Tickets</gradient> balance has been set to " + Gradient.YELLOW + amount);
                                                    return 0;
                                                }))))
                        .then(literal("elite")
                                .then(argument("player", ArgumentTypes.player())
                                        .then(argument("amount", IntegerArgumentType.integer(0))
                                                .executes(ctx -> {
                                                    Player player = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                                                    int amount = ctx.getArgument("amount", Integer.class);
                                                    MysticTickets.ELITE_TICKETS.setTickets(player, amount);
                                                    Helper.sendMessage(player, Gradient.YELLOW + "Your Elite Tickets</gradient> balance has been set to " + Gradient.YELLOW + amount + "</gradient>");
                                                    Helper.sendMessage(ctx.getSource().getSender(), Gradient.YELLOW + player.getName() + "'s Elite Tickets</gradient> balance has been set to " + Gradient.YELLOW + amount);
                                                    return 0;
                                                }))))
                        .then(literal("mythic")
                                .then(argument("player", ArgumentTypes.player())
                                        .then(argument("amount", IntegerArgumentType.integer(0))
                                                .executes(ctx -> {
                                                    Player player = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                                                    int amount = ctx.getArgument("amount", Integer.class);
                                                    MysticTickets.MYTHIC_TICKETS.setTickets(player, amount);
                                                    Helper.sendMessage(player, Gradient.YELLOW + "Your Mythic Tickets</gradient> balance has been set to " + Gradient.YELLOW + amount + "</gradient>");
                                                    Helper.sendMessage(ctx.getSource().getSender(), Gradient.YELLOW + player.getName() + "'s Mythic Tickets</gradient> balance has been set to " + Gradient.YELLOW + amount);
                                                    return 0;
                                                })))))
                .then(literal("check")
                        .then(literal("basic")
                                .then(argument("player", ArgumentTypes.player())
                                                .executes(ctx -> {
                                                    Player player = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                                                    Helper.sendMessage(ctx.getSource().getSender(), Gradient.YELLOW + player.getName() + "</gradient> has " + Gradient.YELLOW + MysticTickets.BASIC_TICKETS.getTickets(player) + " Basic Tickets");
                                                    return 0;
                                                })))
                        .then(literal("enhanced")
                                .then(argument("player", ArgumentTypes.player())
                                                .executes(ctx -> {
                                                    Player player = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                                                    Helper.sendMessage(ctx.getSource().getSender(), Gradient.YELLOW + player.getName() + "</gradient> has " + Gradient.YELLOW + MysticTickets.ENHANCED_TICKETS.getTickets(player) + " Enhanced Tickets");
                                                    return 0;
                                                })))
                        .then(literal("refined")
                                .then(argument("player", ArgumentTypes.player())
                                                .executes(ctx -> {
                                                    Player player = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                                                    Helper.sendMessage(ctx.getSource().getSender(), Gradient.YELLOW + player.getName() + "</gradient> has " + Gradient.YELLOW + MysticTickets.REFINED_TICKETS.getTickets(player) + " Refined Tickets");
                                                    return 0;
                                                })))
                        .then(literal("elite")
                                .then(argument("player", ArgumentTypes.player())
                                                .executes(ctx -> {
                                                    Player player = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                                                    Helper.sendMessage(ctx.getSource().getSender(), Gradient.YELLOW + player.getName() + "</gradient> has " + Gradient.YELLOW + MysticTickets.ELITE_TICKETS.getTickets(player) + " Elite Tickets");
                                                    return 0;
                                                })))
                        .then(literal("mythic")
                                .then(argument("player", ArgumentTypes.player())
                                                .executes(ctx -> {
                                                    Player player = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                                                    Helper.sendMessage(ctx.getSource().getSender(), Gradient.YELLOW + player.getName() + "</gradient> has " + Gradient.YELLOW + MysticTickets.MYTHIC_TICKETS.getTickets(player) + " Mythic Tickets");
                                                    return 0;
                                                })))
                       ).build();
    }
}
