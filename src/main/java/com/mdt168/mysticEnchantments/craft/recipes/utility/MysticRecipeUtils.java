package com.mdt168.mysticEnchantments.craft.recipes.utility;

import com.mdt168.mysticEnchantments.craft.recipes.MysticRecipe;

import java.util.ArrayList;
import java.util.List;

public class MysticRecipeUtils {
    private static final List<MysticRecipe> recipes = new ArrayList<>();

    public static void add(MysticRecipe recipe) {
        recipes.add(recipe);
    }
    public static List<MysticRecipe> getRecipes() {
        return recipes;
    }
}
