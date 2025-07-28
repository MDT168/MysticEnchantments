package com.mdt168.mysticEnchantments.pages;

import com.mdt168.mysticEnchantments.custom.BookTiers;

public class PagedGuis {
    public static final PagedEnchantmentsGui BASIC = PagedEnchantmentsGui.register(BookTiers.BASIC);
    public static final PagedEnchantmentsGui ENHANCED = PagedEnchantmentsGui.register(BookTiers.ENHANCED);
    public static final PagedEnchantmentsGui REFINED = PagedEnchantmentsGui.register(BookTiers.REFINED);
    public static final PagedEnchantmentsGui ELITE = PagedEnchantmentsGui.register(BookTiers.ELITE);
    public static final PagedEnchantmentsGui MYTHIC = PagedEnchantmentsGui.register(BookTiers.MYTHIC);
    public static final PagedMysticRecipeGui RECIPE = new PagedMysticRecipeGui();
}
