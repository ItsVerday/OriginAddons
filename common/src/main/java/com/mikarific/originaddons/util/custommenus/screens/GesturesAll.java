package com.mikarific.originaddons.util.custommenus.screens;

import com.mikarific.originaddons.ui.Window;
import com.mikarific.originaddons.ui.components.*;
import com.mikarific.originaddons.util.CustomMenus;
import com.mikarific.originaddons.util.custommenus.CustomScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.util.Identifier;

public class GesturesAll extends CustomScreen {
    public static void init(Screen screen, Window window) {
        assert MinecraftClient.getInstance().player != null;
        ScreenHandler screenHandler = MinecraftClient.getInstance().player.currentScreenHandler;
        
        Identifier TEXTURE = new Identifier("originaddons", "gui/custommenus/gestures_all.png");
        int TEXTURE_WIDTH = 248;
        int TEXTURE_HEIGHT = 124;
        UIComponent box = new UITexture(TEXTURE, (screen.width - 176) / 2, (screen.height - 124) / 2, 176, 124, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(window);
        //Back
        if (screen.getTitle().getString().contains("쉄")) {
            new UIButton(TEXTURE, 8, 9, 16, 14, 192, 0, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                CustomMenus.pickupItemAtSlot(0);
            }, (b, m, x, y) -> {
                screen.renderTooltip(m, CustomMenus.getDisplayTooltip(screenHandler.getSlot(0).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);
        } else {
            new UIButton(TEXTURE, 8, 9, 16, 14, 176, 0, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                CustomMenus.pickupItemAtSlot(0);
            }, (b, m, x, y) -> {
                screen.renderTooltip(m, CustomMenus.getDisplayTooltip(screenHandler.getSlot(0).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);
        }
        //Previous Page
        if (screen.getTitle().getString().contains("쉅")) {
            new UIButton(TEXTURE, 8, 101, 36, 14, 176, 56, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
                screen.renderTooltip(m, CustomMenus.getDisplayTooltip(screenHandler.getSlot(45).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);
        } else {
            new UIButton(TEXTURE, 8, 101, 36, 14, 176, 28, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                CustomMenus.pickupItemAtSlot(45);
            }, (b, m, x, y) -> {
                screen.renderTooltip(m, CustomMenus.getDisplayTooltip(screenHandler.getSlot(45).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);
        }
        //Next Page
        if (screen.getTitle().getString().contains("쉆")) {
            new UIButton(TEXTURE, 132, 101, 36, 14, 212, 56, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
                screen.renderTooltip(m, CustomMenus.getDisplayTooltip(screenHandler.getSlot(52).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);
        } else {
            new UIButton(TEXTURE, 132, 101, 36, 14, 212, 28, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                CustomMenus.pickupItemAtSlot(52);
            }, (b, m, x, y) -> {
                screen.renderTooltip(m, CustomMenus.getDisplayTooltip(screenHandler.getSlot(52).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);
        }
        //Page Numbers
        new UIText(new LiteralText(screen.getTitle().getSiblings().get(1).getSiblings().get(0).getString()).setStyle(Style.EMPTY), 16777215, 80, 104).setChildOf(box);
        //Slot Buttons
        for (int i = 9; i < 45; i++) {
            int slot = i;
            int slotX = (8 + (slot * 18)) % 162;
            int slotY = 9 + (18 * (int) Math.floor(slot / 9.0));
            new UIItem(null, 0, 0, true, TEXTURE, slotX, slotY, 16, 16, slotX, slotY, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                CustomMenus.pickupItemAtSlot(slot);
            }, (b, m, x, y) -> {
                if (!screenHandler.getSlot(slot).getStack().getTranslationKey().equals("block.minecraft.air")) {
                    screen.renderTooltip(m, CustomMenus.getDisplayTooltip(screenHandler.getSlot(slot).getStack()), (int)x, (int)y);
                }
            }, false).setChildOf(box);
        }
        //Slots
        new UIText(screen.getTitle().getSiblings().get(0).getSiblings().get(1), 16777215, 8, -3).setChildOf(box);
    }
}
