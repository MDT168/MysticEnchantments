package com.mdt168.mysticEnchantments.custom;

import com.mdt168.mysticEnchantments.enchants.EnchantmentStack;
import com.mdt168.mysticEnchantments.enchants.HumaneEnchantment;
import com.mdt168.mysticEnchantments.enchants.MysticEnchants;

import java.lang.reflect.Field;

public class EnchantmentsCounter {
    public static void main(String[] args) {
        Field[] fields = MysticEnchants.class.getDeclaredFields();
        int humaneCounter = 0;
        int enchantsCounter = 0;
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getType().equals(HumaneEnchantment.class)) humaneCounter++;
            if (field.getType().equals(EnchantmentStack.class)) enchantsCounter++;
        }
        System.out.println("Humane Enchantments: " + humaneCounter);
        System.out.println("Normal Enchantments: " + enchantsCounter);
        System.out.println("Total: " + (humaneCounter + enchantsCounter) + " (" + (Math.ceil((((humaneCounter + enchantsCounter) / 135f) * 100) * 100) / 100) + "%)");
    }
}
