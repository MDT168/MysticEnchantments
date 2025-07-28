package com.mdt168.mysticEnchantments.custom.extras;

import com.mdt168.mysticEnchantments.custom.Gradient;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class Guide {
    private static final Inventory guideInventory = GuideBuilder.start()
            .add(
                    "<green>Getting Started with Mystic Enchantments",
                    "<gray>Welcome to Mystic Enchantments!",
                    "<yellow>This plugin adds <gold>120+ new enchantments</gold> across 6 tiers",
                    "<yellow>Start by collecting <#5e4fa2>Mystic Coins</#5e4fa2> and basic enchantments",
                    "<yellow>Use <white>/mysticenchanter</white> to access all features"
            )
            .add(
                    "<gold>Early Game Progression",
                    "<gray>Focus on these starting activities:",
                    "<yellow>• Kill mobs to earn <#5e4fa2>Mystic Souls</#5e4fa2>",
                    "<yellow>• Mine ores for <#5e4fa2>Mystic Fragments</#5e4fa2>",
                    "<yellow>• Chop trees for <#5e4fa2>Mystic Sap</#5e4fa2>",
                    "<yellow>• Convert resources into <gold>Mystic Coins</gold>",
                    "<yellow>• Obtain <white>Basic Tier</white> enchantments first"
            )
            .add(
                    "<gold>Humane Enchantments",
                    "<gray>A New Type of Enchantment Introduced:",
                    "<yellow>• Humane Enchantments are enchanted on the <#5e4fa2>player</#5e4fa2>",
                    "<yellow>• Right-Click While holding the enchantment to <#5e4fa2>Enchant yourself</#5e4fa2>",
                    "<yellow>• Humane Enchants can be removed and turned back into books through <#5e4fa2>Plugin Options</#5e4fa2>",
                    "<yellow>• Humane Enchantments on you can be viewed through Plugin Options <gold>Mystic Coins</gold>"
            )
            .add(
                    "<#5e4fa2>Mystic Effort Points",
                    "<gray>Your primary progression system:",
                    "<yellow>• Earn points through regular gameplay",
                    "<yellow>• Level up to increase coin rewards",
                    "<yellow>• Higher levels give <gold>coin multipliers</gold>",
                    "<yellow>• Check your progress through <white>/mysticenchanter</white>"
            )

            .add(
                    "<#5e4fa2>Mystic Effort Points Tips",
                    "<gray>Earn More Mystic Effort Points",
                    "<yellow>• The Harder and rarer the block, the more Mystic Effort Points you would earn",
                    "<yellow>• The More Damage you deal, the more you will earn",
                    "<yellow>• Focus on hard blocks as they give a lot of Effort points such as <gold>Obsidian</gold>",
                    "<yellow>• Get Prospector And Experiencer Enchantments as they boost efforts <white>income</white>"
            )

            .add(
                    "<#FFA500>Mid Game Advancement",
                    "<gray>Now that you have basic enchantments:",
                    "<yellow>• Aim for <light_purple>Enhanced</light_purple> and <blue>Refined</blue> tier enchants",
                    "<yellow>• Start saving coins for <gold>Elite Tier</gold> rolls",
                    "<yellow>• Focus more on <green>Humane Enchantments</green> for player buffs",
                    "<yellow>• Start aiming to get started with <white>Mystic Recipes</white> for useful and powerful items"
            )
            .add(
                    "<#AA00FF>Mystic Recipes",
                    "<gray>Introduced in v1.13.1+:",
                    "<yellow>• Craft Custom Items And Enchantments",
                    "<yellow>• Some Recipes consume Mystic Coins",
                    "<yellow>• Recipes unlock as you progress In Mystic Levels",
                    "<yellow>• Access via <white>/mysticenchanter</white>"
            )

            .add(
                    "<red>Late Game Mastery",
                    "<gray>For advanced players:",
                    "<yellow>• Pursue <dark_red>Mythic</dark_red> and <#4B0082>Eldritch</#4B0082> tier enchants",
                    "<yellow>• Farm <gold>Enchanted Crate Keys</gold> from elite activities",
                    "<yellow>• Optimize your <#5e4fa2>Mystic Effort</#5e4fa2> multipliers",
                    "<yellow>• Trade with other players for rare enchants"
            )

            .add(
                    "<gold>Enchanted Crates",
                    "<gray>The ultimate rewards:",
                    "<yellow>• Obtain keys from <light_purple>Elite Tier+</light_purple> enchantments",
                    "<yellow>• Each tool type has its own key enchant",
                    "<yellow>• Crates contain <gold>useful rewards</gold>",
                    "<yellow>• Open via <white>/mysticenchanter</white>"
            )

            .add(
                    "<aqua>Pro Tips & Strategies",
                    "<gray>Advanced gameplay advice:",
                    "<yellow>• Balance between rolling new enchants and saving coins",
                    "<yellow>• Specialize in specific tool types first",
                    "<yellow>• Humane Enchantments work in PvP",
                    "<yellow>• Higher tiers have <red>incompatibilities</red> - plan ahead"
            )
            .buildGuide();

    public static Inventory getGuide() {
        return guideInventory;
    }
}
