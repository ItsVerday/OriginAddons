package com.mikarific.originaddons.util.custommenus.screens;

import com.mikarific.originaddons.OriginAddons;
import com.mikarific.originaddons.ui.Window;
import com.mikarific.originaddons.ui.components.UIButton;
import com.mikarific.originaddons.ui.components.UIComponent;
import com.mikarific.originaddons.ui.components.UIItem;
import com.mikarific.originaddons.ui.components.UITexture;
import com.mikarific.originaddons.util.custommenus.CustomMenus;
import com.mikarific.originaddons.util.custommenus.CustomScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;

public class Painting extends CustomScreen {
    private static boolean cosmosCosmetics = false;

    private static final Map<Integer, UIItem> items = new HashMap<>();

    private static final int TEXTURE_WIDTH = 232;
    private static final int TEXTURE_HEIGHT = 127;

    public static void init(Screen screen, Window window) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        ScreenHandler screenHandler = player.currentScreenHandler;

        String FINISHES_ON = "쇼";
        String FINISHES_OFF = "쇽";
        boolean finishesOn = screen.getTitle().getString().contains(FINISHES_ON);
        boolean finishesOff = screen.getTitle().getString().contains(FINISHES_OFF);
        cosmosCosmetics = finishesOn || finishesOff;

        Identifier TEXTURE = new Identifier("originaddons", "gui/custommenus/painting.png");
        UIComponent box;
        if (cosmosCosmetics) {
            box = new UITexture(TEXTURE, (screen.width - 176) / 2, ((screen.height - 127) / 2) - 5, 176, 127, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(window);
            new UIButton(TEXTURE, 74, 78, 28, 15, 176 + (finishesOn ? 28 : 0), 0, 15, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                CustomMenus.pickupItemAtSlot(31);
            }, (b, m, x, y) -> {
                screen.renderTooltip(m, CustomMenus.getDisplayTooltip(screenHandler.getSlot(31).getStack()), (int) x, (int) y);
            }, false).setChildOf(box);
        } else {
            window.includeInventory();
            box = new UITexture(TEXTURE, (screen.width - 176) / 2, ((screen.height - 123) / 2) - 5, 176, 123, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(window);
        }

        String title = screen.getTitle().getString();
        int selectedX, selectedY = -1;
        String slotType = null;

        if (title.contains("쌉")) {
            slotType = "쌉";
            selectedY = 0;
        } else if (title.contains("쌊")) {
            slotType = "쌊";
            selectedY = 1;
        } else if (title.contains("쌋")) {
            slotType = "쌋";
            selectedY = 2;
        }

        if (slotType != null) {
            if (title.contains("\uF82A\uF829\uF826" + slotType)) {
                selectedX = 3;
            } else if (title.contains("\uF82B\uF828" + slotType)) {
                selectedX = 4;
            } else {
                selectedX = 5;
            }

            addSelectionMarker(selectedX, selectedY, TEXTURE, box);
        } else {
            for (int s = 37; s < 44; s++) {
                items.remove(s);
            }
        }

        int selectedX2 = 0;
        if (title.contains("\uF829\uF822쌍")) {
            selectedX2 = 1;
        } else if (title.contains("\uF82A\uF824쌍")) {
            selectedX2 = 2;
        } else if (title.contains("\uF82A\uF829\uF826쌍")) {
            selectedX2 = 3;
        } else if (title.contains("\uF82B\uF828쌍")) {
            selectedX2 = 4;
        } else if (title.contains("\uF82B\uF829\uF828\uF822쌍")) {
            selectedX2 = 5;
        } else if (title.contains("\uF82B\uF82A\uF828\uF824쌍")) {
            selectedX2 = 6;
        } else if (title.contains("\uF82B\uF82A\uF829\uF828\uF826쌍")) {
            selectedX2 = 7;
        }

        if (selectedX2 > 0) {
            addSelectionMarker(selectedX2, 4, TEXTURE, box);
        }

        for (int x = 3; x < 6; x++) {
            for (int y = 0; y < 3; y++) {
                addUIItem(x, y, TEXTURE, screen, screenHandler, box);
            }
        }

        for (int x = 1; x < 8; x++) {
            addUIItem(x, 4, TEXTURE, screen, screenHandler, box);
        }

        if (cosmosCosmetics) {
            addUIItem(1, 1, TEXTURE, screen, screenHandler, box);
            addUIItem(7, 1, TEXTURE, screen, screenHandler, box);
        }

        OriginAddons.LOGGER.info(Arrays.toString(title.getBytes(StandardCharsets.UTF_8)));
    }

    private static void addUIItem(int x, int y, Identifier texture, Screen screen, ScreenHandler handler, UIComponent box) {
        int slot = x + y * 9;
        int slotX = 8 + (x * 18);
        int slotY = 7 + (18 * (y + 1));

        ItemStack previousItem = null;
        if (items.containsKey(slot)) {
            previousItem = items.get(slot).getStack();
        }

        items.put(slot, (UIItem) new UIItem(previousItem, 0, 0, true, texture, slotX, slotY, 16, 16, 176, 64, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            CustomMenus.pickupItemAtSlot(slot);
        }, (b, m, x_, y_) -> {
            ItemStack stack = handler.getSlot(slot).getStack();
            if (!stack.getItem().equals(Items.AIR)) {
                screen.renderTooltip(m, CustomMenus.getDisplayTooltip(stack), (int) x_, (int) y_);
            }
        }, false).setChildOf(box));
    }

    private static void addSelectionMarker(int x, int y, Identifier texture, UIComponent box) {
        int elementX = 6 + (x * 18);
        int elementY = 5 + (18 * (y + 1));

        new UITexture(texture, elementX, elementY, 20, 20, 176, 32, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(box);
    }

    public static void draw(Screen screen) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        ScreenHandler screenHandler = player.currentScreenHandler;

        items.forEach((slotNumber, item) -> {
            Slot slot = screenHandler.slots.get(slotNumber);
            if (!slot.getStack().getItem().equals(Items.AIR)) {
                item.setStack(slot.getStack());
            } else if (!cosmosCosmetics) {
                item.setStack(null);
            }
        });
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
