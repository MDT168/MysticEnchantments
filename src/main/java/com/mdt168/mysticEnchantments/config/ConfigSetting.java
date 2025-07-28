package com.mdt168.mysticEnchantments.config;

import com.mdt168.mysticEnchantments.MysticEnchantments;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ConfigSetting<T> {
    private static final List<ConfigSetting<?>> configs = new ArrayList<>();
    private final String name, key, comment;
    private T value;
    private final T defaultValue;
    private final int minValue;
    private ConfigSetting(String name, T defaultValue, String comment) {
        this.name = name;
        this.minValue = 0;
        String type = switch (defaultValue.getClass().getSimpleName()) {
            case "Integer" -> " - Whole Number - Minimum Value: " + minValue;
            case "Double" -> " - Decimals Number - Minimum Value: " + minValue;
            case "String" -> " - Text";
            case "Boolean" -> " - true/false";
            case "ArrayList" -> " - List";
            default -> "";
        };

        this.defaultValue = defaultValue;
        this.comment = comment + type + " - Default Value: " + defaultValue;
        this.key = this.name.toLowerCase().replace(" ", "-").replace("'", "");
        this.value = defaultValue;
    }
    private ConfigSetting(String name, T defaultValue, String comment, int minValue) {
        this.name = name;
        this.minValue = minValue;
        String type = switch (defaultValue.getClass().getSimpleName()) {
            case "Integer" -> " - Whole Number - Minimum Value: " + minValue;
            case "Double" -> " - Decimals Number - Minimum Value: " + minValue;
            case "String" -> " - Text";
            case "Boolean" -> " - true/false";
            case "ArrayList" -> " - List";
            default -> "";
        };
        this.defaultValue = defaultValue;
        this.comment = comment + type + " - Default Value: " + defaultValue;
        this.key = this.name.toLowerCase().replace(" ", "-").replace("'", "");
        this.value = defaultValue;
    }
    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public static <T> ConfigSetting<T> register(String name, T defaultValue, String comment) {
        ConfigSetting<T> setting = new ConfigSetting<>(name, defaultValue, comment);
        configs.add(setting);
        return setting;
    }
    public static <T> ConfigSetting<T> register(String name, T defaultValue, String comment, int minValue) {
        ConfigSetting<T> setting = new ConfigSetting<>(name, defaultValue, comment, minValue);
        configs.add(setting);
        return setting;
    }

    public void reload() {
        this.value = Config.getOrDefault(key, defaultValue);
        if (this.value instanceof Number number) if (minValue > number.doubleValue()) {
            this.value = defaultValue;
            MysticEnchantments.getLoggerStatic().log(Level.WARNING, "The Minimum value for '" + key + "' is " + minValue + ", Found: " + number.doubleValue() + ". Started using default value: " + defaultValue);
        }
    }

    public static List<ConfigSetting<?>> getConfigs() {
        return configs;
    }
    public void setValue(T value) {
        this.value = value;
        Config.set(key, value);
    }
    public T getValue() {
        return value;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public String getComment() {
        return comment;
    }
}