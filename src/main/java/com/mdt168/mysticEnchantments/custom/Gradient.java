package com.mdt168.mysticEnchantments.custom;

public enum Gradient {
    RED("#BE2121:#FF7797"),
    YELLOW("#FFE259:#FFA751"),
    AQUA("#21B6BE:#77BCFF"),
    BLUE("#54daf4:#545eb6"),
    GREEN("#54F487:#17FF57"),
    PINK("#fa709a:#fbc2eb");
    private final String gradient;
    Gradient(String gradient) {
        this.gradient = "<gradient:" + gradient + ">";
    }

    public String getGradient() {
        return gradient;
    }

    @Override
    public String toString() {
        return gradient;
    }
}
