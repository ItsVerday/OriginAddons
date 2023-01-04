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

import java.util.ArrayList;
import java.util.List;

public class Orbit extends CustomScreen {
    public static void init(Screen screen, Window window) {
        assert MinecraftClient.getInstance().player != null;
        ScreenHandler screenHandler = MinecraftClient.getInstance().player.currentScreenHandler;

        Identifier TEXTURE = new Identifier("originaddons", "gui/custommenus/orbit.png");
        int TEXTURE_WIDTH = 316;
        int TEXTURE_HEIGHT = 112;
        UIComponent box = new UITexture(TEXTURE, (screen.width - 176) / 2, (screen.height - 112) / 2, 176, 112, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(window);
        //Profile
        new UIButton(TEXTURE, 98, 12, 70, 16, 176, 0, 16, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            CustomMenus.pickupItemAtSlot(8);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, CustomMenus.getDisplayTooltip(screenHandler.getSlot(8).getStack()), (int)x, (int)y);
        }, false).setChildOf(box);
        //Quests
        new UIButton(TEXTURE, 98, 30, 70, 16, 176, 32, 16, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            CustomMenus.pickupItemAtSlot(17);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, CustomMenus.getDisplayTooltip(screenHandler.getSlot(17).getStack()), (int)x, (int)y);
        }, false).setChildOf(box);
        //Friends
        new UIButton(TEXTURE, 98, 48, 70, 16, 176, 64, 16, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            CustomMenus.pickupItemAtSlot(26);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, CustomMenus.getDisplayTooltip(screenHandler.getSlot(26).getStack()), (int)x, (int)y);
        }, false).setChildOf(box);
        //Discord
        new UIButton(TEXTURE, 98, 66, 70, 16, 246, 0, 16, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            CustomMenus.pickupItemAtSlot(35);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, CustomMenus.getDisplayTooltip(screenHandler.getSlot(35).getStack()), (int)x, (int)y);
        }, false).setChildOf(box);
        //Settings
        new UIButton(TEXTURE, 98, 84, 70, 16, 246, 32, 16, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            CustomMenus.pickupItemAtSlot(44);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, CustomMenus.getDisplayTooltip(screenHandler.getSlot(44).getStack()), (int)x, (int)y);
        }, false).setChildOf(box);
    }
}
