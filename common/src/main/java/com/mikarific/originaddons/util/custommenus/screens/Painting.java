package com.mikarific.originaddons.util.custommenus.screens;

import com.mikarific.originaddons.ui.Window;
import com.mikarific.originaddons.ui.components.UIComponent;
import com.mikarific.originaddons.ui.components.UITexture;
import com.mikarific.originaddons.util.custommenus.CustomScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class Painting extends CustomScreen {
    static boolean cosmosCosmetics = false;

    public static void init(Screen screen, Window window) {
        String FINISHES_ON = "쇼";
        String FINISHES_OFF = "쇽";
        cosmosCosmetics = screen.getTitle().getString().contains(FINISHES_ON) || screen.getTitle().getString().contains(FINISHES_OFF);

        Identifier TEXTURE = new Identifier("originaddons", "gui/custommenus/painting.png");
        int TEXTURE_WIDTH = 232;
        int TEXTURE_HEIGHT = 127;
        UIComponent box;
        if (cosmosCosmetics) {
            box = new UITexture(TEXTURE, (screen.width - 176) / 2, ((screen.height - 127) / 2) - 5, 176, 127, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(window);
        } else {
            window.includeInventory();
            box = new UITexture(TEXTURE, (screen.width - 176) / 2, ((screen.height - 123) / 2) - 5, 176, 123, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(window);
        }
    }

    public static void draw(Screen screen) {
        if (screen.getTitle().getString().contains("쌉")) System.out.println(1);
        if (screen.getTitle().getString().contains("쌊")) System.out.println(2);
        if (screen.getTitle().getString().contains("쌋")) System.out.println(3);
        if (screen.getTitle().getString().contains("쌌")) System.out.println(4);
        if (screen.getTitle().getString().contains("쌍")) System.out.println(5);
        if (screen.getTitle().getString().contains("쌎")) System.out.println(6);
    }

    public static boolean inventoryEnabled() {
        return !cosmosCosmetics;
    }

    public static List<Integer> getAllowedSlots() {
        if (cosmosCosmetics) return new ArrayList<>();
        List<Integer> allowedSlots = new ArrayList<>();
        allowedSlots.add(10);
        allowedSlots.add(16);
        return allowedSlots;
    }
}
