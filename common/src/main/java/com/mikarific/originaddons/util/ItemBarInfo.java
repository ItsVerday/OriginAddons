package com.mikarific.originaddons.util;

public class ItemBarInfo {
    private int color;
    private float fraction;

    public ItemBarInfo(int color, float fraction) {
        this.color = color;
        this.fraction = fraction;
    }

    public int getColor() {
        return color;
    }

    public float getFraction() {
        return fraction;
    }
}