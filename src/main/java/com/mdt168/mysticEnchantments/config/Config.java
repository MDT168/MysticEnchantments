package com.mdt168.mysticEnchantments.config;

import com.mdt168.mysticEnchantments.MysticEnchantments;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

@SuppressWarnings("unchecked")
public class Config {

    private static File file;
    private static final DumperOptions options = new DumperOptions();
    private static Yaml yaml;
    private static Map<String, Object> data = new LinkedHashMap<>();
    private Config() {}
    public static void init(File dataFolder, String fileName) {
        file = new File(dataFolder, fileName);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setIndent(2);
        yaml = new Yaml(options);


        try {
            if (file.exists()) {
                try (InputStream input = new FileInputStream(file)) {
                    data = yaml.load(input);
                    if (data == null) {
                        data = new LinkedHashMap<>();
                    }
                }
            }

            for (ConfigSetting<?> setting : ConfigSetting.getConfigs()) {
                if (!data.containsKey(setting.getKey())) {
                    data.put(setting.getKey(), setting.getDefaultValue());
                }
            }

            save();
            load();
            addComments();
        } catch (IOException e) {
            MysticEnchantments.getLoggerStatic().severe("Failed to load/save Config: " + e.getMessage());
        }
    }

    private static void addComments() {
        List<String> lines = readLines();
        List<ConfigSetting<?>> settings = ConfigSetting.getConfigs();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (ConfigSetting<?> setting : settings) {
                if (line.replace(" ", "").startsWith(setting.getKey())) {
                    if (setting.getValue() instanceof List) {
                        lines.set(i, "# " + setting.getComment() + "\n" + line);
                    } else {
                        lines.set(i, "# " + setting.getComment() + "\n" + line + "\n");
                    }
                }
            }
        }
        writeLines(lines);
    }

    private static List<String> readLines() {
        try {
            return Files.readAllLines(file.toPath());
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
    private static void writeLines(List<String> lines) {
        try {
            Files.write(file.toPath(), lines);
        } catch (IOException ignored) {
        }
    }

    public static void loadData() {
        try (FileInputStream stream = new FileInputStream(file)) {
            data = yaml.load(stream);
            if (data == null) data = new HashMap<>();
        } catch (Exception e) {
            MysticEnchantments.getLoggerStatic().severe("Failed to load data from config");
        }
    }

    public static void reload() {
        try (FileInputStream input = new FileInputStream(file)) {
            data = yaml.load(input);
            if (data == null) data = new HashMap<>();
        } catch (IOException e) {
            data = new HashMap<>();
        }

        // Also refresh all registered ConfigSetting values
        for (ConfigSetting<?> setting : ConfigSetting.getConfigs()) {
            setting.reload();
        }
    }



    public static void save() {
        if (file == null) throw new IllegalStateException("Config not initialized. Call init() first.");
        try (FileWriter writer = new FileWriter(file)) {
            yaml.dump(data, writer);
        } catch (IOException e) {
            MysticEnchantments.getLoggerStatic().severe("Unable to save data to config: " + e.getMessage());
        }
    }

    public static <T> T getOrDefault(String key, T def) {
        Object value = data.get(key);
        if (value == null) {
            return def; // Return default if key doesn't exist
        }

        // Handle cases where the stored value is already the correct type
        if (def != null && def.getClass().isInstance(value)) {
            return (T) value;
        }

        // Convert from string representation if needed
        try {
            if (def instanceof Integer) {
                return (T) Integer.valueOf(value.toString());
            } else if (def instanceof Double) {
                return (T) Double.valueOf(value.toString());
            } else if (def instanceof Boolean) {
                return (T) Boolean.valueOf(value.toString());
            } else if (def instanceof String) {
                return (T) value.toString();
            } else if (def instanceof List && value instanceof List) {
                return (T) value;
            }
        } catch (NumberFormatException e) {
            MysticEnchantments.getLoggerStatic().warning(
                    "Unexpected value '" + value + "' for key '" + key + "' in the config! Using Default Value: " + def
            );
        }

        return def; // Fallback to default if conversion fails
    }

    public static void load() {
        for (ConfigSetting<?> setting : ConfigSetting.getConfigs()) {
            setting.reload();
        }
    }

    public static void set(String key, Object value) {
        data.put(key, value);
    }

    public static boolean contains(String key) {
        return data.containsKey(key);
    }

    public static void remove(String key) {
        data.remove(key);
    }
}
