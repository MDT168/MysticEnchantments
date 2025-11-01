package com.mdt168.mysticEnchantments.resources;

public enum ResourceTiers {
    COMMON("<#A9A9A9>"),
    UNCOMMON("<#32CD32>"),
    RARE("<#1E90FF>"),
    EPIC("<#9932CC>"),
    LEGENDARY("<#FFD700>"),
    MYTHIC("<gradient:#8A2BE2:#00BFFF>");

    private final String colorCode;

    ResourceTiers(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorCode() {
        return this.colorCode;
    }

    @Override
    public String toString() {
        String notFormatted = name();
        return notFormatted.charAt(0) + notFormatted.substring(1).toLowerCase();
    }
}
