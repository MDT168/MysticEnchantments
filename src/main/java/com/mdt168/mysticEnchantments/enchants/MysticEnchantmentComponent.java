package com.mdt168.mysticEnchantments.enchants;

import com.mdt168.mysticEnchantments.custom.BookTiers;

public interface MysticEnchantmentComponent {
    String getId();
    String getName();
    String getDescription();
    double getRollChance();
    BookTiers getBookTier();
}

