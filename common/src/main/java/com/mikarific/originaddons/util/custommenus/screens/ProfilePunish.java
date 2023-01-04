package com.mikarific.originaddons.util.custommenus.screens;

import com.mikarific.originaddons.ui.Window;
import com.mikarific.originaddons.ui.components.UIButton;
import com.mikarific.originaddons.ui.components.UIComponent;
import com.mikarific.originaddons.ui.components.UITexture;
import com.mikarific.originaddons.util.custommenus.CustomMenus;
import com.mikarific.originaddons.util.custommenus.CustomScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.Identifier;

public class ProfilePunish extends CustomScreen {
    public static void init(Screen screen, Window window) {
        assert MinecraftClient.getInstance().player != null;
        ScreenHandler screenHandler = MinecraftClient.getInstance().player.currentScreenHandler;
        
        Identifier TEXTURE = new Identifier("originaddons", "gui/custommenus/profile_punish.png");
        int TEXTURE_WIDTH = 336;
        int TEXTURE_HEIGHT = 90;
        UIComponent box = new UITexture(TEXTURE, (screen.width - 176) / 2, (screen.height - 55) / 2, 176, 55, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(window);
        int chatV = 16;
        int chatVOffset = 16;
        int behaviorV = 16;
        int behaviorVOffset = 16;
        int modsV = 16;
        int modsVOffset = 16;
        int numbers = 0;
        if (CustomMenus.isProfilePunishChat(screen)) {
            chatV = 0;
            chatVOffset = 0;
            numbers = 8;
        }
        if (CustomMenus.isProfilePunishBehavior(screen)) {
            behaviorV = 0;
            behaviorVOffset = 0;
            numbers = 7;
        }
        if (CustomMenus.isProfilePunishMods(screen)) {
            modsV = 0;
            modsVOffset = 0;
            numbers = 7;
        }
        //Chat
        new UIButton(TEXTURE, 8, 11, 53, 16, 176, chatV, chatVOffset, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            CustomMenus.pickupItemAtSlot(0);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, CustomMenus.getDisplayTooltip(screenHandler.getSlot(0).getStack()), (int)x, (int)y);
        }, false).setChildOf(box);
        //Behavior
        new UIButton(TEXTURE, 61, 11, 53, 16, 229, behaviorV, behaviorVOffset, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            CustomMenus.pickupItemAtSlot(3);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, CustomMenus.getDisplayTooltip(screenHandler.getSlot(3).getStack()), (int)x, (int)y);
        }, false).setChildOf(box);
        //Mods
        new UIButton(TEXTURE, 115, 11, 53, 16, 283, modsV, modsVOffset, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            CustomMenus.pickupItemAtSlot(6);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, CustomMenus.getDisplayTooltip(screenHandler.getSlot(6).getStack()), (int)x, (int)y);
        }, false).setChildOf(box);
        for (int i = 0; i < 9; i++) {
            int numberX = 8 + (i * 18);
            int numberU = 176 + (i * 16);
            int slot = i + 9;
            if (i < numbers) {
                new UIButton(TEXTURE, numberX, 30, 16, 14, numberU, 62, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                    CustomMenus.pickupItemAtSlot(slot);
                }, (b, m, x, y) -> {
                    screen.renderTooltip(m, CustomMenus.getDisplayTooltip(screenHandler.getSlot(slot).getStack()), (int)x, (int)y);
                }, false).setChildOf(box);
            } else {
                new UIButton(TEXTURE, numberX, 30, 16, 14, numberU, 48, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                    CustomMenus.pickupItemAtSlot(slot);
                }, (b, m, x, y) -> {
                    screen.renderTooltip(m, CustomMenus.getDisplayTooltip(screenHandler.getSlot(slot).getStack()), (int)x, (int)y);
                }, false).setChildOf(box);
            }
        }
    }
}