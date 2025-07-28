package com.mdt168.mysticEnchantments.custom;

import java.io.File;
import java.nio.file.Files;

public class EnchantmentJsonMaker {
    public static void main(String[] args) {
        createFile("Eclipse Edge", """
                {
                  "anvil_cost": 1,
                  "description": {
                    "text": "Eclipse Edge",
                    "color": "#4B0082",
                    "bold": true
                  },
                  "max_cost": {
                    "base": 16,
                    "per_level_above_first": 10
                  },
                  "max_level": 1,
                  "min_cost": {
                    "base": 1,
                    "per_level_above_first": 10
                  },
                  "slots": [
                    "mainhand"
                  ],
                  "supported_items": "#minecraft:swords",
                  "weight": 10
                }""");
        System.out.println("Made");
    }
    private static final String PATH = "C:/Modding/PluginsAndDatapacks/MysticEnchants/MysticEnchantments/src/main/resources/MysticDatapack/data/mysticenchantments/enchantment/";
    private static void createFile(String enchantmentName, String json) {
        try {
            File file = new File(PATH + enchantmentName.toLowerCase().replace(" ", "_").replace("'", "") + ".json");
            if (file.createNewFile()) {
                Files.writeString(file.toPath(), json);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static void changeFile(String enchantmentName, String json) {
        try {
            File file = new File(PATH + enchantmentName.toLowerCase().replace(" ", "_").replace("'", "") + ".json");
            if (!file.createNewFile()) {
                Files.writeString(file.toPath(), json);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static void deleteFile(String enchantmentName) {
        try {
            File file = new File(PATH + enchantmentName.toLowerCase().replace(" ", "_").replace("'", "") + ".json");
            file.delete();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
