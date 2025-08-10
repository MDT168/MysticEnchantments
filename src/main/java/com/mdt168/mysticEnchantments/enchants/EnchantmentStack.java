package com.mdt168.mysticEnchantments.enchants;

import com.mdt168.mysticEnchantments.MysticEnchantments;
import com.mdt168.mysticEnchantments.config.ConfigSetting;
import com.mdt168.mysticEnchantments.config.ConfigSettings;
import com.mdt168.mysticEnchantments.custom.BookTiers;
import com.mdt168.mysticEnchantments.custom.dataUtils.EnchantmentsUtils;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import net.kyori.adventure.key.Key;
import org.bukkit.enchantments.Enchantment;
import org.intellij.lang.annotations.Subst;

import java.util.logging.Logger;

public class EnchantmentStack implements MysticEnchantmentComponent {
    private final Enchantment enchantment;
    private final double chancePerLevel;
    private final boolean isNull;
    private static final Logger logger = MysticEnchantments.getLoggerStatic();
    private static final RegistryAccess access = RegistryAccess.registryAccess();
    private final String name, typeOfEnchantment, description, id;
    private final BookTiers bookTier;
    private EnchantmentStack(String name, String typeOfEnchantment, String description,double chancePerLevel, BookTiers bookTier) {
        this.id = name.toLowerCase().replace(" ", "_").replace("'", "");
        this.enchantment = access.getRegistry(RegistryKey.ENCHANTMENT).get(Key.key("mysticenchantments:" + this.id));
        this.isNull = this.enchantment == null;
        if (this.isNull)
            logger.severe( "mysticenchantments:" + this.id + " enchantment not found while registering");
        this.name = name;
        this.description = description;
        this.typeOfEnchantment = typeOfEnchantment;
        this.bookTier = bookTier;
        this.chancePerLevel = chancePerLevel;
    }
    private EnchantmentStack(@Subst("mysticenchantments") String namespace, String name, String typeOfEnchantment, String description, double chancePerLevel, BookTiers bookTier) {
        this.id = name.toLowerCase().replace(" ", "_").replace("'", "");
        this.enchantment = access.getRegistry(RegistryKey.ENCHANTMENT).get(Key.key( namespace + ":" + this.id));
        this.isNull = this.enchantment == null;
        if (this.isNull)
            logger.severe( namespace + ":" + this.id + " enchantment not found while registering from an addon");

        this.name = name;
        this.description = description;
        this.typeOfEnchantment = typeOfEnchantment;
        this.bookTier = bookTier;
        this.chancePerLevel = chancePerLevel;
    }
    public static EnchantmentStack register(String name, String typeOfEnchantment, String description,double chancePerLevel, BookTiers bookTier, boolean skipOnApiMode) {
        EnchantmentStack stack = new EnchantmentStack(name, typeOfEnchantment, description, chancePerLevel, bookTier);
        if (!BlockedUtils.isBlocked(stack) && !stack.isNull) if ((!(ConfigSettings.API_MODE.getValue() && skipOnApiMode))) {
            EnchantmentsUtils.addEnchantment(stack);
        } else MysticEnchantments.blockedContentFromApiMode++;        return stack;
    }
    public static EnchantmentStack registerFromNamespace(String namespace, String name, String typeOfEnchantment, String description,double chancePerLevel, BookTiers bookTier, boolean skipOnApiMode) {
        EnchantmentStack stack = new EnchantmentStack(namespace, name, typeOfEnchantment, description, chancePerLevel, bookTier);
        if (!BlockedUtils.isBlocked(stack) && !stack.isNull) if ((!(ConfigSettings.API_MODE.getValue() && skipOnApiMode))) {
            EnchantmentsUtils.addEnchantment(stack);
        } else MysticEnchantments.blockedContentFromApiMode++;
        return stack;
    }
    public static EnchantmentStack register(String name, String typeOfEnchantment, String description,double chancePerLevel, BookTiers bookTier) {
        EnchantmentStack stack = new EnchantmentStack(name, typeOfEnchantment, description, chancePerLevel, bookTier);
        if (!BlockedUtils.isBlocked(stack) && !stack.isNull) EnchantmentsUtils.addEnchantment(stack);
        return stack;
    }
    public static EnchantmentStack registerFromNamespace(String namespace, String name, String typeOfEnchantment, String description,double chancePerLevel, BookTiers bookTier) {
        EnchantmentStack stack = new EnchantmentStack(namespace, name, typeOfEnchantment, description, chancePerLevel, bookTier);
        if (!BlockedUtils.isBlocked(stack) && !stack.isNull) EnchantmentsUtils.addEnchantment(stack);
        return stack;
    }

    public String getId() {
        return id;
    }

    public double getChancePerLevel() {
        return chancePerLevel;
    }

    public double getRollChance() {
        return Math.round((100.0 / (bookTier.getEnchants().size() + bookTier.getHumaneEnchants().size())) * 100.0) / 100.0;
    }

    public BookTiers getBookTier() {
        return bookTier;
    }

    public Enchantment getEnchantment() {
        return enchantment;
    }

    public String getName() {
        return name;
    }

    public String getTypeOfEnchantment() {
        return typeOfEnchantment;
    }

    public String getDescription() {
        return description;
    }
}
