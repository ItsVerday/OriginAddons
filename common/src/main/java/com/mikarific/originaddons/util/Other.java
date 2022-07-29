package com.mikarific.originaddons.util;

public class Other {
    public static double scrollOffset = 0.0;

    public static void changeScrollOffset(double amount) {
        scrollOffset += amount;
    }

    public static void resetScrollOffset() {
        scrollOffset = 0.0;
    }
}
