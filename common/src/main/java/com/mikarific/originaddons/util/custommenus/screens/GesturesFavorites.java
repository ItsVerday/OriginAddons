package com.mikarific.originaddons.util.custommenus.screens;

import com.mikarific.originaddons.ui.Window;
import com.mikarific.originaddons.ui.components.UIButton;
import com.mikarific.originaddons.ui.components.UIComponent;
import com.mikarific.originaddons.ui.components.UIText;
import com.mikarific.originaddons.ui.components.UITexture;
import com.mikarific.originaddons.util.CustomMenus;
import com.mikarific.originaddons.util.custommenus.CustomScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class GesturesFavorites extends CustomScreen {
    public static void init(Screen screen, Window window) {
        assert MinecraftClient.getInstance().player != null;
        ScreenHandler screenHandler = MinecraftClient.getInstance().player.currentScreenHandler;
        
        Identifier TEXTURE = new Identifier("originaddons", "gui/custommenus/gestures_favorites.png");
        int TEXTURE_WIDTH = 384;
        int TEXTURE_HEIGHT = 136;
        UIComponent box = new UITexture(TEXTURE, (screen.width - 176) / 2, (screen.height - 106) / 2, 176, 106, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(window);
        //Slot 1
        new UIButton(TEXTURE, 8, 27, 52, 34, 176, 0, 34, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            CustomMenus.pickupItemAtSlot(9);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, CustomMenus.getDisplayTooltip(screenHandler.getSlot(9).getStack()), (int)x, (int)y);
        }, false).setChildOf(box);
        //Slot 2
        new UIButton(TEXTURE, 62, 27, 52, 34, 228, 0, 34, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            CustomMenus.pickupItemAtSlot(12);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, CustomMenus.getDisplayTooltip(screenHandler.getSlot(12).getStack()), (int)x, (int)y);
        }, false).setChildOf(box);
        //Slot 3
        new UIButton(TEXTURE, 116, 27, 52, 34, 280, 0, 34, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            CustomMenus.pickupItemAtSlot(15);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, CustomMenus.getDisplayTooltip(screenHandler.getSlot(15).getStack()), (int)x, (int)y);
        }, false).setChildOf(box);
        //Slot 4
        new UIButton(TEXTURE, 8, 63, 52, 34, 176, 68, 34, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            CustomMenus.pickupItemAtSlot(27);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, CustomMenus.getDisplayTooltip(screenHandler.getSlot(27).getStack()), (int)x, (int)y);
        }, false).setChildOf(box);
        //Slot 5
        new UIButton(TEXTURE, 62, 63, 52, 34, 228, 68, 34, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            CustomMenus.pickupItemAtSlot(30);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, CustomMenus.getDisplayTooltip(screenHandler.getSlot(30).getStack()), (int)x, (int)y);
        }, false).setChildOf(box);
        //Slot 6
        new UIButton(TEXTURE, 116, 63, 52, 34, 280, 68, 34, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            CustomMenus.pickupItemAtSlot(33);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, CustomMenus.getDisplayTooltip(screenHandler.getSlot(33).getStack()), (int)x, (int)y);
        }, false).setChildOf(box);

        //Back
        new UIButton(TEXTURE, 8, 9, 16, 14, 332, 0, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            CustomMenus.pickupItemAtSlot(0);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, CustomMenus.getDisplayTooltip(screenHandler.getSlot(0).getStack()), (int)x, (int)y);
        }, false).setChildOf(box);
        //View All
        new UIButton(TEXTURE, 116, 9, 52, 14, 332, 28, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            CustomMenus.pickupItemAtSlot(6);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, CustomMenus.getDisplayTooltip(screenHandler.getSlot(6).getStack()), (int)x, (int)y);
        }, false).setChildOf(box);
        //Gestures
        List<Text> gestures = screen.getTitle().getSiblings().get(1).getSiblings().get(0).getSiblings().stream().filter(sibling -> sibling.getStyle().getFont().getPath().contains("gesture")).toList();
        for (int i = 0; i < gestures.size(); i++) {
            if (i == 0) new UIText(gestures.get(i), 16777215, 18, -3).setChildOf(box);
            if (i == 1) new UIText(gestures.get(i), 16777215, 73, -3).setChildOf(box);
            if (i == 2) new UIText(gestures.get(i), 16777215, 128, -3).setChildOf(box);
            if (i == 3) new UIText(gestures.get(i), 16777215, 21, -3).setChildOf(box);
            if (i == 4) new UIText(gestures.get(i), 16777215, 76, -3).setChildOf(box);
            if (i == 5) new UIText(gestures.get(i), 16777215, 131, -3).setChildOf(box);
        }
    }
}