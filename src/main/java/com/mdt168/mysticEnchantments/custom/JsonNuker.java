package com.mdt168.mysticEnchantments.custom;

import com.google.gson.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

public class JsonNuker {
    private static final String FOLDER_PATH = "C:/Modding/PluginsAndDatapacks/MysticEnchants/MysticEnchantments/src/main/resources/enchantment";

    public static void main(String[] args) {
        File folder = new File(FOLDER_PATH);
        if (!folder.isDirectory()) return;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (!file.getName().toLowerCase().endsWith(".json")) continue;
            try {
                String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
                JsonObject json = JsonParser.parseString(content).getAsJsonObject();
                if (json.has("description")) {
                    JsonElement description = json.get("description");
                    if (description.isJsonPrimitive()) {
                        String descText = description.getAsString();
                        json.add("description", processDescription(descText));
                    }
                }
                Files.write(file.toPath(), gson.toJson(json).getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                System.err.println("Error processing file: " + file.getName());
                e.printStackTrace();
            }
        }
    }

    private static JsonElement processDescription(String original) {
        String cleaned = original.replace("§r", "");
        String[] colorCodes = {"§c", "§d", "§a", "§b"};
        String firstColorCode = null;
        for (String code : colorCodes) {
            if (cleaned.contains(code)) {
                firstColorCode = code;
                break;
            }
        }
        if (firstColorCode != null) {
            for (String code : colorCodes) {
                cleaned = cleaned.replace(code, "");
            }
            JsonObject obj = new JsonObject();
            obj.addProperty("text", cleaned);
            obj.addProperty("color", getHexColor(firstColorCode));
            return obj;
        }
        return new JsonPrimitive(cleaned);
    }

    private static String getHexColor(String colorCode) {
        switch (colorCode) {
            case "§c": return "#ff0a0a";
            case "§d": return "#e20aff";
            case "§a": return "#1bff0a";
            case "§b": return "#0aefff";
            default: return "#000000"; // Fallback (shouldn't occur)
        }
    }
}